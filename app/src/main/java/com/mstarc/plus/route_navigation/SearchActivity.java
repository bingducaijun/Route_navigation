package com.mstarc.plus.route_navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.help.Tip;
import com.mstarc.plus.route_navigation.api.UpdateSearchListListener;
import com.mstarc.plus.route_navigation.base.MyApp;
import com.mstarc.plus.route_navigation.util.SearchUtil;
import com.situ.android.adapter.CommonAdapter;
import com.situ.android.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity implements UpdateSearchListListener
{
	SearchUtil searchUtil = SearchUtil.getInstance();
	ListView searchLv;
	List<Tip> searchList = new ArrayList<Tip>();
	CommonAdapter commonAdapter;
	TextView searchHintTv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		searchLv = (ListView) findViewById(R.id.lv_input_match_result);
		searchHintTv = (TextView) findViewById(R.id.tv_search_hint);
		commonAdapter = new CommonAdapter(this, R.layout.item_search_list, searchList)
		{
			@Override
			public void convert(ViewHolder viewHolder, int i, Object obj)
			{
				Log.d("resulttt", "qqqqqqqqqqqqqqqq" + ((Tip) obj).getName());
				final Tip tip = searchList.get(i);
				viewHolder.setText(R.id.tv_add_name, tip.getName());
				viewHolder.setText(R.id.tv_add_detail, tip.getAddress());
				viewHolder.getView(R.id.rly_add_item).setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						Log.d("resulttt", tip.getPoint().getLatitude() + "," + tip.getPoint().getLongitude());
						((MyApp) SearchActivity.this.getApplicationContext()).tarLocation = new NaviLatLng(tip.getPoint().getLatitude(), tip.getPoint().getLongitude());
						startActivity(new Intent(SearchActivity.this, RouteSelectActivity.class));
					}
				});
			}
		};
		searchLv.setAdapter(commonAdapter);
		Log.d("resulttt", "qqqqqqqqqqqqqqqq" + ((MyApp) this.getApplicationContext()).currCity);

		searchUtil.searchByTips(this, getIntent().getStringExtra("targetAdd"), ((MyApp) this.getApplicationContext()).currCity, this);
	}

	@Override
	protected void onResume()
	{
		super.onResume();

	}

	@Override
	public void onSuccess(List<Tip> list)
	{
		searchLv.setVisibility(View.VISIBLE);
		searchHintTv.setVisibility(View.INVISIBLE);
		searchList.removeAll(searchList);
		searchList.addAll(list);
		commonAdapter.notifyDataSetChanged();
	}

	@Override
	public void onFail()
	{
		searchHintTv.setVisibility(View.VISIBLE);
		searchLv.setVisibility(View.INVISIBLE);
		searchHintTv.setText(R.string.search_fail);
	}
}
