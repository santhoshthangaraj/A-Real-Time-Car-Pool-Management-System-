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
import android.widget.EditText;
import android.widget.Toast;



public class CarinfoActivity extends Activity {

	private static final String INSERTION_URL = "http://morningchronicles.com/android/insertCarDetails.php";
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	JsonParser jsonParser = new JsonParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carinfo);
}
	
public void insertDetails(View view) {
		
	
	System.out.println("Inside insert details!!");
		
		EditText carNumber = (EditText) findViewById(R.id.editText1);
		EditText carModel = (EditText) findViewById(R.id.editText2);
		EditText licenseNumber = (EditText) findViewById(R.id.editText3);
		EditText carColor = (EditText) findViewById(R.id.editText4);
		EditText seatingCapacity = (EditText) findViewById(R.id.editText5);

		new InsertToDatabase(carNumber, carModel, licenseNumber, carColor,
				seatingCapacity).execute();

	}

	class InsertToDatabase extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		String username, carNo, model, licensePlate, color, seatingLimit;
		boolean failure = false;

		public InsertToDatabase(EditText carNumber, EditText carModel,
				EditText licenseNumber, EditText carColor,
				EditText seatingCapacity) {
			username = "ankita2";
			carNo = carNumber.getText().toString();
			model = carModel.getText().toString();
			licensePlate = licenseNumber.getText().toString();
			color = carColor.getText().toString();
			seatingLimit = seatingCapacity.getText().toString();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CarinfoActivity.this);
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
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("carname", carNo));
				params.add(new BasicNameValuePair("carmodel", model));
				params.add(new BasicNameValuePair("licenseplate", licensePlate));
				params.add(new BasicNameValuePair("carcolor", color));
				params.add(new BasicNameValuePair("seatingcapacity", seatingLimit));

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
				Toast.makeText(CarinfoActivity.this, file_url, Toast.LENGTH_LONG).show();
			}

		}
	}

}
