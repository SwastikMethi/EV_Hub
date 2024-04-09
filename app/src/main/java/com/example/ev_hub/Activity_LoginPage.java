package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        TextView skip = findViewById(R.id.skip_text);
        skip.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_LoginPage.this,Activity_HomePage.class);
            startActivity(intent);
        } );

        TextView login = findViewById(R.id.login);
        login.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_LoginPage.this,Activity_Individual_login.class);
            startActivity(intent);
        } );

        ImageButton user_profile = findViewById(R.id.user_profile);
        user_profile.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_LoginPage.this,Activity_Individual_register.class);
            startActivity(intent);
        } );

        ImageButton single_user = findViewById(R.id.single_account);
        single_user.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_LoginPage.this,Activity_Individual_register.class);
            startActivity(intent);
        } );
    }
}