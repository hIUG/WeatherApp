package com.allexis.weatherapp.core.network.service.weather;

import android.support.annotation.NonNull;

import com.allexis.weatherapp.core.network.service.weather.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.allexis.weatherapp.core.util.RequestUtil.REQ_IDENTIFIER_HEADER_COLON;
import static com.allexis.weatherapp.core.util.RequestUtil.REQ_WEATHER_BY_CITY_ID;
import static com.allexis.weatherapp.core.util.RequestUtil.REQ_WEATHER_BY_CITY_NAME;
import static com.allexis.weatherapp.core.util.RequestUtil.REQ_WEATHER_BY_LAT_LON;
import static com.allexis.weatherapp.core.util.RequestUtil.REQ_WEATHER_BY_ZIP;

/**
 * Created by allexis on 10/12/17.
 */

public interface WeatherService {

    String weatherPath = "weather";

    @Headers(REQ_IDENTIFIER_HEADER_COLON + REQ_WEATHER_BY_LAT_LON)
    @GET(weatherPath)
    Call<WeatherResponse> getWeather(@NonNull @Query("appid") String appId,
                                     @Query("lat") double latitude,
                                     @Query("lon") double longitude);

    @Headers(REQ_IDENTIFIER_HEADER_COLON + REQ_WEATHER_BY_ZIP)
    @GET(weatherPath)
    Call<WeatherResponse> getWeather(@NonNull @Query("appid") String appId,
                                     @Query("zip") String zipCode);

    @Headers(REQ_IDENTIFIER_HEADER_COLON + REQ_WEATHER_BY_CITY_ID)
    @GET(weatherPath)
    Call<WeatherResponse> getWeatherByCityId(@NonNull @Query("appid") String appId,
                                             @Query("id") int cityId);

    @Headers(REQ_IDENTIFIER_HEADER_COLON + REQ_WEATHER_BY_CITY_NAME)
    @GET(weatherPath)
    Call<WeatherResponse> getWeatherByCityName(@NonNull @Query("appid") String appId,
                                               @Query("q") String cityName);
}
