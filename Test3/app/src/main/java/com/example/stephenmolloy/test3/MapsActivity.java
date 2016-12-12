package com.example.stephenmolloy.test3;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static android.view.View.X;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


            mMap = googleMap;

            // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
            Employee No1 = new Employee("Employee: Joe Bloggs", 1, " GPS: 53.2707, -9.0568");
            Employee No2 = new Employee("Joe Bloggs", 2, " 56.2707, 14.0568");

            LatLng stephen = new LatLng(53.2707, -9.0568);
            LatLng joe = new LatLng(56.2707, 14.0568);
            //Employee = E.getLocation(53, 9);
            mMap.addMarker(new MarkerOptions().position(stephen).title(No1.getName() + No1.getGPS()));
            mMap.addMarker(new MarkerOptions().position(joe).title(No2.getName() + No2.getGPS()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(stephen));


        }
    }
