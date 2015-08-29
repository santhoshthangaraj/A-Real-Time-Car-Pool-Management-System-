package com.example.karspoolingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Email extends Activity{

	/*public void sendEmail( ){
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"bhatnagar.ankita2209@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "Test mail");
		i.putExtra(Intent.EXTRA_TEXT   , "Hi this is a test mail");
		try {
		    startActivity(i.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(Email.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	
	
*/	



	protected void sendEmail() {
	      Log.i("Send email", "");

	      String[] TO = {"bhatnagar.ankita2209@gmail.com"};
	      String[] CC = {"bhatnagar.ankita2209@gmail.com"};
	      Intent emailIntent = new Intent(Intent.ACTION_SEND);
	      emailIntent.setData(Uri.parse("mailto:"));
	      emailIntent.setType("text/plain");


	      emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
	      emailIntent.putExtra(Intent.EXTRA_CC, CC);
	      emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
	      emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

	      try {
	         startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	         finish();
	         Log.i("Finished sending email...", "");
	      } catch (android.content.ActivityNotFoundException ex) {
	         Toast.makeText(Email.this,"There is no email client installed.", Toast.LENGTH_SHORT).show();
	      }
	   }

}
	
	
	
