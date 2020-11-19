package com.studioapp.mobileapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getContext(), "Map is Ready", Toast.LENGTH_LONG).show();
        mMap = googleMap;
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                //When clicked on map
//                // Initialize marker options
//                MarkerOptions markerOptions = new MarkerOptions();
//                //Set Position of marker
//                markerOptions.position(latLng);
//                //Set title of marker
//                markerOptions.title(latLng.latitude + " : " + latLng.longitude);
//                //Remove all marker
//                mMap.clear();
//                //Animating to zoom the marker
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
//                //Add marker on map
//                mMap.addMarker(markerOptions);
//            }
//        });
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            
            init();

        }

    }

    private static View rootView;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=99;
    private static final float DEFAULT_ZOOM = 15f;


    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGPS;

    //vars
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @SuppressLint("HandlerLeak")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_googlemaps,container,false);

        mSearchText = (AutoCompleteTextView) rootView.findViewById(R.id.input_search);
        mGPS = (ImageView) rootView.findViewById(R.id.ic_GPS);

        getLocationPermission();

        return rootView;
    }

    private void init(){
        Log.d("MAP" , "init: initializing ");

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER){
                    //execute our method for searching
                    geoLocate();
                }
                return false;
            }
        });
        mGPS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("MAP" , "onClick: clicked gps icon");
                getLocationPermission();
            }
        });

        hideSoftKeyboard();
    }

    private void geoLocate() {
        Log.d("MAP" , "geoLocate: geolocateing ");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString,1);
        }catch(IOException e){
            Log.e("MAP","geoLocate: IOException: " + e.getMessage());
        }

        if(list.size() > 0){
            Address address = list.get(0);
            Log.d("MAP" , "geoLocate: found a location: "+ address.toString());

//            Toast.makeText(getContext(),address.toString(),Toast.LENGTH_LONG).show();
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));
        }
    }

    private void getDeviceLocation(){
        Log.d("MAP","getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        try{
            if(mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener(){
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Log.d("MAP", "onComplete: found location");
                            Location currentLocation = (Location)task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),DEFAULT_ZOOM,"My location");
                        }
                        else{
                            Log.d("MAP", "onComplete: current location is null");
                            Toast.makeText(getContext(),"unable to get current location",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        }catch(SecurityException e)
        {
            Log.e("MAP","getDeviceLocation:  SecurityException: " + e.getMessage() );
        }
    }


    private void moveCamera(LatLng latLng, float zoom,String title){
        Log.d("MAP","moveCamera: moving the camera to:  lat: "+ latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));

        if(!title.equals("My location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }
        hideSoftKeyboard();

    }


    private void initMap(){
        Log.d("MAP", "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_maps);

        mapFragment.getMapAsync(GoogleMapsFragment.this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mLocationPermissionGranted = false;
        Log.d("MAP", "onRequestPermissionsResult: getting location");

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d("MAP", "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d("MAP", "onRequestPermissionsResult: getting granted");
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                    Log.e("MAP","INIT MAP");
                }
            }
        }
    }



    private void getLocationPermission(){
        Log.d("MAP", "getLocationPermission: getting location");

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ActivityCompat.checkSelfPermission(getContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d("MAP", "getLocationPermission: if 1");
            if (ActivityCompat.checkSelfPermission(getContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d("MAP", "getLocationPermission: if 2");
                //set a boolean
                mLocationPermissionGranted = true;
                initMap();
            }
            else{
                Log.d("MAP", "getLocationPermission: else 1");
                requestPermissions(permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            Log.d("MAP", "getLocationPermission: else 2");
            requestPermissions(permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
        Log.d("MAP", "getLocationPermission: end");
    }

    private void hideSoftKeyboard(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}


