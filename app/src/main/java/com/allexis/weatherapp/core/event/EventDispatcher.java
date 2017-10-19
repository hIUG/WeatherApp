package com.allexis.weatherapp.core.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by allexis on 10/12/17.
 * <p/>
 * Simple helper class to encapsulate EventBus functionality and with possibility to add further
 * functionality if required
 */

public final class EventDispatcher {

    private EventDispatcher() {
    }

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static <T> void post(T event) {
        EventBus.getDefault().post(event);
    }
}
