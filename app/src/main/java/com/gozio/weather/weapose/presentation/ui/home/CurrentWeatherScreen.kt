package com.gozio.weather.weapose.presentation.ui.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.NEXUS_5
import androidx.compose.ui.tooling.preview.Devices.PIXEL_4_XL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gozio.weather.weapose.R
import com.gozio.weather.weapose.presentation.model.CurrentWeatherMapper
import com.gozio.weather.weapose.presentation.model.CurrentWeatherViewData
import com.gozio.weather.weapose.presentation.model.CurrentWeatherViewState
import com.gozio.weather.weapose.presentation.model.factory.previewCurrentWeatherViewData
import com.gozio.weather.weapose.presentation.theme.WeaposeTheme
import com.gozio.weather.weapose.presentation.ui.WeatherAppState

@Composable
fun CurrentWeather(
    appState: WeatherAppState,
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val weather by remember { viewModel.currentWeather }

    val currentWeather = CurrentWeatherMapper().mapToViewData(weather)
    CurrentWeatherScreen(
        state = CurrentWeatherViewState.Success(currentWeather)
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CurrentWeatherScreen(
    state: CurrentWeatherViewState
) {
    when (state) {
        is CurrentWeatherViewState.Loading -> CircularProgressIndicator()
        is CurrentWeatherViewState.Success -> Column {
            CurrentWeatherAppBar(city = state.currentWeather.city)
            HomeContent(
                currentWeather = state.currentWeather
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    currentWeather: CurrentWeatherViewData,
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val refreshing by viewModel.isLoading
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.getWeather() })

    Column(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(CenterHorizontally))
        NowWeather(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            currentWeather = currentWeather,
        )

        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(
                    top = 50.dp,
                    bottom = 30.dp,
                )
        )
    }
}

@Composable
fun NowWeather(
    modifier: Modifier = Modifier,
    currentWeather: CurrentWeatherViewData,
) {
    Row(modifier = modifier) {
        Image(
            painter = painterResource(id = currentWeather.background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 50.dp)
                .weight(1f),
            alignment = Alignment.CenterEnd,
            contentScale = ContentScale.FillHeight,
        )

        Column(
            modifier = Modifier
                .padding(end = 15.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(
                    id = R.string.home_text_celsius_high_low,
                    currentWeather.maxTemp,
                    currentWeather.minTemp,
                ),
            )

            Degrees(
                currentWeather = currentWeather.weather,
                currentTemp = currentWeather.temp,
            )

            DetailWeather(
                iconId = R.drawable.ic_sun_rise,
                title = stringResource(id = R.string.sun_rise),
                description = currentWeather.sunRise,
            )

            DetailWeather(
                iconId = R.drawable.ic_wind,
                title = stringResource(id = R.string.wind),
                description = stringResource(
                    id = R.string.home_text_meter_per_second,
                    currentWeather.wind
                ),
            )

            DetailWeather(
                iconId = R.drawable.ic_humidity,
                title = stringResource(id = R.string.humidity),
                description = stringResource(
                    id = R.string.home_text_humidity,
                    currentWeather.humidity
                ),
            )
        }
    }
}

@Composable
fun DetailWeather(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    title: String,
    description: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = iconId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 5.dp),
        )

        Text(
            text = description,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeatherAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    city: String? = null,
    onNavigateSearch: () -> Unit = {},
) {
    SmallTopAppBar(
        modifier = modifier,
        title = {
            if (title.isNotBlank()) {
                Text(text = title, maxLines = 1, overflow = TextOverflow.Visible)
            }
        },
        actions = {
            Card(
                onClick = onNavigateSearch,
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Sharp.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 5.dp),
                    )

                    Text(
                        text = city ?: stringResource(id = R.string.unknown_address),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
        },
    )
}

@Composable
fun Degrees(
    modifier: Modifier = Modifier,
    currentTemp: String,
    currentWeather: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = currentTemp,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.alignBy(LastBaseline),
            )

            Column(modifier = Modifier.alignBy(LastBaseline)) {
                Text(text = "o", modifier = Modifier.padding(bottom = 10.dp))

                Text(text = "c")
            }
        }

        Text(
            text = currentWeather,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 22.sp),
        )
    }
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun NowWeatherPreview() {
    WeaposeTheme {
        NowWeather(
            modifier = Modifier.size(500.dp),
            currentWeather = previewCurrentWeatherViewData(),
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DegreesPreview() {
    WeaposeTheme {
        Degrees(
            currentWeather = previewCurrentWeatherViewData().weather,
            currentTemp = previewCurrentWeatherViewData().temp,
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppBarPreview() {
    WeaposeTheme {
        CurrentWeatherAppBar(city = previewCurrentWeatherViewData().city)
    }
}

@Preview(name = "Light", showBackground = true, device = NEXUS_5)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_4_XL
)
@Composable
private fun ScreenPreview() {
    WeaposeTheme {
        CurrentWeatherScreen(
            CurrentWeatherViewState.Success(
                CurrentWeatherViewData(
                    "city",
                    "max temp",
                    "min temp",
                    "temp",
                    "weather",
                    "sunRise",
                    "wind",
                    "humidity",
                    R.drawable.bg_rain
                )
            )
        )
    }
}

