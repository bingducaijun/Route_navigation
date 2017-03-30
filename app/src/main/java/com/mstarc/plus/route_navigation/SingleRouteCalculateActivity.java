package com.mstarc.plus.route_navigation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.mstarc.plus.route_navigation.base.BaseActivity;

public class SingleRouteCalculateActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_basic_navi);
		mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);

		// 设置布局完全不可见
		AMapNaviViewOptions viewOptions = mAMapNaviView.getViewOptions();
		// AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
		viewOptions.setLayoutVisible(false);
		// 主动隐藏导航光柱
		viewOptions.setTrafficBarEnabled(false);
		// viewOptions.setTrafficLine(false);
		mAMapNaviView.onCreate(savedInstanceState);
		mAMapNaviView.setAMapNaviViewListener(this);

		viewOptions.setTilt(0);
		mAMapNaviView.setViewOptions(viewOptions);
	}

	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.btn_map_zoomin:
			Log.e("resulttt", "btn_map_zoomin");
			mAMapNaviView.getMap().animateCamera(CameraUpdateFactory.zoomIn());
			break;
		case R.id.btn_map_zoomout:
			Log.e("resulttt", "btn_map_zoomout");
			mAMapNaviView.getMap().animateCamera(CameraUpdateFactory.zoomOut());
			break;
		}
	}

	@Override
	public void onInitNaviSuccess()
	{
		super.onInitNaviSuccess();
		/**
		 * 方法: int strategy=mAMapNavi.strategyConvert(congestion,
		 * avoidhightspeed, cost, hightspeed, multipleroute); 参数:
		 *
		 * @congestion 躲避拥堵
		 * @avoidhightspeed 不走高速
		 * @cost 避免收费
		 * @hightspeed 高速优先
		 * @multipleroute 多路径
		 *
		 *                说明:
		 *                以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
		 *                注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
		 */
		int strategy = 0;
		try
		{
			// 再次强调，最后一个参数为true时代表多路径，否则代表单路径
			strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);

	}

	@Override
	public void onCalculateRouteSuccess()
	{
		super.onCalculateRouteSuccess();
		mAMapNavi.startNavi(NaviType.EMULATOR);
	}
}
