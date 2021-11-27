package com.example

import mu.KLogging
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import javax.persistence.EntityManager


/**
 * https://www.baeldung.com/junit-5-extensions
 */
class TruncateDbExtension(
    private val entityManager: EntityManager
): BeforeEachCallback, AfterEachCallback {

    companion object: KLogging()

    override fun beforeEach(context: ExtensionContext?) {
        logger.info { "@@@@@@@@@@@@ before each @@@@@@@@@@@@" }
    }

    override fun afterEach(context: ExtensionContext?) {
        logger.info { "@@@@@@@@@@@@ after each @@@@@@@@@@@@" }
    }
}