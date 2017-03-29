package com.mstarc.plus.route_navigation.api;

import com.amap.api.location.AMapLocation;

/**
 * Created by Administrator on 2017/3/29.
 */

public interface UpdateMapListenter
{
	void update(AMapLocation loc);

	void fail();
}
