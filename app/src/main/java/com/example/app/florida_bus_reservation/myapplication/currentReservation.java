package com.example.app.florida_bus_reservation.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class currentReservation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ReservationsAdapter adapter;
    private List<ReservationsData> dataList;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private int user_id;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_reservation);

        /** Sidebar actions */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_current_reservations);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /** Initialize all */
        this.init_all();

        /** --------------------------------------- */

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_current_reservations);
        dataList = new ArrayList<>();
        load_data_from_server(0, this.user_id);

        /** ----------------------------------------------------- */
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ReservationsAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

    }

    /** --------------------------------------------------------------------------------- */
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

    private void menuLinksAction() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.florida_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void load_data_from_server(int i, final int client_id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... params) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(MySQL_Queries.ConnectionString + "reservationList.php?client_id=" + client_id)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        ReservationsData data = new ReservationsData(object.getInt("SCHED_ID"), object.getInt("RES_ID"), object.getInt("RES_SCHED_ID"), object.getInt("RES_TYPE"), object.getInt("RES_BUS_CLASS_ID"), object.getInt("RES_BUS_ID"), object.getDouble("CLASS_SEAT_PRICE"), object.getDouble("TRANS_TOTAL_PAYMENT"), object.getString("SCHED_DATE"), object.getString("SCHED_DEPARTURE_TIME"), object.getString("RES_CODE"), object.getString("RES_SEAT_NUMBERS"), object.getString("CLASS_NAME"), object.getString("BUS_NUMBER"));

                        dataList.add(data);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Done loading Reservations", Toast.LENGTH_SHORT).show();
            }

        };

        task.execute(i);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case(R.id.menu_make_reservation):
                Intent intent = new Intent(getApplicationContext(),reservation_list_schedules.class);
                intent.putExtra("user_id", currentReservation.this.user_id);
//                intent.putExtra("username", currentReservation.this.userName);
                startActivity(intent);
                break;
            case(R.id.menu_current_reservation):
                Intent intentCurrentReservation = new Intent(getApplicationContext(), currentReservation.class);
                intentCurrentReservation.putExtra("user_id", currentReservation.this.user_id);
//                intentCurrentReservation.putExtra("username", currentReservation.this.userName);
                startActivity(intentCurrentReservation);
                break;
        }
        return true;
    }
}
