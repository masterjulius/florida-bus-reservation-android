package com.example.app.florida_bus_reservation.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /** Initialize variables */
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private int user_id;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /** Sidebar actions */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_Home);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** Intialize calling */
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
        this.menuLinksAction();
    }

    private void getIntentParams() {
        Intent intent = getIntent();
        this.user_id = Integer.parseInt(intent.getStringExtra("user_id"));
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

    private void menuLinksAction() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.florida_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case(R.id.menu_make_reservation):
                Intent intent = new Intent(getApplicationContext(),reservation_list_schedules.class);
                intent.putExtra("user_id", HomeActivity.this.user_id);
                intent.putExtra("username", HomeActivity.this.userName);
                startActivity(intent);
                break;
            case(R.id.menu_current_reservation):
                Intent intentCurrentReservation = new Intent(getApplicationContext(), currentReservation.class);
                intentCurrentReservation.putExtra("user_id", HomeActivity.this.user_id);
                intentCurrentReservation.putExtra("username", HomeActivity.this.userName);
                startActivity(intentCurrentReservation);
                break;
        }
        return true;
    }
}
