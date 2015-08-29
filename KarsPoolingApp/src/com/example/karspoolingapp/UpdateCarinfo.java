package com.example.karspoolingapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCarinfo extends Activity {

	MaintainSessionVariables session = new MaintainSessionVariables();
	private static final String READ_COMMENTS_URL = "http://morningchronicles.com/android/getCarDetails.php";
	private static final String UPDATION_URL = "http://morningchronicles.com/android/updateCarDetails.php";
	
	JsonParser jsonParser = new JsonParser();
	JsonParser jsonUpdate = new JsonParser();
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	private ProgressDialog pDialog;
	private JSONArray jsonCarDetails = null;
	
 
	EditText carName, carModel, licensePlateNo, carColor, seatingCapacity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_car_info);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		//String username = session.getUsername();
		 //EditText carModel = (EditText) findViewById(R.id.editText1);
		// carModel.setText(username);
		carName = (EditText) findViewById(R.id.editText1);
		carModel = (EditText) findViewById(R.id.editText2);
		licensePlateNo = (EditText) findViewById(R.id.editText3);
		carColor = (EditText) findViewById(R.id.editText4);
		seatingCapacity = (EditText) findViewById(R.id.editText5);

		JSONObject json = jsonParser.getJSONFromUrl(READ_COMMENTS_URL);
		try {
			jsonCarDetails = json.getJSONArray("cardetails");
			JSONObject c = jsonCarDetails.getJSONObject(0);

			String yusername = c.getString("username");
			String ycarname = c.getString("carno");
			String ycarmodel = c.getString("carmodel");
			String ylicenseplateno = c.getString("licenseplateno");
			String ycarcolor = c.getString("carcolor");
			String yseatingcapacity = c.getString("seatingcapacity");

			carName.setText(ycarname);
			carModel.setText(ycarmodel);
			licensePlateNo.setText(ylicenseplateno);
			carColor.setText(ycarcolor);
			seatingCapacity.setText(yseatingcapacity);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loading the details via AsyncTask
		// new LoadCarDetails().execute();
	}

	public void updateCarDetails(View view) {

		System.out.println("Inside insert details!!");
		EditText carName = (EditText) findViewById(R.id.editText1);
		//Toast.makeText(UpdateCarinfo.this, carName.getText().toString(), Toast.LENGTH_LONG).show();

		//System.out.println("Car No : " + carName.getText().toString());
		EditText carModel = (EditText) findViewById(R.id.editText2);
		EditText licenseNumber = (EditText) findViewById(R.id.editText3);
		EditText carColor = (EditText) findViewById(R.id.editText4);
		EditText seatingCapacity = (EditText) findViewById(R.id.editText5);

		new InsertToDatabase(carName, carModel, licenseNumber, carColor,
				seatingCapacity).execute();

	}

	class InsertToDatabase extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		public String str_username, str_carname, str_model, str_licenseNumber, str_color, str_seating;
		boolean failure = false;

		public InsertToDatabase(EditText carNm, EditText carModel,
				EditText licenseNumber, EditText carColor,
				EditText seatingCapacity) {
			str_username = "ankita2";
			str_carname = carNm.getText().toString();
			//Toast.makeText(UpdateCarinfo.this, str_carname, Toast.LENGTH_LONG).show();

			str_model = carModel.getText().toString();
			str_licenseNumber = licenseNumber.getText().toString();
			str_color = carColor.getText().toString();
			str_seating = seatingCapacity.getText().toString();
			
			System.out.println(str_username + " " + str_carname + " "+str_model);

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(UpdateCarinfo.this);
			pDialog.setMessage("Updating details...");
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
				params.add(new BasicNameValuePair("str_username", str_username));
				params.add(new BasicNameValuePair("str_carname", str_carname));
				params.add(new BasicNameValuePair("str_model", str_model));
				params.add(new BasicNameValuePair("str_licenseNumber", str_licenseNumber));
				params.add(new BasicNameValuePair("str_color", str_color));
				params.add(new BasicNameValuePair("str_seating",
						str_seating));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonUpdate.makeHttpRequest(UPDATION_URL,
						"POST", params);

				// check your log for json response
				Log.d("Updating new object...", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Updation Successful!", json.toString());

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
				Toast.makeText(UpdateCarinfo.this, file_url, Toast.LENGTH_LONG)
						.show();
			}

		}
	}
}
