package com.gozio.weather.weapose.data.model

import com.gozio.weather.weapose.data.model.response.CurrentWeather
import com.gozio.weather.weapose.presentation.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") latitude: Double = Constants.Default.LAT_LNG_DEFAULT.latitude,
        @Query("lon") longitude: Double = Constants.Default.LAT_LNG_DEFAULT.longitude,
        @Query("appid") appid: String = Constants.Default.API_KEY
    ): CurrentWeather

}