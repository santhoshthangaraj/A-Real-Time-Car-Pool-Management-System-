package com.example.karspoolingapp;

import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		android.location.LocationListener {

	GoogleMap map;
	GPSModule gps;

	private static final LatLng HUNT_LIBRARY = new LatLng(35.769635, -78.676415);
	private TextView mAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Parse.initialize(this, "Pqzmq9HXIMw3Qc6R9XTBLKs4jk7hVCNmfQbipZkU",
				"y7KhAzO0n7iriK0GCAda5Kxpq39WaA3bNWLfzXK9");

		ParseObject testObject = new ParseObject("TestObject");
		// testObject.put("foo", "bar");
		// testObject.saveInBackground();

		// mAddress = (TextView) findViewById(R.id.address);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setMyLocationEnabled(true);
		if (map == null) {
			Toast.makeText(this, "Google Maps not available", Toast.LENGTH_LONG)
					.show();
		}

		gps = new GPSModule(MainActivity.this);

		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			Location myLocation = map.getMyLocation();
			// LatLng myLatLng = new LatLng(latitude, longitude);
			String lat = Double.toString(latitude) + ","
					+ Double.toString(longitude);
			testObject.put("Rohan", lat);
			LatLng myL = new LatLng(Double.parseDouble(lat.substring(0,
					lat.indexOf(','))), Double.parseDouble(lat.substring(lat
					.indexOf(',') + 1)));

			testObject.put("Starbucks", "35.325934,-80.744082");
			testObject.put("Ashford", "35.316718,-80.743080");

			testObject.saveInBackground();

			CameraPosition myPosition = new CameraPosition.Builder()
					.target(myL).zoom(17).bearing(90).tilt(30).build();
			map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));

			try {

				Geocoder geo = new Geocoder(
						MainActivity.this.getApplicationContext(),
						Locale.getDefault());
				List<Address> addresses = geo.getFromLocation(latitude,
						longitude, 1);
				if (addresses.isEmpty()) {
					Toast.makeText(MainActivity.this, "Waiting for Location",
							Toast.LENGTH_LONG).show();
				} else {
					if (addresses.size() > 0) {
						// yourtextfieldname.setText(addresses.get(0).getFeatureName()
						// + ", " + addresses.get(0).getLocality() +", " +
						// addresses.get(0).getAdminArea() + ", " +
						// addresses.get(0).getCountryName());
						Toast.makeText(
								getApplicationContext(),
								"Address:- "
										+ addresses.get(0).getFeatureName()
										+ addresses.get(0).getAdminArea()
										+ addresses.get(0).getLocality(),
								Toast.LENGTH_LONG).show();
					}
				}
			} catch (Exception e) {
				e.printStackTrace(); // getFromLocation() may sometimes fail
			}

		}

		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {
				Toast.makeText(MainActivity.this,
						"Provider enabled: " + provider, Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onProviderDisabled(String provider) {
				Toast.makeText(MainActivity.this,
						"Provider disabled: " + provider, Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					double latitude = gps.getLatitude();
					double longitude = gps.getLongitude();

					Location myLocation = map.getMyLocation();
					LatLng myLatLng = new LatLng(latitude, longitude);

					CameraPosition myPosition = new CameraPosition.Builder()
							.target(myLatLng).zoom(17).bearing(90).tilt(30)
							.build();
					map.animateCamera(CameraUpdateFactory
							.newCameraPosition(myPosition));
				}
			}
		};
		/*
		 * locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		 * 5000, 1, locationListener);
		 */

		// ///////////////// NEW CODE ADDED FOR SHOWING PEOPLE USING MULTIPLE
		// DEVICES ///////////////////

		String userId1 = testObject.getObjectId();
		// String userId2 = testObject.getString("Hinjawadi");

		ParseQuery<ParseObject> mapObj = ParseQuery.getQuery("TestObject");
		mapObj.getInBackground(userId1, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject obj, ParseException e) {
				if (e == null) {
					String latlng = obj.getString("Starbucks");
					String ltln = obj.getString("Ashford");

					LatLng myL = new LatLng(Double.parseDouble(latlng
							.substring(0, latlng.indexOf(','))),
							Double.parseDouble(latlng.substring(latlng
									.indexOf(',') + 1)));

					map.addMarker(new MarkerOptions().position(myL)
							.title("Starbucks").snippet("Charlotte"));

					CameraPosition myPosition1 = new CameraPosition.Builder()
							.target(myL).zoom(17).bearing(90).tilt(30).build();
					map.animateCamera(CameraUpdateFactory
							.newCameraPosition(myPosition1));

					LatLng my1L = new LatLng(Double.parseDouble(ltln.substring(
							0, ltln.indexOf(','))), Double.parseDouble(ltln
							.substring(ltln.indexOf(',') + 1)));

					map.addMarker(new MarkerOptions().position(my1L)
							.title("Ashford").snippet("Charlotte"));

					/*
					 * CameraPosition myPosition2 = new CameraPosition.Builder()
					 * .target(my1L).zoom(17).bearing(90).tilt(30).build();
					 * map.animateCamera(CameraUpdateFactory
					 * .newCameraPosition(myPosition2));
					 */

				} else {
					e.printStackTrace();
				}
			}
		});
	}

	long minTime = 5 * 1000; // Minimum time interval for update in seconds,
								// i.e. 5 seconds.
	long minDistance = 10;

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		case R.id.menu_sethybrid:
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;

		case R.id.menu_addmarker:

			// map.addMarker(new MarkerOptions().position(GOLDEN_GATE_BRIDGE))
			// .setTitle("Golden Gate");

			map.addMarker(new MarkerOptions().position(HUNT_LIBRARY)
					.title("Hunt Library").snippet("Raleigh"));
			// map.moveCamera(CameraUpdateFactory.newCameraPosition(new
			// CameraPosition(HUNT_LIBRARY, BIND_ABOVE_CLIENT,
			// TRIM_MEMORY_COMPLETE, TRIM_MEMORY_MODERATE)));
			map.moveCamera(CameraUpdateFactory.newLatLng(HUNT_LIBRARY));
			break;

		case R.id.menu_getcurrentlocation:
			// ---get your current location and display a blue dot---
			map.setMyLocationEnabled(true);

			break;

		case R.id.menu_showcurrentlocation:
			Location myLocation = map.getMyLocation();
			LatLng myLatLng = new LatLng(myLocation.getLatitude(),
					myLocation.getLongitude());

			CameraPosition myPosition = new CameraPosition.Builder()
					.target(myLatLng).zoom(17).bearing(90).tilt(30).build();
			map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));
			break;
		}

		return true;

	} 

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
