package com.allexis.weatherapp.core.network.service.forecast.model

import android.os.Parcelable
import com.allexis.weatherapp.core.network.service.weather.model.Coord
import kotlinx.android.parcel.Parcelize

/**
 * Created by allexis on 10/21/17.
 */

@Parcelize data class City(val id: Int,
                           val name: String,
                           val coord: Coord,
                           val country: String,
                           val population: Int) : Parcelable