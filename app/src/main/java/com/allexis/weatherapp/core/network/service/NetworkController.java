package com.allexis.weatherapp.core.network.service;

import android.util.Log;

import com.allexis.weatherapp.core.util.RequestUtil;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by allexis on 10/13/17.
 * <p>
 * Base network controller to be extended by other network controllers, protected access strings to
 * build a different custom URL if needed by a child controller. The default one shared among all
 * controllers is defined here.
 * Custom controllers should override getURL() in case they want to use a custom URL
 */

public abstract class NetworkController<R> implements Callback<R> {

    protected static final String PROTOCOL_HTTPS = "http://";
    protected static final String DOMAIN = "api.openweathermap.org/";
    public static final String BASE_URL_IMG = PROTOCOL_HTTPS + DOMAIN + "img/w/";
    private static final String TAG = NetworkController.class.getSimpleName();
    private static final String BASE_URL = PROTOCOL_HTTPS + DOMAIN + "data/2.5/";

    /**
     * OkHttp and Retrofit instances in case a custom behaviour, interceptor, etc needs to be done.
     */
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    public NetworkController() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        initClient();
    }

    protected <T> T create(final Class<T> clientClass) {
        return retrofit.create(clientClass);
    }

    @Override
    public void onResponse(Call<R> call, Response<R> response) {
        String reqId = response.raw().request().header(RequestUtil.REQ_IDENTIFIER_HEADER);
        boolean successful = response.isSuccessful();
        boolean shouldCache = RequestUtil.shouldCacheResponse(reqId);
        if (successful && shouldCache) {

        }
        processResponse(response);
    }

    @Override
    public void onFailure(Call<R> call, Throwable t) {
        Log.d(TAG, "onFailure: Unexpected failure while requesting " + getClass().getCanonicalName(), t);
        processFailure();
    }

    protected abstract void processResponse(Response<R> response);

    protected abstract void processFailure();

    protected abstract void initClient();
}
