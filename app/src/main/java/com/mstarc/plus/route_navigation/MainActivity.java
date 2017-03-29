package com.mstarc.plus.route_navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.mstarc.plus.route_navigation.api.UpdateMapListenter;
import com.mstarc.plus.route_navigation.base.MyApp;
import com.mstarc.plus.route_navigation.util.LocationUtil;

/**
 * AMapV2地图中介绍如何显示一个基本地图
 */
public class MainActivity extends Activity implements UpdateMapListenter
{
	private MapView mapView;
	private AMap aMap;
	LocationUtil locationUtil = LocationUtil.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
		// MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);

		mapView = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		initMap();
		locationUtil.setUpdateMapListenter(this);
	}

	/**
	 * CameraPostion创建：CameraPosition LUJIAZUI = new
	 * CameraPosition.Builder().target(Constants.SHANGHAI).zoom(18).bearing(0).tilt(30).build();
	 * //target为LatLng位置，zoom为缩放级别，bearing为可视区域指向的方向，以角度为单位，正北方向为0度，tilt为目标可视区域的倾斜度，以角度为单位。）
	 * 
	 * @param targetLatLng
	 */
	private void changeLocationCenter(LatLng targetLatLng)
	{
		changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(targetLatLng, 15, 30, 30)), null);
		aMap.clear();
		aMap.addMarker(new MarkerOptions().position(targetLatLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

	}

	/**
	 * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
	 */
	private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback)
	{
		aMap.moveCamera(update);
	}

	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.btn_self_location:
			locationUtil.startLocation(MainActivity.this);
			break;
		case R.id.btn_speech_input:
			startActivity(new Intent(MainActivity.this, SpeechInputActivity.class));
			break;
		}
	}

	/**
	 * 初始化AMap对象
	 */
	private void initMap()
	{
		if (aMap == null)
		{
			aMap = mapView.getMap();
			aMap.getUiSettings().setZoomControlsEnabled(false);
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		mapView.onResume();
		locationUtil.startLocation(MainActivity.this);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause()
	{
		super.onPause();
		mapView.onPause();
		locationUtil.destroyLocation();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mapView.onDestroy();
		locationUtil.destroyLocation();
	}

	@Override
	public void update(AMapLocation loc)
	{
		LatLng currLocation = new LatLng(loc.getLatitude(), loc.getLongitude());
		((MyApp) getApplicationContext()).currCity = loc.getCity();
		((MyApp) getApplicationContext()).currLocation = currLocation;
		changeLocationCenter(currLocation);
	}

	@Override
	public void fail() {
		Toast.makeText(this,R.string.location_fail,Toast.LENGTH_SHORT).show();
	}
}
