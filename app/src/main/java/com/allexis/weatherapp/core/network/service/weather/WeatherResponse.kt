package com.allexis.weatherapp.core.network.service.weather

import android.os.Parcelable
import com.allexis.weatherapp.core.network.service.NetworkController
import com.allexis.weatherapp.core.util.TemperatureUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/13/17.
 *
 * POJO classes for Weather API response
 * To take advantage of experimental Parcelize annotation and of Kotlin data class, all POJOs are
 * defined in Kotlin. 1 line POJOs? Cool! Right?
 */

@Parcelize data class WeatherResponse(val cod: Int,
                                      val message: String,
                                      val coord: Coord,
                                      val weather: List<Weather>,
                                      val base: String,
                                      val main: Main,
                                      val visibility: Int,
                                      val wind: Wind,
                                      val clouds: Clouds,
                                      val dt: Int,
                                      val sys: Sys,
                                      val id: Int,
                                      val name: String) : Parcelable

@Parcelize data class Coord(val long: Double,
                            val lat: Double) : Parcelable


@Parcelize data class Weather(val id: Int,
                              val main: String,
                              val description: String,
                              val icon: String) : Parcelable {
    fun getIconUrl(): String = NetworkController.BASE_URL_IMG + icon + ".png"
}

@Parcelize data class Main(private val temp: Double,
                           val pressure: Double,
                           val humidity: Int,
                           @SerializedName("temp_min") private val tempMin: Double,
                           @SerializedName("temp_max") private val tempMax: Double,
                           val seaLevel: Double,
                           val grndLevel: Double,
                           val tempKf: Int) : Parcelable {
    private fun getTempKelvin(): Double = temp
    private fun getTempFahrenheit(): Double = ((temp - 273.15) * 9 / 5) + 32
    private fun getTempCelsius(): Double = (temp - 273.15)
    private fun getTempMinKelvin(): Double = tempMin
    private fun getTempMinFahrenheit(): Double = ((tempMin - 273.15) * 9 / 5) + 32
    private fun getTempMinCelsius(): Double = (tempMin - 273.15)
    private fun getTempMaxKelvin(): Double = tempMax
    private fun getTempMaxFahrenheit(): Double = ((tempMax - 273.15) * 9 / 5) + 32
    private fun getTempMaxCelsius(): Double = (tempMax - 273.15)

    fun getTemp(): Double = when (TemperatureUtil.preferredTemp) {
        TemperatureUtil.TEMP_C -> getTempCelsius()
        TemperatureUtil.TEMP_F -> getTempFahrenheit()
        else -> getTempKelvin()
    }

    fun getTempStr(): String = "%.2f".format(getTemp())

    fun getTempMin(): Double = when (TemperatureUtil.preferredTemp) {
        TemperatureUtil.TEMP_C -> getTempMinCelsius()
        TemperatureUtil.TEMP_F -> getTempMinFahrenheit()
        else -> getTempMinKelvin()
    }

    fun getTempMinStr(): String = "%.2f".format(getTempMin())

    fun getTempMax(): Double = when (TemperatureUtil.preferredTemp) {
        TemperatureUtil.TEMP_C -> getTempMaxCelsius()
        TemperatureUtil.TEMP_F -> getTempMaxFahrenheit()
        else -> getTempMaxKelvin()
    }

    fun getTempMaxStr(): String = "%.2f".format(getTempMax())
}

@Parcelize data class Wind(val speed: Double,
                           val deg: Double) : Parcelable

@Parcelize data class Clouds(val all: Int) : Parcelable

@Parcelize data class Sys(val type: Int,
                          val id: Int,
                          val message: Double,
                          val country: String,
                          val sunrise: Long,
                          val sunset: Long) : Parcelable