package com.weather.infrastructure.restassured

import io.qameta.allure.Allure
import io.qameta.allure.attachment.DefaultAttachmentProcessor
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer
import io.qameta.allure.attachment.http.HttpRequestAttachment
import io.qameta.allure.attachment.http.HttpResponseAttachment
import io.qameta.allure.model.StepResult
import io.restassured.filter.FilterContext
import io.restassured.filter.OrderedFilter
import io.restassured.internal.NameAndValue
import io.restassured.internal.support.Prettifier
import io.restassured.response.Response
import io.restassured.specification.FilterableRequestSpecification
import io.restassured.specification.FilterableResponseSpecification
import java.util.*

class StepWrapperAllureRestAssured : AllureRestAssuredFilter() {
    override fun filter(
        requestSpec: FilterableRequestSpecification,
        responseSpec: FilterableResponseSpecification,
        filterContext: FilterContext
    ): Response {
        val lifecycle = Allure.getLifecycle()
        lifecycle.startStep(
            UUID.randomUUID().toString(),
            StepResult()
                .setStatus(io.qameta.allure.model.Status.PASSED)
                .setName("${requestSpec.method}: ${requestSpec.uri}")
        )
        try {
            return super.filter(requestSpec, responseSpec, filterContext)
        } finally {
            lifecycle.stopStep()
        }
    }
}


/**
 * Allure logger filter for Rest-assured.
 */
open class AllureRestAssuredFilter : OrderedFilter {
    private var requestTemplatePath = "http-request.ftl"
    private var responseTemplatePath = "http-response.ftl"

    fun setRequestTemplate(templatePath: String): AllureRestAssuredFilter {
        requestTemplatePath = templatePath
        return this
    }

    fun setResponseTemplate(templatePath: String): AllureRestAssuredFilter {
        responseTemplatePath = templatePath
        return this
    }


    override fun filter(
        requestSpec: FilterableRequestSpecification,
        responseSpec: FilterableResponseSpecification,
        filterContext: FilterContext
    ): Response {
        val prettifier = Prettifier()
        val requestAttachmentBuilder =
            HttpRequestAttachment.Builder.create("Request", requestSpec.uri)
                .setMethod(requestSpec.method)
                .setHeaders(
                    toMapConverter(
                        requestSpec.headers
                    )
                )
                .setCookies(
                    toMapConverter(
                        requestSpec.cookies
                    )
                )
        if (null != requestSpec.getBody()) {
            requestAttachmentBuilder.setBody(prettifier.getPrettifiedBodyIfPossible(requestSpec))
        }
        val requestAttachment = requestAttachmentBuilder.build()
        DefaultAttachmentProcessor().addAttachment(
            requestAttachment,
            FreemarkerAttachmentRenderer(requestTemplatePath)
        )
        val response = filterContext.next(requestSpec, responseSpec)
        val responseAttachment =
            HttpResponseAttachment.Builder.create("Response")
                .setResponseCode(response.statusCode)
                .setHeaders(
                    toMapConverter(
                        response.headers
                    )
                )
                .setBody(prettifier.getPrettifiedBodyIfPossible(response, response.body))
                .build()

        DefaultAttachmentProcessor().addAttachment(
            responseAttachment,
            FreemarkerAttachmentRenderer(responseTemplatePath)
        )
        return response
    }

    override fun getOrder(): Int {
        return Int.MAX_VALUE
    }

    companion object {
        private fun toMapConverter(items: Iterable<NameAndValue>): Map<String, String> {
            val result = items.map { it.name to it.value }.toMap()
            return result
        }
    }
}