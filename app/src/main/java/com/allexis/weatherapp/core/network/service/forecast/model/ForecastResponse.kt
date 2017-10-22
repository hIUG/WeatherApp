package com.allexis.weatherapp.core.network.service.forecast.model

import android.os.Parcelable
import com.allexis.weatherapp.core.network.model.GsonObject
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/12/17.
 *
 * POJO classes for Forecast API response
 * To take advantage of experimental Parcelize annotation and of Kotlin data class, all POJOs are
 * defined in Kotlin. Some of them are 1 line POJOs? Cool! Right?
 */

@Parcelize data class ForecastResponse(val cod: String,
                                       val message: Double,
                                       val cnt: Int,
                                       val list: List<ForecastListElement>,
                                       val city: City) : Parcelable, GsonObject()