package com.allexis.weatherapp.core.network.service.weather.model

import android.os.Parcelable
import com.allexis.weatherapp.core.network.service.common.NetworkController
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/21/17.
 */

@Parcelize data class Weather(val id: Int,
                              val main: String,
                              val description: String,
                              val icon: String) : Parcelable {
    fun getIconUrl(): String = NetworkController.BASE_URL_IMG + icon + ".png"
}
