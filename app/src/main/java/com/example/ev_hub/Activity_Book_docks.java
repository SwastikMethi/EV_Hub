package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activity_Book_docks extends AppCompatActivity {
    Button btn_book_dock;
    ImageButton minimize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_docks);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_book_dock = findViewById(R.id.book_now);
        View dock1 = findViewById(R.id.dock_card_01);
        View dock2 = findViewById(R.id.dock_card_02);
        View dock3 = findViewById(R.id.dock_card_03);
        minimize = findViewById(R.id.minimize);

        btn_book_dock.setOnClickListener(v -> {
            dock1.setVisibility(View.VISIBLE);
            dock2.setVisibility(View.VISIBLE);
            dock3.setVisibility(View.VISIBLE);
            minimize.setVisibility(View.VISIBLE);
        });
        minimize.setOnClickListener(v -> {
            minimize.setVisibility(View.GONE);
            dock1.setVisibility(View.GONE);
            dock2.setVisibility(View.GONE);
            dock3.setVisibility(View.GONE);
        });

        findViewById(R.id.nav_button).setOnClickListener(view ->{
            Intent intent = new Intent(Activity_Book_docks.this, Activity_Map.class);
            startActivity(intent);
        });

        findViewById(R.id.home_button).setOnClickListener(view ->{
            Intent intent = new Intent(Activity_Book_docks.this, Activity_HomePage.class);
            startActivity(intent);
            finish();
        });
    }
}