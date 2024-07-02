package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_ProfilePage extends AppCompatActivity {
    Button btn_home, btn_map, btn_route;
    TextView user_name;
    View btn_logout, btn_wallet, btn_myaccount;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.green_user_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.user_button).setVisibility(View.INVISIBLE);
        findViewById(R.id.nav_button).setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ProfilePage.this, Activity_Map.class);
            startActivity(intent);
        });
        findViewById(R.id.home_button).setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ProfilePage.this, Activity_HomePage.class);
            startActivity(intent);
        });
        findViewById(R.id.route_button).setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ProfilePage.this, Activity_Routepage.class);
            startActivity(intent);
        });


        user_name = findViewById(R.id.user_name);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        user_name.setText(user.getDisplayName());

        btn_wallet = findViewById(R.id.wallet_card);
        btn_logout = findViewById(R.id.logout_card);
        btn_myaccount = findViewById(R.id.account_card);
        btn_logout.setOnClickListener(v -> {
            auth.signOut();
            Intent intent = new Intent(Activity_ProfilePage.this, Activity_LoginPage.class);
            finish();
            startActivity(intent);
        });
        btn_wallet.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ProfilePage.this, Activity_WalletPage.class);
            startActivity(intent);
        });
        btn_myaccount.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ProfilePage.this, Activity_My_Accountpage.class);
            startActivity(intent);
        });
    }
}