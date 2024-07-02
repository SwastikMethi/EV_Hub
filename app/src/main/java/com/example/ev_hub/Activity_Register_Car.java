package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Register_Car extends AppCompatActivity {
    ImageButton home_button, map_button, route_button, user_button, back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        home_button = findViewById(R.id.home_button);
        map_button = findViewById(R.id.nav_button);
        route_button = findViewById(R.id.route_button);
        user_button= findViewById(R.id.user_button);
        back_button = findViewById(R.id.back_button_register_ev);

        home_button.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Register_Car.this, Activity_HomePage.class);
            startActivity(intent);
        });
        map_button.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Register_Car.this, Activity_Map.class);
            startActivity(intent);
        });
        route_button.setOnClickListener(v -> {
            Toast.makeText(this, "In-Progress", Toast.LENGTH_SHORT).show();
        });
        user_button.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Register_Car.this, Activity_ProfilePage.class);
            startActivity(intent);
        });
        back_button.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Register_Car.this, Activity_HomePage.class);
            startActivity(intent);
        });
    }
}