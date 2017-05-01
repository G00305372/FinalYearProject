package com.example.g00305372.projectgps;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import static android.R.attr.radius;
import static com.example.g00305372.projectgps.R.id.map;

public class Locations extends FragmentActivity implements OnMapReadyCallback, OnClickListener, android.content.DialogInterface.OnClickListener {
    private GoogleMap mMap;
    private EditText editTextShowLocation;
    private Button buttonGetLocation,logout;
    private LocationManager locManager;
    private LocationListener locListener = new MyLocationListener();
    private Projection projection;

    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    String text;
    Socket client;
    String name,Job,jobname;
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    String joblat,joblng;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        editTextShowLocation = (EditText) findViewById(R.id.editTextShowLocation);
        //progress = (ProgressBar) findViewById(R.id.progressBar1);
        //progress.setVisibility(View.GONE);
        buttonGetLocation = (Button) findViewById(R.id.buttonGetLocation);
        logout = (Button) findViewById(R.id.button7);

        buttonGetLocation.setOnClickListener(this);
        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        name = b.getString("name");
        Job = b.getString("Job");

        Job.replace("-","");
        String str[] =Job.split("\\s+");
        jobname = str[1];
        joblat = str[2];
        joblng = str[3];
        Toast.makeText(this, jobname+" "+joblat+" "+joblng, Toast.LENGTH_LONG).show();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locListener);


        }
        if (network_enabled) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, locListener);
        }
    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {


                String londitude = "Longitude: " + location.getLongitude();
                String latitude = "Latitude: " + location.getLatitude();
                String altitiude = "Altitiude: " + location.getAltitude();
                String accuracy = "Accuracy: " + location.getAccuracy();

                Double lat = location.getLatitude();
                Double lng = location.getLongitude();
                editTextShowLocation.setText(name.toUpperCase() +"\n"+londitude + "\n" + latitude + "\n" + altitiude + "\n" + accuracy );

                MarkerOptions mp1 = new MarkerOptions();
                mp1.position(new LatLng(location.getLatitude(),location.getLongitude()));
                Marker marker =mMap.addMarker(mp1.title(name));

                double latt = Double.parseDouble(joblat);
                double lngt = Double.parseDouble(joblng);
                Marker marker1 =mMap.addMarker(new MarkerOptions().position(new LatLng(latt,lngt )).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(jobname));

                Location loc = new Location("");
                loc.setLatitude(lat);
                loc.setLongitude(lng);
                Location loc1 = new Location("");
                loc1.setLatitude(latt);
                loc1.setLongitude(lngt);
                //drawCircle(new LatLng(Latitude, Longitude));

//                float distanceInMeters = loc.distanceTo(loc1);
//                float distance = distanceInMeters/1000;
//                //float[] distance111 = new float[2];
//                float[] distance111 = new float[0];
//                //Math.round(distance);
//                CircleOptions circleOptions = new CircleOptions();
//
//                // Specifying the center of the circle
//                circleOptions.center(new LatLng(53.2773003, -9.0080364));
//
//                // Radius of the circle
//                circleOptions.radius(2);
//
//                // Border color of the circle
//                circleOptions.strokeColor(Color.BLACK);
//
//                // Fill color of the circle
//                circleOptions.fillColor(0x30ff0000);
//
//                // Border width of the circle
//                circleOptions.strokeWidth(2);
//
//                // Adding the circle to the GoogleMap
//                mMap.addCircle(circleOptions);
//                Location.distanceBetween( marker.getPosition().latitude, marker.getPosition().longitude,
//                        circleOptions.getCenter().latitude, circleOptions.getCenter().longitude, distance111);
//
//                if( distance111[0] > radius ){
//                    Toast.makeText(getBaseContext(), "Outside", Toast.LENGTH_LONG).show();
//
//                } else {
//                    Toast.makeText(getBaseContext(), "Inside", Toast.LENGTH_LONG).show();
//                }

               // editTextShowLocation.append("\n Distance to Job: "+distance+" km\n");

                CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                //CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
                mMap.moveCamera(center);
                //mMap.animateCamera(zoom);
                text = name+" "+lat +"  "+lng;
                Locations.con con = new Locations.con();
                con.start();

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
                //client = new Socket("10.12.13.150", 8080);
                client = new Socket("192.168.0.46", 8080);

                client.setKeepAlive(true);
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                out.println("equip "+text);
                out.flush();
            }catch(IOException i){

            }
        }
    }
}


