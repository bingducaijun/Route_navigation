package com.mstarc.plus.route_navigation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.services.help.Tip;
import com.mstarc.plus.route_navigation.api.UpdateSearchListListener;
import com.mstarc.plus.route_navigation.base.MyApp;
import com.mstarc.plus.route_navigation.util.SearchUtil;
import com.situ.android.adapter.CommonAdapter;
import com.situ.android.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SpeechInputActivity extends Activity implements UpdateSearchListListener {
    SearchUtil searchUtil = SearchUtil.getInstance();
    ListView searchLv;
    List<Tip> searchList = new ArrayList<Tip>();
    CommonAdapter commonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_input);
        searchLv = (ListView) findViewById(R.id.lv_input_match_result);
        commonAdapter = new CommonAdapter(this, R.layout.item_search_list, searchList) {
            @Override
            public void convert(ViewHolder viewHolder, int i, Object obj) {
                Log.d("resulttt", "qqqqqqqqqqqqqqqq" + ((Tip) obj).getName());
                Tip tip = searchList.get(i);
                viewHolder.setText(R.id.tv_add_name, tip.getName());
                viewHolder.setText(R.id.tv_add_detail, tip.getAddress());
            }
        };
        searchLv.setAdapter(commonAdapter);
        Log.d("resulttt", "qqqqqqqqqqqqqqqq" + ((MyApp) this.getApplicationContext()).currCity);
        searchUtil.searchByTips(this, "福林苑小区", ((MyApp) this.getApplicationContext()).currCity, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onSuccess(List<Tip> list) {
        searchList.removeAll(searchList);
        searchList.addAll(list);
        commonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFail() {
        Toast.makeText(this, R.string.search_fail, Toast.LENGTH_SHORT).show();
    }
}
