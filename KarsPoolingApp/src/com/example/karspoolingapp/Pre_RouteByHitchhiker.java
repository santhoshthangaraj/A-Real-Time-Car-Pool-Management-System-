package com.example.karspoolingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class Pre_RouteByHitchhiker extends Activity {

		

	String[] sp_array;
    Spinner sp;
    String[] dp_array;
    Spinner dp;
   
    
    private String main_starting_point ;
    private String main_ending_point ;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_routebyhitchhiker);
        
        sp=(Spinner) findViewById(R.id.spinner2);
        sp_array = getResources().getStringArray(R.array.startpoint);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sp_array);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new OnItemSelectedListener (){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index = arg0.getSelectedItemPosition();
				main_starting_point=sp_array[index];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
        
      //second spinner
        dp=(Spinner) findViewById(R.id.spinner1);
        dp_array = getResources().getStringArray(R.array.endpoint);
        
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,dp_array);
        dp.setAdapter(adapter2);
        dp.setOnItemSelectedListener(new OnItemSelectedListener (){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index = arg0.getSelectedItemPosition();
				main_ending_point= dp_array[index];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
       
     }
	
	public void OnClickEventNew(View V)
	{
		

		Intent i = new Intent(getApplicationContext(),RouteByHitchhiker.class);
		i.putExtra("pre_strt_point", main_starting_point);    
		i.putExtra("pre_end_point", main_ending_point);    
		startActivity(i);
		
	}
	
	
}
