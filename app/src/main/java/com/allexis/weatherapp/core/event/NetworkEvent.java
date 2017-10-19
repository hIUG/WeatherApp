package com.allexis.weatherapp.core.event;

/**
 * Created by allexis on 10/12/17.
 */

public abstract class NetworkEvent<T> {

    protected boolean successful;
    protected T responseObject;

    public NetworkEvent(boolean successful) {
        this.successful = successful;
    }

    public NetworkEvent(boolean successful, T responseObject) {
        this.responseObject = responseObject;
        this.successful = successful && responseObject != null;
    }

    public T getResponseObject() {
        return responseObject;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
