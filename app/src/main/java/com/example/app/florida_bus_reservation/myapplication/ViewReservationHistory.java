package com.example.app.florida_bus_reservation.myapplication;

import java.util.Calendar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewReservationHistory extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    /** Initialize variables */
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private int user_id;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservation_history);

        /** Sidebar actions */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_Make_reservation);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** Intialize calling */
        final Button btnSetDate = (Button) findViewById(R.id.btnSetDate);
        final Button btnSetTime = (Button) findViewById(R.id.btnSetTime);

        btnSetDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ViewReservationHistory.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setTitle("Select Date");
                dpd.show(getFragmentManager(), "DatePicker");
            }
        });

        btnSetTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        ViewReservationHistory.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true); // true = is 24 hours, false = is 12 hours
                        tpd.setTitle("Select Time");
                        tpd.show(getFragmentManager(), "TimePicker");
            }
        });

        this.init_all();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init_all() {
        this.getIntentParams();
    }

    private void getIntentParams() {
        Intent intent = getIntent();

        this.user_id = intent.getIntExtra("user_id", 0);
        this.userName = intent.getStringExtra("username");

        /**
         * accessing navigation items from different layout
         * because you cannot access external layouts or other resourses through this class
         * you need to find them by hierarchies or groups
         */
        NavigationView navigationView = (NavigationView) findViewById(R.id.florida_nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView lblUserName = (TextView) headerView.findViewById(R.id.lblNavUsername);
        lblUserName.setText("Howdy, " + this.userName);

    }

    /**
     * DateTimePicker and TimePicker actions
     */
    private void dateTimePickerActions() {


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String strDate = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Toast.makeText(this, strDate, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String strTime = "You picked the following time: "+hourOfDay+"h"+minute+"m"+second;
        Toast.makeText(this, strTime, Toast.LENGTH_SHORT).show();
    }
}
