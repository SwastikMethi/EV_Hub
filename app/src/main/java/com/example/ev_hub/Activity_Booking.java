package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Activity_Booking extends AppCompatActivity {

    private EditText companyNameEditText;
    private EditText startTimeEditText;
    private EditText endTimeEditText;
    private Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_register);


        // Initialize EditText fields and button
        companyNameEditText = findViewById(R.id.company_name);
        startTimeEditText = findViewById(R.id.company_number);
        endTimeEditText = findViewById(R.id.company_password);
        bookButton = findViewById(R.id.company_register_button);

        // Set click listener for the book button
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event
                // You can navigate to the booking page here
            }
        });

        // Set up text change listeners to enable/disable the book button
        companyNameEditText.addTextChangedListener(textWatcher);
        startTimeEditText.addTextChangedListener(textWatcher);
        endTimeEditText.addTextChangedListener(textWatcher);
    }

    // TextWatcher to monitor changes in EditText fields
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            // Enable the book button if all required fields are filled
            boolean isCompanyNameEmpty = companyNameEditText.getText().toString().trim().isEmpty();
            boolean isStartTimeEmpty = startTimeEditText.getText().toString().trim().isEmpty();
            boolean isEndTimeEmpty = endTimeEditText.getText().toString().trim().isEmpty();

            bookButton.setEnabled(!isCompanyNameEmpty && !isStartTimeEmpty && !isEndTimeEmpty);
        }
    };
}
