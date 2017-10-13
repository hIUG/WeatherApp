package com.allexis.weatherapp.core.network.service.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/13/17.
 *
 * POJO classes for Weather API response
 * To take advantage of experimental Parcelize annotation and of Kotlin data class, all POJOs are
 * defined in Kotlin. 1 line POJOs? Cool! Right?
 */

@Parcelize data class WeatherResponse(val coord: Coord,
                                      val weather: List<Weather>,
                                      val base: String,
                                      val main: Main,
                                      val visibility: Int,
                                      val wind: Wind,
                                      val clouds: Clouds,
                                      val dt: Int,
                                      val sys: Sys,
                                      val id: Int,
                                      val name: String,
                                      val cod: Int) : Parcelable

@Parcelize data class Coord(val long: Double,
                            val lat: Double) : Parcelable


@Parcelize data class Weather(val id: Int,
                              val main: String,
                              val description: String,
                              val icon: String) : Parcelable

@Parcelize data class Main(val temp: Double,
                           val pressure: Int,
                           val humidity: Int,
                           val tempMin: Double,
                           val tempMax: Double,
                           val seaLevel: Double,
                           val grndLevel: Double,
                           val tempKf: Int) : Parcelable

@Parcelize data class Wind(val speed: Double,
                           val deg: Int) : Parcelable

@Parcelize data class Clouds(val all: Int) : Parcelable

@Parcelize data class Sys(val type: Int,
                          val id: Int,
                          val message: Double,
                          val country: String,
                          val sunrise: Int,
                          val sunset: Int) : Parcelable