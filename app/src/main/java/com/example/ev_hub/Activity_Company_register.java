package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Activity_Company_register extends AppCompatActivity {
    EditText name, contact_no, password;
    Button btn_register;
    FirebaseAuth mAuth;
    FirebaseDatabase companydb;
    DatabaseReference company_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_company_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        findViewById(R.id.company_login_click).setOnClickListener(v -> {
            Intent intent = new Intent(Activity_Company_register.this, Activity_Company_login.class);
            finish();
            startActivity(intent);
        });

        name = findViewById(R.id.company_name);
        contact_no = findViewById(R.id.company_number);
        password = findViewById(R.id.company_password);
        btn_register = findViewById(R.id.company_register_button);
        mAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = contact_no.getText().toString();
                String name_1 = name.getText().toString();
                String pass = password.getText().toString();
                if (contact_no.length()<10) {
                    Toast.makeText(Activity_Company_register.this, "Enter valid Contact number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name.getText().toString())) {
                    Toast.makeText(Activity_Company_register.this, "Enter name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<8){
                    findViewById(R.id.alert).setVisibility(TextView.VISIBLE);
                    return;
                }
                company_helper company = new company_helper(name.getText().toString(), contact_no.getText().toString(), password.getText().toString());
                companydb = FirebaseDatabase.getInstance("https://ev-hub-acebb-default-rtdb.firebaseio.com/");
                company_ref = companydb.getReference("Company_Data");
                company_ref.child(name_1).setValue(company);
                mAuth.createUserWithEmailAndPassword(number + "@company.com", pass)
                        .addOnCompleteListener(Activity_Company_register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name.getText().toString())
                                            .build();
                                    user.updateProfile(profileUpdates);
                                    Intent intent = new Intent(Activity_Company_register.this, Activity_Company_login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Activity_Company_register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}