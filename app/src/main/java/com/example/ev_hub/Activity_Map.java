package com.example.ev_hub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.health.connect.datatypes.ExerciseRoute;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.codebyashish.googledirectionapi.AbstractRouting;
import com.codebyashish.googledirectionapi.ErrorHandling;
import com.codebyashish.googledirectionapi.RouteDrawing;
import com.codebyashish.googledirectionapi.RouteInfoModel;
import com.codebyashish.googledirectionapi.RouteListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Activity_Map extends AppCompatActivity implements OnMapReadyCallback, RouteListener {
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double user_lat, user_long;
    private LatLng destination_location = null;
    private LatLng source_location;
    private List<Polyline> polylines = new ArrayList<>();
    Button togooglemaps;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.nav_button).setVisibility(View.INVISIBLE);
        findViewById(R.id.green_nav_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.home_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, Activity_HomePage.class);
            startActivity(intent);
        });
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (fragment != null) {
            fragment.getMapAsync(this);
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        checkLocationPermission();
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(false);

        fetchMyLocation();

        LatLng kanidi_biotech = new LatLng(28.44565308022041, 77.55669672468707);
        map.addMarker(new MarkerOptions().position(kanidi_biotech).title("Kanidi Biotech"));
        map.moveCamera(CameraUpdateFactory.newLatLng(kanidi_biotech));

        LatLng prateek_edifice = new LatLng(28.58, 77.33);
        map.addMarker(new MarkerOptions().position(prateek_edifice).title("Prateek Edifice"));
        map.moveCamera(CameraUpdateFactory.newLatLng(prateek_edifice));

        LatLng EVCD_Direct = new LatLng(28.626707, 77.37762);
        map.addMarker(new MarkerOptions().position(EVCD_Direct).title("EVCD Direct"));
        map.moveCamera(CameraUpdateFactory.newLatLng(EVCD_Direct));

        LatLng EVCD_s_Private = new LatLng(28.6266422,77.37762);
        map.addMarker(new MarkerOptions().position(EVCD_s_Private).title("EVCD  S Private"));
        map.moveCamera(CameraUpdateFactory.newLatLng(EVCD_s_Private));

        LatLng Ev_power_001 = new LatLng(28.4456,77.5566);
        map.addMarker(new MarkerOptions().position(Ev_power_001).title("Ev Power 001"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Ev_power_001));

        LatLng Chandigarh_EV_001 = new LatLng(28.62721,77.37766);
        map.addMarker(new MarkerOptions().position(Chandigarh_EV_001).title("Chandigarh-EV-001"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Chandigarh_EV_001));

        LatLng Sun_city_Demo = new LatLng(28.4369,77.1126);
        map.addMarker(new MarkerOptions().position(Sun_city_Demo).title("Sun City Demo"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Sun_city_Demo));

        LatLng Centra_Greens_EV_002 = new LatLng(30.8801,75.82789);
        map.addMarker(new MarkerOptions().position(Centra_Greens_EV_002).title("Centra Greens EV 002"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Centra_Greens_EV_002));

        LatLng ParasTierea_EV_004 = new LatLng(28.5048,77.41142);
        map.addMarker(new MarkerOptions().position(ParasTierea_EV_004).title("Paras Tierea EV 004"));
        map.moveCamera(CameraUpdateFactory.newLatLng(ParasTierea_EV_004));

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                destination_location = marker.getPosition();

                if (!marker.getTitle().contains("(")) {
                    float distance = calculateDistance(source_location, destination_location);

                    String distanceText;
                    if (distance < 1000) {
                        distanceText = String.format("%.0f", distance) + " meters";
                    } else {
                        distanceText = String.format("%.2f", distance / 1000) + " kms";
                    }
                    marker.setTitle(marker.getTitle() + " (" + distanceText + ")");
                }

                View card = findViewById(R.id.card_view);
                TextView selected_location = findViewById(R.id.selected_location);
                selected_location.setText(marker.getTitle());
                card.setVisibility(View.VISIBLE);

                float distance = calculateDistance(source_location, destination_location);
                String distance_string = String.format("%.2f", distance / 1000) + " kms";
                TextView distance_text = findViewById(R.id.kms);
                distance_text.setText("Distance: "+ distance_string);

                togooglemaps = findViewById(R.id.get_directions);
                togooglemaps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = "http://maps.google.com/maps?saddr=" + source_location.latitude + "," + source_location.longitude + "&daddr=" + destination_location.latitude + "," + destination_location.longitude;
                        Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(uri));
                        startActivity(intent);
                    }
                });
                getRoute(source_location, destination_location);
                return false;
            }
        });

    }
    private float calculateDistance(LatLng sourceLocation, LatLng destinationLocation) {
        float[] results = new float[1];
        Location.distanceBetween(sourceLocation.latitude, sourceLocation.longitude, destinationLocation.latitude, destinationLocation.longitude, results);
        return results[0];
    }


    private void getRoute(LatLng sourceLocation, LatLng destinationLocation) {
        RouteDrawing routeDrawing = new RouteDrawing.Builder()
                .context(Activity_Map.this)  // pass your activity or fragment's context
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this).alternativeRoutes(true)
                .waypoints(sourceLocation, destinationLocation)
                .build();
        routeDrawing.execute();
    }

    private void fetchMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                user_lat = location.getLatitude();
                user_long = location.getLongitude();

                source_location = new LatLng(user_lat, user_long);

                LatLng latLng = new LatLng(user_lat, user_long);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
    }

    @Override
    public void onRouteFailure(ErrorHandling errorHandling) {
        Toast.makeText(this, "Failed to Map Route", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRouteStart() {
        Toast.makeText(this, "Mapping Route", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRouteSuccess(ArrayList<RouteInfoModel> arrayList, int i) {
        for (Polyline polyline : polylines) {
            polyline.remove();
        }
        polylines.clear();
        RouteInfoModel routeInfoModel = arrayList.get(i);
        List<LatLng> latLngList = routeInfoModel.getPoints();
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE);
        polylineOptions.width(10);
        polylineOptions.addAll(latLngList);
        Polyline polyline = map.addPolyline(polylineOptions);
        polylines.add(polyline);
    }

    @Override
    public void onRouteCancelled() {
        Toast.makeText(this, "Route Cancelled", Toast.LENGTH_SHORT).show();
    }
    private void checkLocationPermission() {
        // Check if the location permission is granted
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // If permission is not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // If permission is already granted, fetch the user's location
            fetchMyLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Check if the permission request is for location and if it's granted
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, fetch the user's location
            fetchMyLocation();
        } else {
            // Permission denied, handle it gracefully
            // You may inform the user about the necessity of location permission for the app's functionality
            // Or disable location-related features
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
