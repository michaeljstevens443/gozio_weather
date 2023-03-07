package com.gozio.weather.weapose.presentation.ui

import android.content.Context
import com.gozio.weather.weapose.base.BaseTest
import com.gozio.weather.weapose.presentation.model.CurrentWeatherMapper
import com.gozio.weather.weapose.presentation.ui.home.CurrentWeatherViewModel
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import org.junit.Rule

class CurrentWeatherViewModelTest : BaseTest() {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var currentWeatherMapper: CurrentWeatherMapper

    private lateinit var viewModel: CurrentWeatherViewModel

}
