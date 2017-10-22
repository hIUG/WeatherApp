package com.allexis.weatherapp.core.network.service.common;

import com.allexis.weatherapp.core.network.model.GsonObject;
import com.allexis.weatherapp.core.network.service.common.model.NetworkRequest;

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

public abstract class NetworkController<T extends GsonObject> implements Callback<T> {

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

    protected <R> R create(final Class<R> clientClass) {
        return retrofit.create(clientClass);
    }

    protected void execute(Call<T> call) {
        NetworkService.processRequest(new NetworkRequest<>(call, this, getResponseClass()));
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        processResponse(response.isSuccessful(), response.code(), response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        processFailure();
    }

    protected abstract void processResponse(boolean successful, int responseCode, T response);

    protected abstract void processFailure();

    protected abstract void initClient();

    protected abstract Class<T> getResponseClass();
}
