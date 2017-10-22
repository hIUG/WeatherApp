package com.allexis.weatherapp.core.network.service.common;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.allexis.weatherapp.WeatherApplication;
import com.allexis.weatherapp.core.network.model.GsonObject;
import com.allexis.weatherapp.core.network.service.common.model.NetworkRequest;
import com.allexis.weatherapp.core.persist.CacheManager;
import com.allexis.weatherapp.core.util.RequestUtil;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by allexis on 10/22/17.
 * Intent service created to handle all network requests and caching on a separate thread. All
 * results are posted back to the main thread through the callback defined for each request.
 * This service implements a LinkedBlockingQueue to perform all calls on a thread safe way.
 * Each call is added when requested through processRequest() method and then removed when
 * started to process it.
 */

public class NetworkService<T extends GsonObject> extends IntentService {

    private static final String TAG = NetworkService.class.getSimpleName();

    private static final LinkedBlockingQueue<NetworkRequest> requestQueue = new LinkedBlockingQueue<>();

    public NetworkService() {
        super(TAG);

        //Restart service if Killed by OS
        setIntentRedelivery(true);
    }

    public static void processRequest(NetworkRequest networkRequest) {
        requestQueue.add(networkRequest);
        Context context = WeatherApplication.getInstance().getApplicationContext();
        Intent serviceIntent = new Intent(context, NetworkService.class);
        context.startService(serviceIntent);
    }

    /**
     * All log prints are to provide a better understanding of this process
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NetworkRequest<T> nextRequest = requestQueue.poll();
        Call<T> call;
        Callback<T> callback;
        T gsonObject;
        Response<T> response = null;
        String cachedResponse;
        String localTag = TAG + "@" + Thread.currentThread().getId();

        Log.d(localTag, "onHandleIntent: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (nextRequest != null) {
            call = nextRequest.getCall();
            callback = nextRequest.getCallBack();

            Log.d(localTag, "onHandleIntent: processing request:\n" + RequestUtil.getCacheSaveKey(call));

            if (RequestUtil.shouldCacheResponse(call)) {
                Log.d(localTag, "onHandleIntent: this response should be being cached... verifying if has been already");
                cachedResponse = CacheManager.getInstance().getString(RequestUtil.getCacheSaveKey(call), null);
                if (cachedResponse != null) {
                    gsonObject = GsonObject.fromJsonString(cachedResponse, nextRequest.getResponseClass());
                    response = Response.success(gsonObject);
                    Log.d(localTag, "onHandleIntent: response was cached... retrieved");
                }
            }

            if (response == null) {
                try {
                    response = call.execute();
                    Log.d(localTag, "onHandleIntent: response was not cached or shouldn't be... has been executed");
                    if (response.isSuccessful() && RequestUtil.shouldCacheResponse(response)) {
                        Log.d(localTag, "onHandleIntent: response executed should be cached... saving the result JSON body");
                        CacheManager.getInstance().put(RequestUtil.getCacheSaveKey(response), response.body().toJsonString());
                    }
                } catch (IOException e) {
                    Log.e(localTag, "onHandleIntent: Unexpected exception when trying to execute a call to retrieve it response calling callback.onFailure()", e);
                    callback.onFailure(call, e);
                }
            }
            Log.d(localTag, "onHandleIntent: end of process... executing callback.onResponse()");
            callback.onResponse(call, response);
        } else {
            Log.e(localTag, "onHandleIntent: Unexpected null request on the request queue");
        }
        Log.d(localTag, "onHandleIntent: <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }
}
