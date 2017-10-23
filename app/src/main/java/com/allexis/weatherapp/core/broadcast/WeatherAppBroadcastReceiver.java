package com.allexis.weatherapp.core.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.allexis.weatherapp.R;

public class WeatherAppBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
            Toast.makeText(context, R.string.broadcast_received_power_connected, Toast.LENGTH_LONG).show();

        if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED))
            Toast.makeText(context, R.string.broadcast_received_power_disconnected, Toast.LENGTH_LONG).show();

        if (intent.getAction().equals(Intent.ACTION_REBOOT))
            Toast.makeText(context, R.string.broadcast_received_reboot, Toast.LENGTH_LONG).show();
    }
}
