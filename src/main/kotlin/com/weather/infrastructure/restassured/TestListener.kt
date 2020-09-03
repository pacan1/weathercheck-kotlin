package com.weather.infrastructure.restassured

import io.cucumber.plugin.ConcurrentEventListener
import io.cucumber.plugin.event.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class TestListener : ConcurrentEventListener {

    private val writeHandler: EventHandler<WriteEvent?> =
        EventHandler { event -> logger.debug { "Write event: ${event?.text}" } }


    private val testStepStarted: EventHandler<TestStepStarted?> =
        EventHandler { event -> logger.debug { "Step started: ${event?.testStep?.codeLocation}" } }


    private val testCaseStarted: EventHandler<TestCaseStarted?> =
        EventHandler { event ->
            logger.debug { "Scenario ${event?.testCase?.name} started" }
        }

    private val testCaseFinished: EventHandler<TestCaseFinished?> =
        EventHandler { event ->
            val message = "Scenario: ${event?.testCase?.name} finished: ${event?.result?.status}"
            when (event?.result?.status) {
                Status.FAILED -> logger.error { message }
                else -> logger.info { message }
            }
        }

    override fun setEventPublisher(publisher: EventPublisher) {
        publisher.registerHandlerFor(WriteEvent::class.java, writeHandler)
        publisher.registerHandlerFor(TestCaseStarted::class.java, testCaseStarted)
        publisher.registerHandlerFor(TestStepStarted::class.java, testStepStarted)
        publisher.registerHandlerFor(TestCaseFinished::class.java, testCaseFinished)
    }
}
