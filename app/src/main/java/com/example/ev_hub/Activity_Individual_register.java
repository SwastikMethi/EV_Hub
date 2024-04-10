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


public class Activity_Individual_register extends AppCompatActivity {

    EditText name, email, password;
    Button btn_register;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(Activity_Individual_register.this, Activity_HomePage.class);
            startActivity(intent);
            finish();
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
        } );

        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.register_button);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, name, password;
                name = String.valueOf(Activity_Individual_register.this.name.getText());
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

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //FirebaseUser user = mAuth.getCurrentUser();
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