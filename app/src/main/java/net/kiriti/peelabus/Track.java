package net.kiriti.peelabus;

import android.app.Dialog;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class Track extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapReadyCallback,
        ResultCallback<Status>, GoogleMap.OnMarkerClickListener {

    //    GoogleMap mGoogleMap;
//    GoogleApiClient mGoogleApiClient;
    private String urlJsonObj = "http://test.peelabus.com/WebService.asmx/LiveTracking";
    private Timer timer = new Timer();
    private static String TAG = MainActivity.class.getSimpleName();
    private Double lat;
    private Double lon;
    private Double speed;


    // temporary string to show the parsed response
    private String jsonResponse;

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;

    private TextView textLat, textLong;

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServicesAvailable()) {
            Toast.makeText(this, "Perfect!!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_track);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            textLat = (TextView) findViewById(R.id.lat);
//            textLong = (TextView) findViewById(R.id.lon);
            initMap();

            // create GoogleApiClient
            createGoogleApi();

        } else {
            // No Google Maps Layout
        }

    }

    // Create GoogleApiClient instance
    private void createGoogleApi() {
        Log.d(TAG, "createGoogleApi()");
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Call GoogleApiClient connection when starting the Activity
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Disconnect GoogleApiClient when stopping Activity
        googleApiClient.disconnect();
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Cant connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);


        if (map != null) {

            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v = getLayoutInflater().inflate(R.layout.info_window, null);

                    TextView tvLocality = (TextView) v.findViewById(R.id.tv_locality);
                    TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                    TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
                    TextView tvSnippet = (TextView) v.findViewById(R.id.tv_snippet);

                    LatLng ll = marker.getPosition();
                    tvLocality.setText(marker.getTitle());
                    tvLat.setText("Latitude: " + ll.latitude);
                    tvLng.setText("Longitude: " + ll.longitude);
                    tvSnippet.setText(marker.getSnippet());

                    return v;
                }
            });
        }
        makeJsonObjectRequest();
//        goToLocationZoom(39.008224, -76.8984527, 15);
        Log.i("hellooo", "heeeee");

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClickListener: " + marker.getPosition());
        return false;
    }


    // Get last known location

    private void getLastKnownLocation() {
//        timer.scheduleAtFixedRate(new TimerTask() {
//
//            @Override
//            public void run() {
        Log.d(TAG, "getLastKnownLocation()");
//            lastLocation = lat + lon;
        if (lat != null && lon != null) {
            Log.i(TAG, "LasKnown location. " +
                    "Long: " + lon +
                    " | Lat: " + lat);
            writeLastLocation();
//            startLocationUpdates();
        } else {
            Log.w(TAG, "No location retrieved yet");
//            startLocationUpdates();
        }
//            }
//        }, 0, 20 * 1000);

    }

    // Start location Updates
//    private void startLocationUpdates(){
//        Log.i(TAG, "startLocationUpdates()");
//
//        locationRequest =
//    }

    private void writeActualLocation(Location location) {
        Log.i("actual", "Actuallll====");
        textLat.setText("Lat: " + lat);
        textLong.setText("Long: " + lon);

        markerLocation(new LatLng(lat, lon));
    }

    private void writeLastLocation() {
        writeActualLocation(lastLocation);
    }


    private Marker locationMarker;

    private void markerLocation(LatLng latLng) {
        Log.i(TAG, "markerLocation(" + latLng + ")");
        String title = latLng.latitude + ", " + latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        if (map != null) {
            if (locationMarker != null)
                locationMarker.remove();
            locationMarker = map.addMarker(markerOptions);
            float zoom = 14f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            map.animateCamera(cameraUpdate);
        }
    }



    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        map.moveCamera(update);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        map.moveCamera(update);
    }

    Marker marker;

    public void geoLocate() throws IOException {

//        EditText et = (EditText) findViewById(R.id.editText);
//        String location = et.getText().toString();
//
//        Geocoder gc = new Geocoder(this);
//        List<Address> list = gc.getFromLocationName(location, 1);
//        Address address = list.get(0);
//        String locality = address.getLocality();
        makeJsonObjectRequest();

//        String locality = "It me";
//        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double latt = lat;
        double lng = lon;
        goToLocationZoom(latt, lng, 15);
        Log.i("geo", "geo = " + latt + " " + lng);

        setMarker("hey", latt, lng);

    }

//    Circle circle;

//    Marker marker1;
//    Marker marker2;
//    Polyline line;

    ArrayList<Marker> markers = new ArrayList<Marker>();
    static final int POLYGON_POINTS = 1;
    Polygon shape;

    private void makeJsonObjectRequest() {

        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        urlJsonObj, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONArray jsonarr = response.getJSONArray("Result");
                            Log.i("jsonarr", "jsonarr=" + jsonarr);
                            JSONObject obj = jsonarr.getJSONObject(0);
                            Log.i("jsonarr", "jsonarr=" + obj);
                            // Parsing json object response
                            // response will be a json object
                            Double latt = obj.getDouble("LAT");
                            lat = latt;
                            Double lonn = obj.getDouble("LONG");
                            lon = lonn;
                            Double speedd = obj.getDouble("speed");
                            speed = speedd;
                            String locality = "Speed: " + String.valueOf(speedd);
                            goToLocationZoom(latt, lonn, 16);
                            setMarker(locality, latt, lonn);

                            jsonResponse = "";
                            jsonResponse += "Name: " + lat + "\n\n";
                            jsonResponse += "Email: " + lon + "\n\n";
                            jsonResponse += "Home: " + speed + "\n\n";


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                        // hide the progress dialog
                    }
                });
                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(jsonObjReq);
            }
        }, 0, 10 * 1000);
    }

    private void setMarker(String locality, double lat, double lng) {

        if(markers.size() == POLYGON_POINTS){
            removeEverything();
        }

        if(speed == 0) {

            MarkerOptions options = new MarkerOptions()
                    .title(locality)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.info_icon))
                    .position(new LatLng(lat, lng))
                    .snippet("I am Here");

            markers.add(map.addMarker(options));

        } else {

            MarkerOptions options = new MarkerOptions()
                    .title(locality)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_bus_icon))
                    .position(new LatLng(lat, lng))
                    .snippet("I am Here");

            markers.add(map.addMarker(options));

        }
        if(markers.size() == POLYGON_POINTS){
            drawPolygon();
        }

    }

    private void drawPolygon() {
        PolygonOptions options = new PolygonOptions()
                .fillColor(0x330000FF)
                .strokeWidth(3)
                .strokeColor(Color.RED);

        for(int i=0; i<POLYGON_POINTS;i++){
            options.add(markers.get(i).getPosition());
        }
        shape = map.addPolygon(options);

    }

    private void removeEverything() {
        for(Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
        shape.remove();
        shape = null;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    LocationRequest mLocationRequest;

    @Override
    public void onConnected(Bundle bundle) {

        Log.i(TAG, "onConnected()");
        getLastKnownLocation();
//        mLocationRequest = LocationRequest.create();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(10000);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for Activity#requestPermissions for more details.
//                return;
//            }
//        }
//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "onConnectionSuspended()");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.w(TAG, "onConnectionFailed()");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged ["+location+"]");
//        lastLocation = location;
        writeActualLocation(location);
        if(location == null){
            Toast.makeText(this, "Cant get current location", Toast.LENGTH_LONG).show();
        } else {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 16);
            map.animateCamera(update);
        }
    }

    @Override
    public void onResult(@NonNull Status status) {

    }

}
