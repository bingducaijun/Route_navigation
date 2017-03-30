package com.mstarc.plus.route_navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpeachActivity extends Activity
{
	TextView inputHintTv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speach);
		inputHintTv = (TextView) findViewById(R.id.tv_input_hint);

		final Button speechBtn = (Button) findViewById(R.id.btn_speech);
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				speechBtn.setVisibility(View.VISIBLE);
			}
		}, 5000);

		speechBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				startActivity(new Intent(SpeachActivity.this, SearchActivity.class).putExtra("targetAdd", "福林苑"));
			}
		});
	}
}
