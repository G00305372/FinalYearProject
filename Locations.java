package com.stephen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Locations extends FragmentActivity implements OnMapReadyCallback,OnClickListener, android.content.DialogInterface.OnClickListener
{
	private GoogleMap mMap;
	private EditText editTextShowLocation;
	private Button buttonGetLocation;
	private LocationManager locManager;
	private LocationListener locListener = new MyLocationListener();

	private boolean gps_enabled = false;
	private boolean network_enabled = false;

	Socket client;



	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		editTextShowLocation = (EditText) findViewById(R.id.editTextShowLocation);
		//progress = (ProgressBar) findViewById(R.id.progressBar1);
		//progress.setVisibility(View.GONE);
		buttonGetLocation = (Button) findViewById(R.id.buttonGetLocation);
		buttonGetLocation.setOnClickListener(this);
		locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		//Locations.con con = new Locations.con();
		//con.start();

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);

	}

	@Override
	public void onClick(View v) {
		//progress.setVisibility(View.VISIBLE);
		// exceptions will be thrown if provider is not permitted.
		try {
			gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {

		}
		try {
			network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {

		}

		// don't start listeners if no provider is enabled
		if (!gps_enabled && !network_enabled) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Attention!");
			builder.setMessage("Sorry, location is not determined. Please enable location providers");
			builder.setPositiveButton("OK", this);
			builder.setNeutralButton("Cancel", this);
			builder.create().show();
			//progress.setVisibility(View.GONE);

		}

		if (gps_enabled) {
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
		}
		if (network_enabled) {
			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
		}
	}

	class MyLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				// This needs to stop getting the location data and save the battery power.
				locManager.removeUpdates(locListener);

				String londitude = "Londitude: " + location.getLongitude();
				String latitude = "Latitude: " + location.getLatitude();
				String altitiude = "Altitiude: " + location.getAltitude();
				String accuracy = "Accuracy: " + location.getAccuracy();

				editTextShowLocation.setText(londitude + "\n" + latitude + "\n" + altitiude + "\n" + accuracy );
				//progress.setVisibility(View.GONE);


				MarkerOptions mp1 = new MarkerOptions();
				mp1.position(new LatLng(location.getLatitude(),location.getLongitude()));
				mMap.addMarker(mp1.title("BOB"));


			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(which == DialogInterface.BUTTON_NEUTRAL){
			editTextShowLocation.setText("Sorry, location is not determined. To fix this please enable location providers");
		}else if (which == DialogInterface.BUTTON_POSITIVE) {
			startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

	}

	private class con extends Thread{

		public void run(){
			try {
				PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
				client = new Socket("10.12.18.8", 12345);
				client.setKeepAlive(true);
				out.println("hi");
				out.flush();
				//out.close();
			}catch(IOException i){

			}
		}
	}


}

