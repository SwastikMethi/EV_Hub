package com.example.ev_hub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Activity_Individual_login extends AppCompatActivity {
    EditText email, password;
    Button btn_login;
    CheckBox remember_me;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_individual_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView register = findViewById(R.id.register);
        register.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_Individual_login.this,Activity_Individual_register.class);
            startActivity(intent);
        } );

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        btn_login = findViewById(R.id.login_button);

        btn_login.setOnClickListener(view ->{
            String email_text = email.getText().toString();
            String password_text = password.getText().toString();
            if(TextUtils.isEmpty(email_text)){
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            }
            if(TextUtils.isEmpty(password_text)){
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            }

            mAuth.signInWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(Activity_Individual_login.this,Activity_HomePage.class);
                                finish();
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Activity_Individual_login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }
}