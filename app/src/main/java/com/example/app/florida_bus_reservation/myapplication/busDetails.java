package com.example.app.florida_bus_reservation.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class busDetails extends AppCompatActivity {

    private int sched_id, class_id, bus_id, seat_count;
    private double seat_price;
    private boolean has_aircon;
    private int user_id;
    private TextView txtBusClass, txtBusNumber, txtNumberOfSeats, txtSeatPrice, txtHasAircon, txtAvailableSeats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);
        this.init_all();
    }


    public void init_all() {
        this.get_intent_details();
        this.btn_trigger();
    }

    public void get_intent_details() {
        Intent intent = getIntent();
        this.user_id = intent.getIntExtra("client_id", 0);

        this.sched_id = intent.getIntExtra("sched_id", 0);
        this.class_id = intent.getIntExtra("class_id", 0);
        this.bus_id = intent.getIntExtra("bus_id", 0);

        this.seat_count = intent.getIntExtra("seat_count", 0);
        this.seat_price = intent.getDoubleExtra("seat_price", 0);

        this.txtBusClass = (TextView) findViewById(R.id.txtBusClass);
        this.txtBusNumber = (TextView) findViewById(R.id.txtBusNumber);
        this.txtNumberOfSeats = (TextView) findViewById(R.id.txtNumberOfSeats);
        this.txtSeatPrice = (TextView) findViewById(R.id.txtSeatPrice);
        this.txtHasAircon = (TextView) findViewById(R.id.txtHasAircon);
        this.txtAvailableSeats = (TextView) findViewById(R.id.txtAvailableSeats);

        this.txtBusClass.setText(intent.getStringExtra("class_name").toString());
        this.txtBusNumber.setText(intent.getStringExtra("bus_number").toString());
        this.txtNumberOfSeats.setText(Integer.toString(intent.getIntExtra("seat_count",0)));
        this.txtSeatPrice.setText(Double.toString(intent.getDoubleExtra("seat_price", 0)));
        String isAirConditioned = intent.getIntExtra("has_aircon", 0) == 1 ? "YES" : "NO";
        this.txtHasAircon.setText(isAirConditioned);

    }

    private void btn_trigger() {
        final Button btnProceed = (Button) findViewById(R.id.btnProceedToSeats);
        btnProceed.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(busDetails.this, SeatSelection.class);
                intent.putExtra("sched_id", sched_id);
                intent.putExtra("user_id", user_id);
                intent.putExtra("class_id", class_id);
                intent.putExtra("bus_id", bus_id);
                intent.putExtra("seat_count", seat_count);
                intent.putExtra("seat_price", seat_price);
                busDetails.this.startActivity(intent);
            }
        });
    }

}
