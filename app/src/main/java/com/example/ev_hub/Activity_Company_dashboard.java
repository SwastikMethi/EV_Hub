package com.example.ev_hub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_Company_dashboard extends AppCompatActivity {
    Button logout, requests, accept_request, reject_request;
    TextView company_name, request_station_title, request_time;
    FirebaseDatabase database;
    DatabaseReference reservationsRef;
    View requestCard;
    String currentRequestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_company_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        database = FirebaseDatabase.getInstance("https://ev-hub-acebb-default-rtdb.firebaseio.com/");
        reservationsRef = database.getReference("Reservations");
        company_name = findViewById(R.id.company_name_title);
        logout = findViewById(R.id.company_logout);
        requests = findViewById(R.id.get_requests);
        requestCard = findViewById(R.id.requests_card);
        request_station_title = findViewById(R.id.requests_title);
        request_time = findViewById(R.id.requests_time);
        accept_request = findViewById(R.id.accept);
        reject_request = findViewById(R.id.reject);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Activity_Company_dashboard.this, Activity_LoginPage.class);
                finish();
                startActivity(intent);
            }
        });
        company_name.setText("Welcome, " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        requests.setOnClickListener(v -> {
            requestCard.setVisibility(View.VISIBLE);
            // Fetch and display requests
            fetchRequests();
        });
        accept_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveRequest();
            }
        });
        reject_request.setOnClickListener(v -> rejectRequest());
    }
    private void fetchRequests() {
        reservationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Iterate through the reservations and display them
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String stationName = snapshot.child("stationName").getValue(String.class);
                    String startTime = snapshot.child("startTime").getValue(String.class);
                    String endTime = snapshot.child("endTime").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);

                    // Display the first request found and break the loop
                    if (status.equals("pending")) {
                        displayRequest(stationName, startTime, endTime, snapshot.getKey());
                        break;
                    }
                    break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                Toast.makeText(Activity_Company_dashboard.this, "Failed to fetch requests: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayRequest(String stationName, String startTime, String endTime, String requestId) {
        // Display the request details in the card
        request_station_title.setText(stationName);
        request_time.setText("Start: " + startTime + ", End: " + endTime);

        // Store the request ID to track the current request
        currentRequestId = requestId;
    }
//
    private void approveRequest() {
        if (currentRequestId != null) {
            // Approve the request by updating its status in the database
            reservationsRef.child(currentRequestId).child("status").setValue("approved")
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Activity_Company_dashboard.this, "Request approved successfully.", Toast.LENGTH_SHORT).show();
                        // Hide the request card after approving
                        requestCard.setVisibility(View.GONE);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Activity_Company_dashboard.this, "Failed to approve request: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
//
    private void rejectRequest() {
        if (currentRequestId != null) {
            // Reject the request by updating its status in the database
            reservationsRef.child(currentRequestId).child("status").setValue("rejected")
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Activity_Company_dashboard.this, "Request rejected successfully.", Toast.LENGTH_SHORT).show();
                        // Hide the request card after rejecting
                        requestCard.setVisibility(View.GONE);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(Activity_Company_dashboard.this, "Failed to reject request: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}