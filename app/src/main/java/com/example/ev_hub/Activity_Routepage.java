package com.example.ev_hub;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Activity_Routepage extends AppCompatActivity {
    ImageButton map_button, profile_button, home_button, back_button;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_REQUEST_CODE = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_routepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        map_button = findViewById(R.id.nav_button);
        profile_button = findViewById(R.id.user_button);
        home_button = findViewById(R.id.home_button);
        back_button = findViewById(R.id.back_button_routing);

        map_button.setOnClickListener(view -> {
            Intent intent = new Intent(Activity_Routepage.this, Activity_Map.class);
            startActivity(intent);
        });
        profile_button.setOnClickListener(view -> {
            Intent intent = new Intent(Activity_Routepage.this, Activity_ProfilePage.class);
            startActivity(intent);
        });
        home_button.setOnClickListener(view -> {
            Intent intent = new Intent(Activity_Routepage.this, Activity_HomePage.class);
            startActivity(intent);
        });
        back_button.setOnClickListener(view -> {
            finish();
        });

        findViewById(R.id.green_route_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.route_button).setVisibility(View.INVISIBLE);
    }
}