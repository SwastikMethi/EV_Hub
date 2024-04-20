package com.example.ev_hub;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Activity_Book_docks extends AppCompatActivity {
    Button btn_book_dock, reserve_dock_1, reserve_dock_2, reserve_dock_3;
    ImageButton minimize;
    FirebaseDatabase reservationdb;
    DatabaseReference reservationsRef;
    TextView select_slots, start, to, end, total_connectors;
    String start_time, end_time;

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

        reservationdb = FirebaseDatabase.getInstance("https://ev-hub-acebb-default-rtdb.firebaseio.com/");
        reservationsRef = reservationdb.getReference("Reservations");

        btn_book_dock = findViewById(R.id.book_now);
        View dock1 = findViewById(R.id.dock_card_01);
        View dock2 = findViewById(R.id.dock_card_02);
        View dock3 = findViewById(R.id.dock_card_03);
        minimize = findViewById(R.id.minimize);
        reserve_dock_1 = findViewById(R.id.reserve_btn_01);
        reserve_dock_2 = findViewById(R.id.reserve_btn_02);
        reserve_dock_3 = findViewById(R.id.reserve_btn_03);

        select_slots = findViewById(R.id.selected_time_slot);
        start = findViewById(R.id.start);
        to = findViewById(R.id.to);
        end = findViewById(R.id.end);
        total_connectors = findViewById(R.id.total_connectors);

        btn_book_dock.setOnClickListener(v -> {
            btn_book_dock.setVisibility(View.GONE);
            select_slots.setVisibility(View.VISIBLE);
            start.setVisibility(View.VISIBLE);
            to.setVisibility(View.VISIBLE);
            end.setVisibility(View.VISIBLE);
            dock1.setVisibility(View.VISIBLE);
            dock2.setVisibility(View.VISIBLE);
            dock3.setVisibility(View.VISIBLE);
            minimize.setVisibility(View.VISIBLE);
        });
        minimize.setOnClickListener(v -> {
            btn_book_dock.setVisibility(View.VISIBLE);
            select_slots.setVisibility(View.GONE);
            start.setVisibility(View.GONE);
            to.setVisibility(View.GONE);
            end.setVisibility(View.GONE);
            minimize.setVisibility(View.GONE);
            dock1.setVisibility(View.GONE);
            dock2.setVisibility(View.GONE);
            dock3.setVisibility(View.GONE);
        });

        reserve_dock_1.setOnClickListener(v -> {
            if (start_time == null || end_time == null) {
                Toast.makeText(Activity_Book_docks.this, "Please select start and end times.", Toast.LENGTH_SHORT).show();
                return;
            }
            reserveDock("Dock 1");
        });
        reserve_dock_2.setOnClickListener(v -> {
            if (start_time == null || end_time == null) {
                Toast.makeText(Activity_Book_docks.this, "Please select start and end times.", Toast.LENGTH_SHORT).show();
                return;
            }
            reserveDock("Dock 2");
        });
        reserve_dock_3.setOnClickListener(v -> {
            if (start_time == null || end_time == null) {
                Toast.makeText(Activity_Book_docks.this, "Please select start and end times.", Toast.LENGTH_SHORT).show();
                return;
            }
            reserveDock("Dock 3");
        });

        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openStartDialogue();
            }
        });

        end.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openEndDialogue();
            }
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

        listenForBookingStatusChanges();
    }

    private void reserveDock(String dockName) {
        // Get selected time slots
        String selectedStartTime = start.getText().toString();
        String selectedEndTime = end.getText().toString();

        // Check if start and end times are selected
        if (selectedStartTime.isEmpty() || selectedEndTime.isEmpty()) {
            Toast.makeText(Activity_Book_docks.this, "Please select start and end times.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Push booking details to Firebase Realtime Database
        if (isStartTimeValid(selectedStartTime, selectedEndTime)) {
            // Push booking details to Firebase Realtime Database
            String bookingId = reservationsRef.push().getKey(); // Generate a unique key for the booking
            Map<String, Object> bookingDetails = new HashMap<>();
            bookingDetails.put("stationName", dockName);
            bookingDetails.put("startTime", selectedStartTime);
            bookingDetails.put("endTime", selectedEndTime);
            bookingDetails.put("status", "pending");
            reservationsRef.child(bookingId).setValue(bookingDetails)
                    .addOnSuccessListener(aVoid -> {
                        // Booking request sent successfully
                        Toast.makeText(Activity_Book_docks.this, "Booking request sent for " + dockName + ".", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Failed to send booking request
                        Toast.makeText(Activity_Book_docks.this, "Failed to send booking request.", Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Display a toast for invalid time
            Toast.makeText(Activity_Book_docks.this, "Invalid time selection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openStartDialogue() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                Activity_Book_docks.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        start.setText(hourOfDay + ":" + minute);
                        start_time = hourOfDay + ":" + minute;
                    }
                }, 12, 0, false
        );
        timePickerDialog.show();
    }
    private void openEndDialogue() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                Activity_Book_docks.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        end.setText(hourOfDay + ":" + minute);
                        end_time = hourOfDay + ":" + minute;
                    }
                }, 12, 0, false
        );
        timePickerDialog.show();
    }
    private void listenForBookingStatusChanges() {
        reservationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Iterate through the reservations and update the reserve button accordingly
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String stationName = snapshot.child("stationName").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);

                    // Check if the status is approved for the current station
                    if(status !=null){
                        switch (status){
                            case "approved":
                                updateReserveButton(stationName, true);
                                break;
                            case "rejected":
                                updateReserveButton(stationName, false);
                                break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                Toast.makeText(Activity_Book_docks.this, "Failed to fetch booking statuses: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateReserveButton(String dockName, boolean approved) {
        switch (dockName) {
            case "Dock 1":
                reserve_dock_1.setText(approved ? "Approved" : "Rejected");
                reserve_dock_1.setBackgroundColor(getResources().getColor(approved ? R.color.blue : R.color.red));
                reserve_dock_1.setClickable(false);
                break;
            case "Dock 2":
                reserve_dock_2.setText(approved ? "Approved" : "Rejected");
                reserve_dock_2.setBackgroundColor(getResources().getColor(approved ? R.color.blue : R.color.red));
                reserve_dock_2.setClickable(false);
                break;
            case "Dock 3":
                reserve_dock_3.setText(approved ? "Approved" : "Rejected");
                reserve_dock_3.setBackgroundColor(getResources().getColor(approved ? R.color.blue : R.color.red));
                reserve_dock_3.setClickable(false);
                break;
            // Add cases for other docks if needed
        }
    }
    private boolean isStartTimeValid(String startTime, String endTime) {
        // Check if the start time is before the end time
        String[] startParts = startTime.split(":");
        String[] endParts = endTime.split(":");

        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);
        if (startHour < endHour || (startHour == endHour && startMinute < endMinute)) {
            return true;
        }
        return false;
    }
}