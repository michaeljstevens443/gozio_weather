package com.gozio.weather.weapose.base

import org.junit.Rule

open class BaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
}
