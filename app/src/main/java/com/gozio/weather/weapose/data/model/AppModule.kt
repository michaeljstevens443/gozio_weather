package com.gozio.weather.weapose.data.model

import com.gozio.weather.weapose.data.model.repository.WeatherRepository
import com.gozio.weather.weapose.presentation.utils.Constants.Default.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Handles all of our DI (dagger/hilt)
 * for repo/api handling
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideWeatherRepo(
        api: WeatherApi,
    ) = WeatherRepository(api)

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(WeatherApi::class.java)
    }

}