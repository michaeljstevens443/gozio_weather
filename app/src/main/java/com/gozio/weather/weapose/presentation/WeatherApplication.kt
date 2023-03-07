package com.gozio.weather.weapose.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (com.gozio.weather.weapose.BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
