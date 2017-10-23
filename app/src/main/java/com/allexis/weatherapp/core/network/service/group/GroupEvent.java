package com.allexis.weatherapp.core.network.service.group;

import com.allexis.weatherapp.core.event.NetworkEvent;
import com.allexis.weatherapp.core.network.service.group.model.GroupResponse;

/**
 * Created by allexis on 10/14/17.
 */

public class GroupEvent extends NetworkEvent<GroupResponse> {

    public GroupEvent(boolean successful) {
        this(successful, 0, null);
    }

    public GroupEvent(boolean successful, GroupResponse responseObject) {
        this(successful, 0, responseObject);
    }

    public GroupEvent(boolean successful, int code, GroupResponse body) {
        super(successful, code, body);
    }
}
