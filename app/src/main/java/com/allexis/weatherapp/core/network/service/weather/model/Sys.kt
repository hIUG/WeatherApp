package com.allexis.weatherapp.core.network.service.weather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/21/17.
 */

@Parcelize data class Sys(val type: Int,
                          val id: Int,
                          val message: Double,
                          val country: String,
                          val sunrise: Long,
                          val sunset: Long) : Parcelable