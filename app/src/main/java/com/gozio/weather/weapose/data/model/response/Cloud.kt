package com.gozio.weather.weapose.data.model.response

import com.google.gson.annotations.SerializedName

data class Cloud(
    @SerializedName("all") val all: Int? = 0,
) : Model()
