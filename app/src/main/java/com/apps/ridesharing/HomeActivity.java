package com.apps.ridesharing;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ridesharing.maps.DirectionFinder;
import com.apps.ridesharing.maps.DirectionFinderListener;
import com.apps.ridesharing.maps.Route;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, DirectionFinderListener {

    //Navigation
    private ActionBarDrawerToggle toggle;

    private static final String TAG = "HomeActivity";
    private GoogleMap mMap;

    private GoogleApiClient client;
    private LocationRequest request;
    private Marker currentLocationMarker;
    private Location lastLocation;

    private EditText originPlace, destinationPlace;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //====================================| Custom Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            //toolbar.getBackground().setAlpha(200);
        }

        //====================================================| To Display Navigation Bar Icon and Back
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_id);
        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_id);
            View hView =  navigationView.getHeaderView(0);
            CircleImageView navUserPhoto = (CircleImageView)hView.findViewById(R.id.nav_header_photo);
            navUserPhoto.setImageDrawable(getResources().getDrawable(R.drawable.admin));
            TextView navUserName = (TextView)hView.findViewById(R.id.nav_header_user);
            navUserName.setText("Mustofa Kamal");
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        //separator Item Decoration
        NavigationMenuView navMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        navMenuView.addItemDecoration(new DividerItemDecoration(HomeActivity.this,DividerItemDecoration.VERTICAL));

        //====================================| Google Maps

        originPlace = (EditText) findViewById(R.id.origin_place);
        destinationPlace = (EditText) findViewById(R.id.destination_place);

        //If above the android version Marshmallow then call the location permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //====================================| PlaceAutocompleteFragment
        //https://www.truiton.com/2016/03/android-nearby-places-api-autocomplete-widget/
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        //AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        //autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.d(TAG, "Place selected: " + place.getName());
                destinationPlace.setText(place.getLatLng().latitude+","+place.getLatLng().longitude);
                sendRequest();
                //goToLocation(place.getLatLng().latitude, place.getLatLng().longitude,15);
                //addMarker(place.getLatLng().latitude, place.getLatLng().longitude, place.getName().toString());
            }

            @Override
            public void onError(Status status) {
                Log.d(TAG, "An error occurred: " + status);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    //====================================================| onBackPressed in Background and OptionsMenu
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_maps_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.none:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //====================================================| Navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.settings_id) {
        }
        if (menuItem.getItemId() == R.id.profile_id) {
        }
        if (menuItem.getItemId() == R.id.notice_id) {
        }
        if (menuItem.getItemId() == R.id.about_id) {
            aboutMe();
        }
        if (menuItem.getItemId() == R.id.log_out) {
        }
        return false;
    }

    //====================================================| Alert Dialog
    public void alertDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).show();
    }

    //====================================================| About
    public void aboutMe() {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("About")
                .setMessage(HomeActivity.this.getString(R.string.apps_details))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Thank You", Toast.LENGTH_SHORT);
                    }
                }).show();
    }


    //===============================================| onStart(), onPause(), onResume(), onStop()
    @Override
    public void onStart() {
        super.onStart();
        if (client != null && client.isConnected()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (client != null && client.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (client != null && client.isConnected()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (client != null && client.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
            client.disconnect();
        }
    }

    //===============================================| onMapReady
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //For Marshmallow and Above
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (mMap != null) {
            /*mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {

                }

                @Override
                public void onMarkerDrag(Marker marker) {

                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        LatLng latLng = marker.getPosition();
                        List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                        Address address = addressList.get(0);
                        marker.setTitle(address.getLocality());
                        marker.showInfoWindow();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });*/
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View row = getLayoutInflater().inflate(R.layout.address_info_window, null);
                    TextView t1 = row.findViewById(R.id.locality);
                    TextView t2 = row.findViewById(R.id.lat);
                    TextView t3 = row.findViewById(R.id.lng);
                    TextView t4 = row.findViewById(R.id.snippet);

                    LatLng latLng = marker.getPosition();
                    t1.setText(marker.getTitle());
                    t2.setText(String.valueOf(latLng.latitude));
                    t3.setText(String.valueOf(latLng.longitude));
                    t4.setText(marker.getSnippet());

                    return row;
                }
            });
        }

        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    //===============================================| GoogleApiClient
    protected synchronized void buildGoogleApiClient() {
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        client.connect();
    }

    //===============================================| Display User Location
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest().create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(1000); //1000*10
        request.setFastestInterval(1000); //1000*5
        request.setSmallestDisplacement(0.1f);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        //Toast.makeText(this, location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();

        if (location == null) {
            Toast.makeText(this, "Location could not be found", Toast.LENGTH_SHORT).show();
        } else {
            originPlace.setText(location.getLatitude()+","+location.getLongitude());
            addMarker(location.getLatitude(),location.getLongitude(),"Current Location");
            goToLocation(location.getLatitude(),location.getLongitude(),16);

            //stop location updates
            /*if (client != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
            }*/
        }

        /*if(currentLocationMarker != null){
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        currentLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));*/
    }

    //===============================================| Add Marker and Move Map Camera
    public void addMarker(double latitude, double longitude, String locality){
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }

        MarkerOptions options = new MarkerOptions();
        options.position(new LatLng(latitude,longitude));
        options.draggable(true);
        options.title(locality);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        currentLocationMarker = mMap.addMarker(options);
    }
    public void goToLocation(double latitude, double longitude, int zoom) {
        LatLng latLng = new LatLng(latitude,longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    //====================================| Location Permission
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        //If permission is granted then proceed this operation
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    //====================================| Handle the Request Permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    //===============================================| DirectionFinderListener
    private void sendRequest() {
        String origin = originPlace.getText().toString();
        String destination = destinationPlace.getText().toString();
        if (origin.isEmpty() && destination.isEmpty()) {
            Toast.makeText(this, "Please enter origin address and destination address!", Toast.LENGTH_SHORT).show();
        } else {
            try {
                new DirectionFinder(this, origin, destination).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "", "", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.duration_place)).setText(route.duration.text);
            ((TextView) findViewById(R.id.distance_place)).setText(route.distance.text);

            /*originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_radio_button_checked_black_24dp))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_radio_button_checked_black_24dp))
                    .title(route.endAddress)
                    .position(route.endLocation)));*/

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.parseColor("#008577")).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
