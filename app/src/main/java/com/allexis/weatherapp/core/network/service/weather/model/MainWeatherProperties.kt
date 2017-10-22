package com.allexis.weatherapp.core.network.service.weather.model

import android.os.Parcelable
import com.allexis.weatherapp.core.persist.data.Temperature
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/21/17.
 */

@Parcelize data class MainWeatherProperties(private val temp: Double,
                                            val pressure: Double,
                                            val humidity: Int,
                                            @SerializedName("temp_min") private val tempMin: Double,
                                            @SerializedName("temp_max") private val tempMax: Double,
                                            val seaLevel: Double,
                                            val grndLevel: Double,
                                            val tempKf: Int) : Parcelable {
    companion object {
        const val TEMP_VALUE_FORMAT: String = "%.2f"
    }

    private fun getTempKelvin(): Double = temp
    private fun getTempFahrenheit(): Double = ((temp - 273.15) * 9 / 5) + 32
    private fun getTempCelsius(): Double = (temp - 273.15)
    private fun getTempMinKelvin(): Double = tempMin
    private fun getTempMinFahrenheit(): Double = ((tempMin - 273.15) * 9 / 5) + 32
    private fun getTempMinCelsius(): Double = (tempMin - 273.15)
    private fun getTempMaxKelvin(): Double = tempMax
    private fun getTempMaxFahrenheit(): Double = ((tempMax - 273.15) * 9 / 5) + 32
    private fun getTempMaxCelsius(): Double = (tempMax - 273.15)

    private fun getTemp(): Double = when (Temperature.getPreferredTemp()) {
        Temperature.TEMP_C -> getTempCelsius()
        Temperature.TEMP_F -> getTempFahrenheit()
        else -> getTempKelvin()
    }

    private fun getTempMin(): Double = when (Temperature.getPreferredTemp()) {
        Temperature.TEMP_C -> getTempMinCelsius()
        Temperature.TEMP_F -> getTempMinFahrenheit()
        else -> getTempMinKelvin()
    }

    private fun getTempMax(): Double = when (Temperature.getPreferredTemp()) {
        Temperature.TEMP_C -> getTempMaxCelsius()
        Temperature.TEMP_F -> getTempMaxFahrenheit()
        else -> getTempMaxKelvin()
    }

    fun getTempStr(): String = TEMP_VALUE_FORMAT.format(getTemp())

    fun getTempMinStr(): String = TEMP_VALUE_FORMAT.format(getTempMin())

    fun getTempMaxStr(): String = TEMP_VALUE_FORMAT.format(getTempMax())
}