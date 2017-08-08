
package com.example.app.florida_bus_reservation.myapplication;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.InterpolatorRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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

public class reservation_list_schedules extends AppCompatActivity {

    /** Initialize variables */
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private int user_id;
    private String userName;

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list_schedules);

        /** Sidebar actions */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_list_reservation_schedules);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** ---- Initialize all calling ---- */
        this.init_all();

        /** ----------------------------------------------------- */
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dataList = new ArrayList<>();
        load_data_from_server(0);

        /** ----------------------------------------------------- */
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(this, dataList, this.user_id);
        recyclerView.setAdapter(adapter);


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

        NavigationView nv = (NavigationView)findViewById(R.id.florida_nav_view);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.menu_make_reservation):
                        Intent intent = new Intent(getApplicationContext(),reservation_list_schedules.class);
                        intent.putExtra("user_id", reservation_list_schedules.this.user_id);
                        intent.putExtra("username", reservation_list_schedules.this.userName);
                        startActivity(intent);
                        break;
                    case(R.id.menu_current_reservation):
                        Intent intentCurrentReservation = new Intent(getApplicationContext(), currentReservation.class);
                        intentCurrentReservation.putExtra("", reservation_list_schedules.this.user_id);
                        startActivity(intentCurrentReservation);
                        break;
                }
                return true;
            }
        });

    }


    private void load_data_from_server(int id) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(MySQL_Queries.ConnectionString + "reservationComponents.php?id=" + integers[0])
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        MyData data = new MyData(object.getInt("sched_id"), object.getInt("bus_id"), object.getInt("class_id"), object.getInt("class_seat_count"), object.getDouble("class_seat_price"), object.getInt("class_has_aircon"), object.getString("sched_date"), object.getString("sched_departure_time"), object.getString("bus_number"), object.getString("class_name"));

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
                Toast.makeText(getApplicationContext(), "Done loading schedules", Toast.LENGTH_SHORT).show();
            }

        };

        task.execute(id);

    }
}
