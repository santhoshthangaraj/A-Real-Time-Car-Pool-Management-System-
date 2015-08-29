package com.example.karspoolingapp;

/*import com.example.karsapp.R;*/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class Usertype extends Activity{
	private RadioGroup radio_Group;
	  private RadioButton radio_Button;
	  private Button btnDisplay;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertype);
        
    	addListenerOnButton();

       
     }

	
			public void addListenerOnButton() {
				 
				radio_Group = (RadioGroup) findViewById(R.id.radioGroup);
				btnDisplay = (Button) findViewById(R.id.btnDisplay);
			 
				btnDisplay.setOnClickListener(new OnClickListener() {
			 
					@Override
					public void onClick(View v) {
			 
					        // get selected radio button from radioGroup
						int selectedId = radio_Group.getCheckedRadioButtonId();
						
						System.out.println(selectedId);
			 
						// find the radiobutton by returned id
					        radio_Button = (RadioButton) findViewById(selectedId);
					        String value = radio_Button.getText().toString();
			 
						if (value.equalsIgnoreCase("Hitchhiker"))
						{
							Intent i=new Intent(getApplicationContext(),HitchhikerpageActivity.class);
				     		startActivity(i);
						}
						else
						{Intent i=new Intent(getApplicationContext(),CarownerActivity.class);
			     		startActivity(i);}
			 
					}
			 
				});
			 
			  }

}
