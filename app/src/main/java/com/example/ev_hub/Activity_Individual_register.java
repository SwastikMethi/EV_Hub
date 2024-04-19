package com.example.ev_hub;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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


public class Activity_Individual_register extends AppCompatActivity {

    EditText user_name, email, password;
    Button btn_register;
    FirebaseAuth mAuth;
    FirebaseDatabase userdb;
    DatabaseReference user_ref;

    private String extractUsername(String email) {
        // Use substring to get the part before @gmail.com
        int atIndex = email.indexOf("@");

        if (atIndex != -1) {
            return email.substring(0, atIndex);
        } else {
            // Handle the case where the email doesn't contain @
            return email;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_individual_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView login_click = findViewById(R.id.login_click);
        login_click.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_Individual_register.this,Activity_Individual_login.class);
            startActivity(intent);
            finish();
        });

        mAuth = FirebaseAuth.getInstance();
        user_name = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.register_button);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, name, password;
                name = user_name.getText().toString();
                email = String.valueOf(Activity_Individual_register.this.email.getText());
                password = String.valueOf(Activity_Individual_register.this.password.getText());

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(Activity_Individual_register.this, "Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Activity_Individual_register.this, "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Activity_Individual_register.this, "Password is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length()<8){
                    TextView alert = findViewById(R.id.alert);
                    alert.setVisibility(View.VISIBLE);
                    return;
                }
                String unique_username = extractUsername(email);
                register_helper reg = new register_helper(name, email, password);
                userdb = FirebaseDatabase.getInstance("https://ev-hub-acebb-default-rtdb.firebaseio.com/");
                user_ref = userdb.getReference("User_Data");
                user_ref.child(unique_username).setValue(reg);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .build();
                                    user.updateProfile(profileUpdates);
                                    Toast.makeText(Activity_Individual_register.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Activity_Individual_register.this, Activity_Individual_login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Activity_Individual_register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}