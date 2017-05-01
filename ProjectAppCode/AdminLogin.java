package com.example.g00305372.projectgps;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class AdminLogin extends FragmentActivity implements OnMapReadyCallback {
    Socket client;
    String text;
    private GoogleMap mMap;
    String name;
    Double lat,lng,JobLat,JobLng;
    EditText jobname;
    String NAME;
    Marker marker;
    Button clear,clrjob,confirm,logout;
    String job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        clear = (Button)findViewById(R.id.button2);
        clrjob = (Button)findViewById(R.id.button3);
        confirm = (Button)findViewById(R.id.button4);
        logout = (Button)findViewById(R.id.button5);
        jobname = (EditText)findViewById(R.id.editText3);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        name = b.getString("locations");
        name.replace("-","");

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
            }
        });

        clrjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marker.remove();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                job = jobname.getText().toString();
                if(job.equals("")){

                }else {
                    text = JobLat + " " + JobLng;
                    AdminLogin.con con = new AdminLogin.con();
                    con.start();
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminLogin.this,Login.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String str[] = name.split("\\s+");
        for(int ii =1;ii<str.length;ii=ii+3){
            NAME = str[ii];
            lat = Double.parseDouble(str[ii+1]);
            lng = Double.parseDouble(str[ii+2]);
            mMap.addMarker(new MarkerOptions().position( new LatLng(lat, lng)).title(NAME));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                JobLat = point.latitude;
                JobLng = point.longitude;

                marker =mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).draggable(true).title("Job Location"));
            }
        });

    }

    private class con extends Thread{

        public void run(){
            try {

                //client = new Socket("10.12.13.150", 8080);
                client = new Socket("192.168.0.46", 8080);
                client.setKeepAlive(true);

                //while(true){
                PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                out.println("Job "+text +" "+ job);
                out.flush();
                //}
                //out.clos{e();
            }catch(IOException i){

            }
        }
    }
}
