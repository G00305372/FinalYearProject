package com.example.g00305372.projectgps;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class AdminLogin extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Double lat,lng;
    String NAME;
    EditText emp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        emp = (EditText)findViewById(R.id.emp);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync((OnMapReadyCallback) this);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String name = b.getString("locations");

        name.replace("-","");
        String str[] = name.split("\\s+");
        for(int ii =1;ii<str.length;ii=ii+3){
            NAME = str[ii];
            lat = Double.parseDouble(str[ii+1]);
            lng = Double.parseDouble(str[ii+2]);
            emp.append(NAME+str[ii+1]+str[ii+2]+"\n");

        }


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(name));
        mMap.addMarker(new MarkerOptions().position( new LatLng(lat, -lng)).title(NAME));
        //mMap.addMarker(new MarkerOptions().position( new LatLng(53.2707, -9.0568)).title(NAME));
    }




}
