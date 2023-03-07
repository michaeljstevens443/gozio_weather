package com.gozio.weather.weapose.presentation.model

sealed interface CurrentWeatherViewState {
    object Loading : CurrentWeatherViewState
    data class Success(
        val currentWeather: CurrentWeatherViewData
    ) : CurrentWeatherViewState
}