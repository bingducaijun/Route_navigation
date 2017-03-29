package com.mstarc.plus.route_navigation.api;

import com.amap.api.services.help.Tip;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public interface UpdateSearchListListener
{
	void onSuccess(List<Tip> list);
    void onFail();
}
