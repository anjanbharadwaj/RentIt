package com.example.anjanbharadwaj.bookrent;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    //private String address;
    String s;
    String user;
    String name = "The name is: ";
    ArrayList<String> st = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        System.out.println("Maps");
        Intent intent = getIntent();
         s = (String) (intent.getExtras().get("String"));
        user= (String) (intent.getExtras().get("User"));


        /*
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> st = new ArrayList<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while(i.hasNext()){
                    st.add(((DataSnapshot)i.next()).getKey());
                }

                name +=  st.get(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })
        */

        System.out.println(s +  " LOCATION TO SEARCH FOR");

        //System.out.println((String) (intent.getExtras().get("StringAdrs")));
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
        //for(int i = 0; i < list.size(); i++) {
            final Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocationName(s, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addresses.get(0);
        /*
        Address address = null;
        try {
            address = geocoder.getFromLocationName("99 Kellogg Way Santa Clara", 1).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        double longitude = address.getLongitude();
            double latitude = address.getLatitude();

        //DatabaseReference
            LatLng sydney = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(sydney).title(user));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(getApplicationContext(), Contact.class);
                    //intent.putExtra("User", )
                    startActivity(intent);
                    return false;
                }
            });
        }
    }

