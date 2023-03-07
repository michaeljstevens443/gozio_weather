package com.gozio.weather.weapose.presentation.ui.home


//import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gozio.weather.weapose.data.model.Result.Error
import com.gozio.weather.weapose.data.model.Result.Success
import com.gozio.weather.weapose.data.model.repository.WeatherRepository
import com.gozio.weather.weapose.data.model.response.CurrentWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val weatherRepo: WeatherRepository
) : ViewModel() {
    val currentWeather = mutableStateOf(CurrentWeather())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        getWeather()
    }

    fun getWeather() {
        viewModelScope.launch {
            isLoading.value = true
            val result = weatherRepo.getWeather()
            when (result) {
                is Success -> {
                    currentWeather.value = result.data!!
                    isLoading.value = false
                }
                is Error -> loadError.value = result.message ?: "Unknown error with VM weather call"
            }
        }
    }
}
