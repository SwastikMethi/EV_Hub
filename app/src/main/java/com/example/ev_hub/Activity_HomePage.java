package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
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

public class Activity_HomePage extends AppCompatActivity {
    FirebaseAuth auth;
    Button btn_logout,book_button, get_location;
    View register_car, select_ev;
    FirebaseUser user;
    TextView user_name;

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
            Intent intent = new Intent(Activity_HomePage.this, Activity_Map.class);
            startActivity(intent);
        });

        book_button = findViewById(R.id.book_button);
        auth = FirebaseAuth.getInstance();
        btn_logout = findViewById(R.id.log_out);
        get_location = findViewById(R.id.get_location_button);
        user = auth.getCurrentUser();
        user_name = findViewById(R.id.name);
        
//        user_name.setText(user.getDisplayName());

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Activity_HomePage.this, Activity_LoginPage.class);
                finish();
                startActivity(intent);
            }
        });

        book_button.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_HomePage.this, Activity_Book_docks.class);
            startActivity(intent);
        });

        get_location.setOnClickListener(view ->{
            Intent intent = new Intent(Activity_HomePage.this, Activity_Map.class);
            startActivity(intent);
        });

        findViewById(R.id.home_button).setVisibility(View.INVISIBLE);

        findViewById(R.id.green_home_btn).setVisibility(View.VISIBLE);
    }
}