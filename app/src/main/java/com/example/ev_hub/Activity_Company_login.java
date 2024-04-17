package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Activity_Company_login extends AppCompatActivity {
    EditText contact_no, password;
    Button btn_login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_company_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.company_register).setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Company_login.this, Activity_Company_register.class);
            startActivity(intent);
        });

        mAuth = FirebaseAuth.getInstance();
        contact_no = findViewById(R.id.contact_no);
        password = findViewById(R.id.company_password_login);
        btn_login = findViewById(R.id.company_login_button);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = contact_no.getText().toString();
                String pass = password.getText().toString();

                if (number.isEmpty()) {
                    Toast.makeText(Activity_Company_login.this, "Enter Contact no.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.isEmpty()) {
                    Toast.makeText(Activity_Company_login.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(number +"@company.com", pass).addOnCompleteListener(Activity_Company_login.this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Activity_Company_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activity_Company_login.this, Activity_Company_dashboard.class);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(Activity_Company_login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}