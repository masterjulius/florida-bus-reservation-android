package com.example.app.florida_bus_reservation.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class reservationComplete extends AppCompatActivity {
    private double TotalPrice;

    private int user_id;
    private String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_complete);
    }

}
