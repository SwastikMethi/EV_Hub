package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_HomePage extends AppCompatActivity {
    FirebaseAuth auth;
    Button book_button, get_location;
    ImageButton wallet_button;
    FirebaseUser user;
    ProgressBar progressBar;
    View register_car, select_car;

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
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(Activity_HomePage.this, Activity_Map.class);
            startActivity(intent);
        });

        ImageButton account = findViewById(R.id.user_button);
        account.setOnClickListener(view ->{
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(Activity_HomePage.this, Activity_ProfilePage.class);
            startActivity(intent);
        });

        ImageButton route = findViewById(R.id.route_button);
        route.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_HomePage.this, Activity_Routepage.class);
            startActivity(intent);
        });

        book_button = findViewById(R.id.book_button);
        auth = FirebaseAuth.getInstance();
        get_location = findViewById(R.id.get_location_button);
        user = auth.getCurrentUser();
        progressBar = findViewById(R.id.progress_bar);
        wallet_button = findViewById(R.id.wallet_img);
        register_car= findViewById(R.id.register_car_card);
        select_car = findViewById(R.id.select_ev_card);


        wallet_button.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_HomePage.this, Activity_WalletPage.class);
            startActivity(intent);
        });
        book_button.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_HomePage.this, Activity_Book_docks.class);
            startActivity(intent);
        });
        register_car.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_HomePage.this, Activity_Register_Car.class);
            startActivity(intent);
        });
        get_location.setOnClickListener(view ->{
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(Activity_HomePage.this, Activity_Map.class);
            startActivity(intent);
        });
        select_car.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_HomePage.this, Activity_Select_EV.class);
            startActivity(intent);
        });


        findViewById(R.id.home_button).setVisibility(View.INVISIBLE);
        findViewById(R.id.green_home_btn).setVisibility(View.VISIBLE);
    }
}