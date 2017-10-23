package com.allexis.weatherapp.core.event.common;

/**
 * Created by allexis on 10/16/17.
 */

public class PermissionGrantedEvent {

    private String permission;

    public PermissionGrantedEvent(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
