package com.allexis.weatherapp.core.network.service.forecast.model

import android.os.Parcelable
import com.allexis.weatherapp.core.network.service.weather.model.Clouds
import com.allexis.weatherapp.core.network.service.weather.model.MainWeatherProperties
import com.allexis.weatherapp.core.network.service.weather.model.Weather
import com.allexis.weatherapp.core.network.service.weather.model.Wind
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/21/17.
 */

@Parcelize data class ForecastListElement(val dt: Long,
                                          val main: MainWeatherProperties,
                                          val weather: List<Weather>,
                                          val clouds: Clouds,
                                          val wind: Wind,
                                          val sys: Sys,
                                          @SerializedName("dt_txt") val dtTxt: String) : Parcelable