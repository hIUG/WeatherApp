package com.allexis.weatherapp.core.network.service.group;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.allexis.weatherapp.WeatherApplication;
import com.allexis.weatherapp.core.event.EventDispatcher;
import com.allexis.weatherapp.core.network.service.common.NetworkController;
import com.allexis.weatherapp.core.network.service.group.model.GroupResponse;
import com.allexis.weatherapp.core.util.CollectionUtil;
import com.allexis.weatherapp.core.util.TextUtil;

import java.util.List;

/**
 * Created by allexis on 10/14/17.
 */

public class GroupController extends NetworkController<GroupResponse> {

    private GroupService service;

    @Override
    protected void initClient() {
        service = create(GroupService.class);
    }

    public void getGroupByCitiesId(@NonNull List<Integer> cityIds) {
        if (CollectionUtil.isEmpty(cityIds)) {
            return;
        }
        String cityIdsStr = TextUtils.join(TextUtil.SEPARATOR_COMMA, cityIds.toArray());
        execute(service.getGroupByCitiesId(WeatherApplication.getAPIkey(), cityIdsStr));
    }

    @Override
    public void processResponse(boolean successful, int responseCode, GroupResponse response) {
        EventDispatcher.post(new GroupEvent(successful, responseCode, response));
    }

    @Override
    public void processFailure() {
        EventDispatcher.post(new GroupEvent(false));
    }

    @Override
    protected Class<GroupResponse> getResponseClass() {
        return GroupResponse.class;
    }
}
