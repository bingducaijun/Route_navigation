package com.mstarc.plus.route_navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RouteSelectActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_select);
	}

	public void onClick(View view)
	{
		switch (view.getId())
		{
		case R.id.tv_driver_route:
			startActivity(new Intent(RouteSelectActivity.this, SingleRouteCalculateActivity.class));
			break;
		case R.id.tv_walk_route:
			startActivity(new Intent(RouteSelectActivity.this, WalkRouteCalculateActivity.class));
			break;
		}
	}
}
