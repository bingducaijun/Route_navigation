package com.mstarc.plus.route_navigation.util;

import android.content.Context;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.mstarc.plus.route_navigation.api.UpdateSearchListListener;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class SearchUtil implements Inputtips.InputtipsListener
{
	static SearchUtil instance;
	private UpdateSearchListListener updateSearchListListener;

	public static SearchUtil getInstance()
	{
		if (instance == null)
			instance = new SearchUtil();
		return instance;
	}

	public void setUpdateSearchListListener(UpdateSearchListListener updateSearchListListener)
	{
		this.updateSearchListListener = updateSearchListListener;
	}

	public void searchByTips(Context context, String tipsContent, String city, UpdateSearchListListener updateSearchListListener)
	{
		setUpdateSearchListListener(updateSearchListListener);
		searchByTips(context, tipsContent, city);
	}

	public void searchByTips(Context context, String tipsContent, String city)
	{
		InputtipsQuery inputtipsQuery = new InputtipsQuery(tipsContent, city);// 初始化一个输入提示搜索对象，并传入参数
		inputtipsQuery.setCityLimit(true);// 将获取到的结果进行城市限制筛选
		Inputtips inputtips = new Inputtips(context, inputtipsQuery);// 定义一个输入提示对象，传入当前上下文和搜索对象
		inputtips.setInputtipsListener(this);// 设置输入提示查询的监听，实现输入提示的监听方法onGetInputtips()
		inputtips.requestInputtipsAsyn();// 输入查询提示的异步接口实现
	}

	@Override
	public void onGetInputtips(List<Tip> resultList, int returnCode)
	{
		if (returnCode == AMapException.CODE_AMAP_SUCCESS)
		{
			updateSearchListListener.onSuccess(resultList);
		} else
		{
			updateSearchListListener.onFail();
		}
	}
}
