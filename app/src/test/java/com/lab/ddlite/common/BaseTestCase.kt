package com.lab.ddlite.common

import org.junit.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

abstract class BaseTestCase {
    @Before
    open fun before() {
        // setup before each test
    }

    @After
    open fun after() {
        // teardown after each test
    }

    // Printing test name
    @get:Rule
    val textWatcher = object : TestWatcher() {
        override fun starting(description: Description) {
            super.starting(description)
            println()
            println("############# ${description.methodName}()")
            println()
        }

        override fun succeeded(description: Description?) {
            super.succeeded(description)
            println("OK")
        }
    }
}