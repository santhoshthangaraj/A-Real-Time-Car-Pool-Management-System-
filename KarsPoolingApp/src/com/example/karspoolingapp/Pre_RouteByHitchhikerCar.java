package com.example.karspoolingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;


public class Pre_RouteByHitchhikerCar extends Activity {

		


    private String CarNumber ;
    private EditText edit_text;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_routebyhitchhikercar);
        
        
     }
	
	public void onButtonClickCar(View V)
	{
		edit_text   = (EditText)findViewById(R.id.editText1);
        CarNumber = edit_text.getText().toString();
        System.out.println("Value for car number is----"+CarNumber);
       

		Intent i = new Intent(getApplicationContext(),RouteByHitchhikerCar.class);
		i.putExtra("pre_end_point", CarNumber);    
		startActivity(i);
		
	}
	
	
}
