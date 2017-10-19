package com.allexis.weatherapp.core.network.service.forecast

import android.os.Parcelable
import com.allexis.weatherapp.core.network.service.weather.*
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/12/17.
 *
 * POJO classes for Forecast API response
 * To take advantage of experimental Parcelize annotation and of Kotlin data class, all POJOs are
 * defined in Kotlin. 1 line POJOs? Cool! Right?
 */

@Parcelize data class ForecastResponse(val cod: String,
                                       val message: Double,
                                       val cnt: Int,
                                       val list: kotlin.collections.List<List>,
                                       val city: City) : Parcelable

@Parcelize data class Sys(val pod: String) : Parcelable

@Parcelize data class City(val id: Int,
                           val name: String,
                           val coord: Coord,
                           val country: String,
                           val population: Int) : Parcelable

@Parcelize data class List(val dt: Int,
                           val main: Main,
                           val weather: kotlin.collections.List<Weather>,
                           val clouds: Clouds,
                           val wind: Wind,
                           val sys: Sys,
                           val dtTxt: String) : Parcelable