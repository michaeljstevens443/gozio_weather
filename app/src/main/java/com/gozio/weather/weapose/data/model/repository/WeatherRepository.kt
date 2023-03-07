package com.gozio.weather.weapose.data.model.repository


import com.gozio.weather.weapose.data.model.Result
import com.gozio.weather.weapose.data.model.WeatherApi
import com.gozio.weather.weapose.data.model.response.CurrentWeather
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WeatherRepository @Inject constructor(private val weatherApi: WeatherApi) {
    suspend fun getWeather(): Result<CurrentWeather> {
        val result = try {
            weatherApi.getWeather()
        } catch (e: Exception) {
            return Result.Error("An unknown error has occurred while retrieving weather: $e")
        }
        return Result.Success(result)
    }
}