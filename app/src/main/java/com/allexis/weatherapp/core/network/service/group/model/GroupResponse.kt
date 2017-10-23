package com.allexis.weatherapp.core.network.service.group.model

import android.os.Parcelable
import com.allexis.weatherapp.core.network.model.GsonObject
import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/22/17.
 */

@Parcelize data class GroupResponse(val cnt: Int,
                                    val list: List<WeatherResponse>) : Parcelable, GsonObject()