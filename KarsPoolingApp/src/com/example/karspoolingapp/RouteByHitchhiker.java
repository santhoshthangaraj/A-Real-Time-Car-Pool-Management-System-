package com.example.karspoolingapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RouteByHitchhiker extends Activity {

	MaintainSessionVariables session = new MaintainSessionVariables();
	private static final String READ_COMMENTS_URL2 = "http://morningchronicles.com/android/getTripDetails.php";
	
	private static final String INSERTION_URL = "http://morningchronicles.com/android/insertHitchhikerDetails.php";
	//private static final String INSERTION_URL = "http://morningchronicles.com/android/insertHitchhikerTripDetails.php";
	//private static final String GET_VACANCY_URL = "http://morningchronicles.com/android/getVacancy.php";
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	JsonParser jsonParser = new JsonParser();
	JsonParser jsonParser2 = new JsonParser();
	// JsonParser jsonUpdate = new JsonParser();


	private JSONArray jsonTripDetails = null;
	

	RelativeLayout rl;
	RadioGroup rg;
	String new_sp;
	String new_dp;
	String details;
	int jsonLength;
	String session_username;
	JSONObject json;
	String username = "ankita2";

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.routebyhitchhiker);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		new_sp = bundle.getString("pre_strt_point");
		new_dp = bundle.getString("pre_end_point");
		//session_username = session.getUsername();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		// code for dynamic radio button generation
		final RadioButton[] rb = new RadioButton[100];
		rl = (RelativeLayout) findViewById(R.id.rl);
		rg = new RadioGroup(this);
		
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("start_point", new_sp));
		params.add(new BasicNameValuePair("end_point", new_dp));

		json = jsonParser.makeHttpRequest(READ_COMMENTS_URL2, "POST", params);
		params.clear();

		try {
			jsonTripDetails = json.getJSONArray("route");
			System.out.println(jsonTripDetails);
			int k = jsonTripDetails.length();
			if (k > 0) {
				for (int i = 0; i < jsonTripDetails.length(); i++) {
					JSONObject c = jsonTripDetails.getJSONObject(i);

					String parent_username_str = c.getString("username");
					String route = c.getString("route");
					String timing = c.getString("timing");
					String seating = c.getString("seating_capacity");
					rb[i] = new RadioButton(this);
					rg.addView(rb[i]);
					rb[i].setText(parent_username_str + "," + route + "," + timing
							+ "," + seating);
					params.clear();

				}
				rl.addView(rg);
				rl.setPadding(50, 50, 50, 50);
			} else {
				Toast.makeText(RouteByHitchhiker.this,
						"No Trip available on these routes", Toast.LENGTH_LONG)
						.show();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		addListenerOnButton();

	}

	public void addListenerOnButton() {

		Button btnDisplay = (Button) findViewById(R.id.button1);
		btnDisplay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// get selected radio button from radioGroup
				int selectedId = rg.getCheckedRadioButtonId();

				System.out.println("selected Id is" + selectedId);


				try {
					JSONObject c = jsonTripDetails
							.getJSONObject(selectedId - 1);
					String parent_username_str = c.getString("username");
					String route = c.getString("route");
					String timing = c.getString("timing");
					String seating_capacity = c.getString("seating_capacity");

					
					
					
					new InsertToDatabase(username,parent_username_str,route, timing, seating_capacity,
							new_sp, new_dp).execute();
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

	}
	
	
	
	class InsertToDatabase extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		
		String s_username,s_parent_username_str,s_route, s_timing, s_seating_capacity,s_new_sp, s_new_dp;
		boolean failure = false;

		public InsertToDatabase(String username,String parent_username_str,String route,String timing,String seating_capacity,
				String new_sp,String new_dp) {
			
			s_username = username;
			s_parent_username_str = parent_username_str;
			s_route = route;
			s_timing = timing;
			s_seating_capacity = seating_capacity;
			s_new_sp =new_sp;
			s_new_dp = new_dp;
		System.out.println("heyyy " + s_username);
					}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RouteByHitchhiker.this);
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
			System.out.println("in background i am");
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", s_username));
				params.add(new BasicNameValuePair("parent_username_str", s_parent_username_str));
				params.add(new BasicNameValuePair("route", s_route));
				params.add(new BasicNameValuePair("timing", s_timing));
				params.add(new BasicNameValuePair("start_point", s_new_sp));
				params.add(new BasicNameValuePair("end_point", s_new_dp));

				System.out.println("param value is --->>"+  params);

				//System.out.println("came till herrrrrrreeeeeeee.....");
				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject jsonVacancy = jsonParser2.makeHttpRequest(INSERTION_URL,"POST", params);

				// check your log for json response
				Log.d("Inserting...", jsonVacancy.toString());

				// json success tag
				success = jsonVacancy.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Insertion Successful!", jsonVacancy.toString());
					// Intent i = new Intent(Login.this, ReadComments.class);
					// finish();
					// startActivity(i);
					return jsonVacancy.getString(TAG_MESSAGE);
				} else {
					Log.d("Insertion Failed!", jsonVacancy.getString(TAG_MESSAGE));
					return jsonVacancy.getString(TAG_MESSAGE);

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
				Toast.makeText(RouteByHitchhiker.this, file_url, Toast.LENGTH_LONG).show();
			}

		}

		
}

}

	
	
	
	

