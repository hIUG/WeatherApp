package com.allexis.weatherapp.core.network.service.weather.model

import android.os.Parcelable
import com.allexis.weatherapp.core.network.model.GsonObject
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/13/17.
 *
 * POJO classes for Weather API response
 * To take advantage of experimental Parcelize annotation and of Kotlin data class, all POJOs are
 * defined in Kotlin. Some of them are 1 line POJOs? Cool! Right?
 */

@Parcelize data class WeatherResponse(val cod: Int,
                                      val message: String,
                                      val coord: Coord,
                                      val weather: List<Weather>,
                                      val base: String,
                                      val main: MainWeatherProperties,
                                      val visibility: Int,
                                      val wind: Wind,
                                      val clouds: Clouds,
                                      val dt: Int,
                                      val sys: Sys,
                                      val id: Int,
                                      val name: String) : Parcelable, GsonObject()