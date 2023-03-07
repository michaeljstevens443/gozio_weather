package com.gozio.weather.weapose.presentation.utils

import com.google.android.gms.maps.model.LatLng
import com.gozio.weather.weapose.BuildConfig

object Constants {
    object DateFormat {
        const val HH_mm = "HH:mm"
        const val EE_MM_dd = "EEEE, MMM dd"
    }

    object Key {
        const val LAT_LNG = "lat_lng"
        const val LAT = "lat"
        const val LNG = "lng"
        const val FROM_ROUTE = "from_route"
        const val ADDRESS_NAME = "address_name"
    }

    object Default {
        val LAT_LNG_DEFAULT = LatLng(33.78591032377107, -84.40964058633683)
        val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        // wasnt working when trying to use gradle.properties and then bringing
        // it in via build.gradle -> BuildConfig.API_KEY
        val API_KEY = "b6458011a483edf39eff7d11034f5f42"
    }
}
