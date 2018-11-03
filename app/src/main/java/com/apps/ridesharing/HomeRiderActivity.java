package com.apps.ridesharing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.apps.ridesharing.database.ConstantKey;
import com.apps.ridesharing.rider.RiderAsyncTask;
import com.apps.ridesharing.rider.RiderOnlineActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class HomeRiderActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, DirectionCallback {

    private static final String TAG = "HomeRiderActivity";
    private Activity context;

    //Navigation
    private ActionBarDrawerToggle toggle;

    private SharedPreferences preferences;
    private boolean isLoggedIn;
    private String riderMobile;
    private GoogleMap mMap;

    private GoogleApiClient client;
    private LocationRequest request;
    private Marker currentLocationMarker;
    private Location lastLocation;

    private String serverKey = "AIzaSyCCGReTLzs3myqFuZNN1tXK1GRawxjy3Ao";
    private LatLng origin = null;
    private LatLng destination = null;

    private Switch isOnlineSwitch;
    private TextView isOnline;
    private LinearLayout notificationBox;
    String lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_rider);

        context = this;

        //===============================================| Notification Data from MyFirebaseMessagingService
        if (getIntent().getExtras() != null) {
            notificationBox = (LinearLayout) findViewById(R.id.rider_notification_box);
            notificationBox.setVisibility(View.VISIBLE);

            String name = getIntent().getExtras().getString("user_full_name");
            TextView userName = (TextView) findViewById(R.id.user_name);
            userName.setText(name);

            String mobile = getIntent().getExtras().getString("user_mobile");
            TextView userMobile = (TextView) findViewById(R.id.user_mobile);
            userMobile.setText(mobile);

            lat = getIntent().getExtras().getString("user_latitude");
            lng = getIntent().getExtras().getString("user_longitude");
            Log.d(TAG, "Notification: "+name+" : "+mobile);
        }

        Button ok = (Button) findViewById(R.id.rider_notification_box_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationBox.setVisibility(View.GONE);
                destination = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                requestDirection();
            }
        });
        Button close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationBox.setVisibility(View.GONE);
            }
        });

        //===============================================| Getting SharedPreferences
        preferences = getSharedPreferences(ConstantKey.RIDER_LOGIN_KEY, MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean(ConstantKey.RIDER_IS_LOGGED_KEY, false);
        riderMobile = preferences.getString(ConstantKey.RIDER_MOBILE_KEY, "Data not found");

        //====================================| Custom Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            //toolbar.getBackground().setAlpha(200);
        }

        //====================================| Switch Button
        isOnline = (TextView) findViewById(R.id.is_online);
        isOnlineSwitch = (Switch) findViewById(R.id.is_online_switch);
        isOnlineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isOnline.setText("Online");
                    isOnline.setTextColor(Color.parseColor("#008577"));
                    Log.d(TAG, "Last LatLng" + String.valueOf(origin.latitude) + String.valueOf(origin.longitude));
                } else {
                    isOnline.setText("Offline");
                    isOnline.setTextColor(Color.parseColor("#444444"));

                    String type = "offline_rider";
                    new RiderAsyncTask(context, new RiderAsyncTask.AsyncResponse() {
                        @Override
                        public void processFinish(String output) {
                            Log.d(TAG, ""+output);
                            if (output.equals("Deleted successfully")) {
                                Intent intent = new Intent(context, RiderOnlineActivity.class);
                                startActivity(intent);
                            }
                        }
                    }).execute(type, riderMobile);
                }
            }
        });

        //====================================| Google Maps
        //If above the android version Marshmallow then call the location permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

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
        Log.d(TAG, "Change Location: "+location.getLatitude()+","+location.getLongitude());

        if (location == null) {
            Log.d(TAG, "Location could not be found");
        } else {
            origin = new LatLng(location.getLatitude(), location.getLongitude());
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

        String type = "update_rider_latlng";
        new RiderAsyncTask(context, new RiderAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.d(TAG, ""+output);
                if (output.equals("Updated successfully")) {

                }
            }
        }).execute(type, riderMobile, String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
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
                    Log.d(TAG, "permission denied");
                }
                return;
            }
        }
    }

    //===============================================| Direction
    public void requestDirection() {
        Log.d(TAG, "Direction: "+origin.longitude+destination.longitude);
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .unit(Unit.METRIC)
                .transportMode(TransportMode.DRIVING)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            Route route = direction.getRouteList().get(0);
            //mMap.addMarker(new MarkerOptions().position(origin).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_radio_button_checked_black_24dp)));
            mMap.addMarker(new MarkerOptions().position(origin).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.addMarker(new MarkerOptions().position(destination).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
            mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.parseColor("#008577")));
            setCameraWithCoordinationBounds(route);
        } else {
            Log.d(TAG, "Direction is not ok: "+direction.getStatus());
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Log.d(TAG, "Direction is not ok: "+t.getMessage());
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }
}
