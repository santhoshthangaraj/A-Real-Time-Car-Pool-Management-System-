package com.example.karspoolingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HitchhikerpageActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hitchhikerpage);
        
	}
        
			public void hhCarPlanTrip(View v) {
				Intent i = new Intent(getApplicationContext(),
						HHSearchType.class);
				startActivity(i);
			}
		
			public void hhCarCancelTrip(View v) {
				Intent i = new Intent(getApplicationContext(),
						CancelTripByHitchhiker.class);
				startActivity(i);
			}
			
								
			public void hhShowToken(View v) {
				Intent i = new Intent(getApplicationContext(),
						ShowToken.class);
				startActivity(i);
			}
			
			public void hhTrackCo(View v) {
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);
			}
					
}
