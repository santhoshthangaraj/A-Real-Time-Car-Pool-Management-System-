package com.example.karspoolingapp;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class CarownerActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carowner);

	}
	
	public void car_fill_details(View v)
	{
		Intent i = new Intent(getApplicationContext(),
				CarinfoActivity.class);
		startActivity(i);
		
	}
	public void car_plan_trip(View v)
	{
		Intent i = new Intent(getApplicationContext(),
				RouteByCarowner.class);
		startActivity(i);
		
	}
	public void car_track_hh(View v)
	{
		Intent i = new Intent(getApplicationContext(),
				MainActivity.class);
		startActivity(i);
		
	}
	public void car_cancel_trip(View v)
	{
		Intent i = new Intent(getApplicationContext(),
				CancelTripByCarowner.class);
			startActivity(i);
		
	}
	public void car_update_details(View v)
	{
		Intent i = new Intent(getApplicationContext(),
				UpdateCarinfo.class);
		startActivity(i);
		
		
	}
	public void car_change_trip(View v)
	{
		Intent i = new Intent(getApplicationContext(),
				ChangeRouteByCarowner.class);
		startActivity(i);
		
	}
	
}
