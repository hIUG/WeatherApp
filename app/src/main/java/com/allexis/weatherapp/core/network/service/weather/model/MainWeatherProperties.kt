package com.allexis.weatherapp.core.network.service.weather.model

import android.os.Parcelable
import com.allexis.weatherapp.R
import com.allexis.weatherapp.WeatherApplication
import com.allexis.weatherapp.core.persist.CacheManager
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

    //TODO: This should be done on a separate Util class and accessing resources every time should be avoided
    private fun getTemp(): Double = when (CacheManager.getInstance().preferredTemp) {
        WeatherApplication.getInstance().getString(R.string.valid_unit_systems_c) -> getTempCelsius()
        WeatherApplication.getInstance().getString(R.string.valid_unit_systems_f) -> getTempFahrenheit()
        else -> getTempKelvin()
    }

    private fun getTempMin(): Double = when (CacheManager.getInstance().preferredTemp) {
        WeatherApplication.getInstance().getString(R.string.valid_unit_systems_c) -> getTempMinCelsius()
        WeatherApplication.getInstance().getString(R.string.valid_unit_systems_f) -> getTempMinFahrenheit()
        else -> getTempMinKelvin()
    }

    private fun getTempMax(): Double = when (CacheManager.getInstance().preferredTemp) {
        WeatherApplication.getInstance().getString(R.string.valid_unit_systems_c) -> getTempMaxCelsius()
        WeatherApplication.getInstance().getString(R.string.valid_unit_systems_f) -> getTempMaxFahrenheit()
        else -> getTempMaxKelvin()
    }

    fun getTempStr(): String = TEMP_VALUE_FORMAT.format(getTemp())

    fun getTempMinStr(): String = TEMP_VALUE_FORMAT.format(getTempMin())

    fun getTempMaxStr(): String = TEMP_VALUE_FORMAT.format(getTempMax())
}