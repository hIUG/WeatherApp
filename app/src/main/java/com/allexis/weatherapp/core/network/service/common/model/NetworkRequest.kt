package com.allexis.weatherapp.core.network.service.common.model

import com.allexis.weatherapp.core.network.model.GsonObject
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by allexis on 10/22/17.
 */

data class NetworkRequest<T : GsonObject>(val call: Call<T>, val callBack: Callback<T>, val responseClass: Class<T>)