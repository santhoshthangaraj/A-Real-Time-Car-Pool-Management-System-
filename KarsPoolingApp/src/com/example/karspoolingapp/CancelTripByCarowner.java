
package com.example.karspoolingapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;



public class CancelTripByCarowner extends Activity {
	private static final String DELETION_URL = "http://morningchronicles.com/android/deleteCarOwnerPlanTrip.php";
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	JsonParser jsonParser = new JsonParser();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canceltripbycarowner);

}
	public void coCancelCarTrip(View V)
	{
		
		
		System.out.println("Inside insert details!!");
			
			
			new DeleteFromDatabase().execute();

		}

		class DeleteFromDatabase extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */

			String sent_username;
			boolean failure = false;

			public DeleteFromDatabase() {
				sent_username = "ankita2";
				
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(CancelTripByCarowner.this);
				pDialog.setMessage("Deleting details...");
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
					params.add(new BasicNameValuePair("username", sent_username));
					
					Log.d("request!", "starting");
					// getting product details by making HTTP request
					JSONObject json = jsonParser.makeHttpRequest(DELETION_URL,
							"POST", params);

					// check your log for json response
					Log.d("Deleting...", json.toString());

					// json success tag
					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						Log.d("Deletion Successful!", json.toString());
						// Intent i = new Intent(Login.this, ReadComments.class);
						// finish();
						// startActivity(i);
						return json.getString(TAG_MESSAGE);
					} else {
						Log.d("Deletion Failed!", json.getString(TAG_MESSAGE));
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
					Toast.makeText(CancelTripByCarowner.this, file_url, Toast.LENGTH_LONG).show();
				}

			}
		}

	}
