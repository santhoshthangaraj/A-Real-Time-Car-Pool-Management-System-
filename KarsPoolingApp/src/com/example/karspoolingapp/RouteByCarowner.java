package com.example.karspoolingapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.karspoolingapp.CarinfoActivity.InsertToDatabase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class RouteByCarowner extends Activity {
	
	private static final String INSERTION_URL = "http://morningchronicles.com/android/insertCarOwnerPlanTrip.php";
	private ProgressDialog pDialog;
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	JsonParser jsonParser = new JsonParser();

	String[] sp_array;
    Spinner sp;
    String[] dp_array;
    Spinner dp;
    String[] r_array;
    Spinner r;
    String[] t_array;
    Spinner t;
    
    String main_starting_point;
    String main_ending_point;
    String main_route;
    String main_timing;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route);
        //First spinner
        sp=(Spinner) findViewById(R.id.start_point);
        sp_array = getResources().getStringArray(R.array.startpoint);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sp_array);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new OnItemSelectedListener (){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index = arg0.getSelectedItemPosition();
				main_starting_point =  sp_array[index];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
        
      //second spinner
        dp=(Spinner) findViewById(R.id.end_point_spinner);
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
        
      //third spinner
        r=(Spinner) findViewById(R.id.route_spinner);
        r_array = getResources().getStringArray(R.array.route);
        
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,r_array);
        r.setAdapter(adapter3);
        r.setOnItemSelectedListener(new OnItemSelectedListener (){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index = arg0.getSelectedItemPosition();
				main_route= r_array[index];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
        
        //fourth spinner
        t=(Spinner) findViewById(R.id.time_spinner);
        t_array = getResources().getStringArray(R.array.time);
        
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,t_array);
        t.setAdapter(adapter4);
        t.setOnItemSelectedListener(new OnItemSelectedListener (){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index = arg0.getSelectedItemPosition();
				main_timing = t_array[index];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});
        
        
}
	
	public int checkOnPaths(String start_point1, String end_point1,
			String route1, String timing1)
	{
		
		if (start_point1.equalsIgnoreCase(end_point1))
		{
			Toast.makeText(getBaseContext(), "Start Point and End Point can't be the same", Toast.LENGTH_LONG).show();

			return 0;}
		else
		return 1;
		
	}
	
public void coPlanTrip(View V)	
	{
	int a = checkOnPaths(main_starting_point, main_ending_point, main_route,main_timing);
		if (a==1)
		{
			new InsertToDatabase(main_starting_point, main_ending_point, main_route,main_timing).execute();
		}
		else
		{
			Intent i = new Intent(getApplicationContext(),
					RouteByCarowner.class);
			startActivity(i);
		}
		//Toast.makeText(getBaseContext(), "You selected" + main_starting_point +main_ending_point +main_route +main_timing, Toast.LENGTH_LONG).show();
}
	
	class InsertToDatabase extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		String username, start_point, end_point, route, timing;
		boolean failure = false;

		public InsertToDatabase(String start_point1, String end_point1,
				String route1, String timing1) {
			username = "ankita2";
			start_point = start_point1;
			end_point = end_point1;
			route = route1;
			timing = timing1;
			
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RouteByCarowner.this);
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
				params.add(new BasicNameValuePair("start_point", start_point));
				params.add(new BasicNameValuePair("end_point", end_point));
				params.add(new BasicNameValuePair("route", route));
				params.add(new BasicNameValuePair("timing", timing));

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
				Toast.makeText(RouteByCarowner.this, file_url, Toast.LENGTH_LONG).show();
			}

		}
	}

}
	
	
