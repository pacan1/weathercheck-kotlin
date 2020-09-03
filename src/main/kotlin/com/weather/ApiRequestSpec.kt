package com.weather

import io.restassured.filter.FilterContext
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.internal.RequestSpecificationImpl
import io.restassured.response.Response
import io.restassured.specification.FilterableRequestSpecification
import io.restassured.specification.FilterableResponseSpecification
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

val requestLogger = object : RequestLoggingFilter() {
    override fun filter(
        requestSpec: FilterableRequestSpecification,
        responseSpec: FilterableResponseSpecification,
        ctx: FilterContext
    ): Response {
        logger.info {
            """Request: ${requestSpec.method} ${requestSpec.uri}
| Form: ${requestSpec.formParams} Body: ${(requestSpec as RequestSpecificationImpl).body}""".trimMargin()
        }
        return ctx.next(requestSpec, responseSpec)
    }
}

val responseLogger = object : ResponseLoggingFilter() {
    override fun filter(
        requestSpec: FilterableRequestSpecification,
        responseSpec: FilterableResponseSpecification,
        ctx: FilterContext
    ): Response {
        val response = ctx.next(requestSpec, responseSpec)
        logger.info { "Response: code: ${response.statusCode} body: ${response.asString()}" }
        return response
    }
}