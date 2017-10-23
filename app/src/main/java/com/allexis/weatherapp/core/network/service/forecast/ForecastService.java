package com.allexis.weatherapp.core.network.service.forecast;

import android.support.annotation.NonNull;

import com.allexis.weatherapp.core.network.service.forecast.model.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.allexis.weatherapp.core.util.RequestUtil.REQ_FORECAST_BY_CITY_ID;
import static com.allexis.weatherapp.core.util.RequestUtil.REQ_FORECAST_BY_CITY_NAME;
import static com.allexis.weatherapp.core.util.RequestUtil.REQ_FORECAST_BY_LAT_LON;
import static com.allexis.weatherapp.core.util.RequestUtil.REQ_FORECAST_BY_ZIP;
import static com.allexis.weatherapp.core.util.RequestUtil.REQ_IDENTIFIER_HEADER_COLON;

/**
 * Created by allexis on 10/12/17.
 */

public interface ForecastService {

    String forecastPath = "forecast";

    @Headers(REQ_IDENTIFIER_HEADER_COLON + REQ_FORECAST_BY_LAT_LON)
    @GET(forecastPath)
    Call<ForecastResponse> getForecast(@NonNull @Query("appid") String appId,
                                       @Query("lat") double latitude,
                                       @Query("lon") double longitude);

    @Headers(REQ_IDENTIFIER_HEADER_COLON + REQ_FORECAST_BY_ZIP)
    @GET(forecastPath)
    Call<ForecastResponse> getForecast(@NonNull @Query("appid") String appId,
                                       @Query("zip") String zipCode);

    @Headers(REQ_IDENTIFIER_HEADER_COLON + REQ_FORECAST_BY_CITY_ID)
    @GET(forecastPath)
    Call<ForecastResponse> getForecastByCityId(@NonNull @Query("appid") String appId,
                                               @Query("id") int cityId);

    @Headers(REQ_IDENTIFIER_HEADER_COLON + REQ_FORECAST_BY_CITY_NAME)
    @GET(forecastPath)
    Call<ForecastResponse> getForecastByCityName(@NonNull @Query("appid") String appId,
                                                 @Query("q") String cityName);
}
