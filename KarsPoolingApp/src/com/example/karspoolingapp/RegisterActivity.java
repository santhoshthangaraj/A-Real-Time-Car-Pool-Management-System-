package com.example.karspoolingapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private static final String INSERTION_URL = "http://morningchronicles.com/android/register_verification.php";
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	JsonParser jsonParser = new JsonParser();
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		TextView registerScreen=(TextView)findViewById(R.id.link_to_login);
        registerScreen.setOnClickListener(new View.OnClickListener(){	public void onClick(View v)
        	{
        		Intent i=new Intent(getApplicationContext(),LoginActivity.class);
        		startActivity(i);   	}
        });
        
	}
	
	
	public void registerActivity(View view) {
		
		
		//System.out.println("Inside insert details!!");
			
			EditText firstname = (EditText) findViewById(R.id.editText1);
			EditText lastname= (EditText) findViewById(R.id.editText2);
			EditText dob= (EditText) findViewById(R.id.editText3);
			EditText unccmailid = (EditText) findViewById(R.id.editText4);
			EditText username = (EditText) findViewById(R.id.editText5);
			int i1 = (int) (Math.random() * (1000-9999));
				
			//int i1 = 444444;
			String password = Integer.toString(i1);
				System.out.println(Integer.toString(i1));
				
			new InsertToDatabase(username,password, firstname, lastname, dob,
					unccmailid).execute();

		}

		class InsertToDatabase extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */

			
			String str_username, str_firstname, str_lastname, str_dob, str_uncc, str_pass;
			boolean failure = false;

			public InsertToDatabase(EditText username,String password, EditText firstname,
					EditText lastname, EditText dateofbirth,
					EditText unccmailid) {
				
				str_username = username.getText().toString();
				str_firstname = firstname.getText().toString();
				str_lastname = lastname.getText().toString();
				str_dob = dateofbirth.getText().toString();
				str_uncc = unccmailid.getText().toString();
				str_pass = password;
				
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(RegisterActivity.this);
				pDialog.setMessage("Inserting details...");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			@Override
			protected String doInBackground(String... args) {
				// TODO Auto-generated method stub
				// Check for success tag
				int success;

				try {
					// Building Parameters
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("username", str_username));
					params.add(new BasicNameValuePair("password", str_pass));
					params.add(new BasicNameValuePair("firstname", str_firstname));
					params.add(new BasicNameValuePair("lastname", str_lastname));
					params.add(new BasicNameValuePair("dateofbirth", str_dob));
					params.add(new BasicNameValuePair("unccmailid", str_uncc));


					//System.out.println("came till herrrrrrreeeeeeee.....");
					Log.d("request!", "starting");
					// getting product details by making HTTP request
					JSONObject json = jsonParser.makeHttpRequest(INSERTION_URL,
							"POST", params);

					// check your log for json response
					Log.d("Inserting...", json.toString());

					// json success tag
					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						Log.d("Insertion Successful!", json.toString());
						// Intent i = new Intent(Login.this, ReadComments.class);
						// finish();
						// startActivity(i);
						return json.getString(TAG_MESSAGE);
					} else {
						Log.d("Insertion Failed!", json.getString(TAG_MESSAGE));
						return json.getString(TAG_MESSAGE);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;

			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			@Override
			protected void onPostExecute(String file_url) {
				// dismiss the dialog once product deleted
				pDialog.dismiss();
				if (file_url != null) {
					Toast.makeText(RegisterActivity.this, file_url, Toast.LENGTH_LONG).show();
				}

			}
		}

	}
