package com.example.ev_hub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView login = findViewById(R.id.individual_login_page);
        login.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_LoginPage.this,Activity_Individual_login.class);
            startActivity(intent);
        } );
        View indivisual_user = findViewById(R.id.individual_profile);
        indivisual_user.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_LoginPage.this,Activity_Individual_register.class);
            startActivity(intent);
        });

        View company_user = findViewById(R.id.company_profile);
        company_user.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_LoginPage.this,Activity_Company_register.class);
            startActivity(intent);
        });

        findViewById(R.id.company_login_page).setOnClickListener(v -> {
            Intent intent = new Intent(Activity_LoginPage.this, Activity_Company_login.class);
            startActivity(intent);
        });
    }
}