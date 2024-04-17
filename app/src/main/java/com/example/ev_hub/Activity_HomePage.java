package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_HomePage extends AppCompatActivity {
    FirebaseAuth auth;
    Button btn_logout;
    View nearest_charging_station, reserve_dock, select_ev, register_car;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageButton map = findViewById(R.id.nav_button);
        map.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_HomePage.this, Activity_Map.class);
            startActivity(intent);
        } );

        auth = FirebaseAuth.getInstance();
        btn_logout = findViewById(R.id.log_out);
        nearest_charging_station = findViewById(R.id.nearest_station_card);
        user = auth.getCurrentUser();

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Activity_HomePage.this, Activity_LoginPage.class);
                finish();
                startActivity(intent);
            }
        });

        nearest_charging_station.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_HomePage.this, Activity_Map.class);
            startActivity(intent);
        });

        // Find the button for booking and set a click listener
        reserve_dock = findViewById(R.id.reserve_dock_button);
        reserve_dock.setOnClickListener(view -> {
            // Navigate to the booking page
            Intent intent = new Intent(Activity_HomePage.this, Activity_Booking.class);
            startActivity(intent);
        });

        findViewById(R.id.home_button).setVisibility(View.INVISIBLE);
        findViewById(R.id.green_home_btn).setVisibility(View.VISIBLE);
    }
}


