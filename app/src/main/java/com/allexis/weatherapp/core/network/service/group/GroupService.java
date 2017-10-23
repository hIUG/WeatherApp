package com.allexis.weatherapp.core.network.service.group;

import android.support.annotation.NonNull;

import com.allexis.weatherapp.core.network.service.group.model.GroupResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.allexis.weatherapp.core.util.RequestUtil.REQ_GROUP_BY_CITIES_ID;
import static com.allexis.weatherapp.core.util.RequestUtil.REQ_IDENTIFIER_HEADER_COLON;

/**
 * Created by allexis on 10/12/17.
 */

public interface GroupService {

    String groupPath = "group";

    @Headers(REQ_IDENTIFIER_HEADER_COLON + REQ_GROUP_BY_CITIES_ID)
    @GET(groupPath)
    Call<GroupResponse> getGroupByCitiesId(@NonNull @Query("appid") String appId,
                                           @NonNull @Query("id") String citiesId);
}
