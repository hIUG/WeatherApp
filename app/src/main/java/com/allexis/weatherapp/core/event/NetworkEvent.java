package com.allexis.weatherapp.core.event;

/**
 * Created by allexis on 10/12/17.
 */

public abstract class NetworkEvent<T> {

    private final int code;
    private final boolean successful;
    private final T responseObject;

    public NetworkEvent(boolean successful, int code, T responseObject) {
        this.responseObject = responseObject;
        this.code = code;
        this.successful = successful && responseObject != null;
    }

    public T getResponseObject() {
        return responseObject;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
