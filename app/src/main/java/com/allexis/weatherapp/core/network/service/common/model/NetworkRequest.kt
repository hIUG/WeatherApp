package com.allexis.weatherapp.core.network.service.common.model

import com.allexis.weatherapp.core.network.model.GsonObject
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by allexis on 10/22/17.
 * Taking advantage of Kotlin data class again to define a 1 line POJO with setters/getters/equals
 * and toString implicitly implemented.
 */

data class NetworkRequest<T : GsonObject>(val call: Call<T>, val callBack: Callback<T>, val responseClass: Class<T>)