package com.allexis.weatherapp.core.network.service.weather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/21/17.
 */

@Parcelize data class Coord(val long: Double,
                            val lat: Double) : Parcelable