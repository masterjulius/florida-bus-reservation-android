package com.example.app.florida_bus_reservation.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SeatSelection extends AppCompatActivity {

    /** Initialize variables */
    private int sched_id, class_id, bus_id;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private int user_id, seat_count;
    private double seat_price;
    private String userName;

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private SeatsAdapter adapter;
    private List<SeatsData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        /** Initializer */
        this.init_all();

        /** ----------------------------------------------------- */
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_seats);
        dataList = new ArrayList<>();
        load_data_from_server(0, this.sched_id);

        /** ----------------------------------------------------- */
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        TextView txtViewTotalPrice = (TextView) findViewById(R.id.txtReserveTotalPrice);
        txtViewTotalPrice.setText("Price per seat is: " + Double.toString(this.seat_price));

        adapter = new SeatsAdapter(this, dataList, this.seat_count, this.seat_price, txtViewTotalPrice);
        recyclerView.setAdapter(adapter);

    }

    private void load_data_from_server(int id, final int schedID) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(MySQL_Queries.ConnectionString + "seatsAvailable.php?sched_id=" + schedID)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        SeatsData data = new SeatsData(object.getInt("seat"));

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

    /**
     * Button Trigger
     */
    private void btnTrigger() {
        final Button btnReserveNow = (Button) findViewById(R.id.btnReserveNow);
        btnReserveNow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {

                    @Override
                    protected Void doInBackground(Integer... params) {

                        Double totalPrice = Calculations_Class.seatComputedPrice;
                        String seatNumbers = "";
                        int index = 0;
                        for (int s: Global_Class.seatList) {
                            String separator = index == Global_Class.seatList.size() - 1 ? "" : ":";
                            seatNumbers += Integer.toString(s) + separator;
                            index++;
                        }

                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("res_sched_id", Integer.toString(sched_id))
                                .add("res_bus_class_id", Integer.toString(class_id))
                                .add("res_bus_id", Integer.toString(bus_id))
                                .add("res_seat_numbers", seatNumbers)
                                .add("res_client_id", Integer.toString(user_id))
                                .add("total_price", Double.toString(Calculations_Class.seatComputedPrice))
                                .build();

                        Request request = new Request.Builder()
                                .url(MySQL_Queries.ConnectionString + "saveReservation.php")
                                .post(requestBody)
                                .build();

                        try {
                            Response response = client.newCall(request).execute();
                            JSONArray array = new JSONArray(response.body().string());

                            JSONObject object = array.getJSONObject(0);
                            String ticket_id = object.getString("reservation_code");
                            Intent intent = new Intent(SeatSelection.this, reservationComplete.class);
                            intent.putExtra("reservation_code", ticket_id);
                            SeatSelection.this.startActivity(intent);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        catch (JSONException e) {
                            System.out.println("End of content");
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Done processing data", Toast.LENGTH_SHORT).show();
                        Global_Class.seatList = new ArrayList<Integer>();
                    }
                };

                task.execute(0);

            }
        });
    }

    /** ----------------------------------------------------------------- */

    private void init_all() {
        this.getIntentParams();
        this.menuLinksAction();
        this.btnTrigger();
    }

    private void getIntentParams() {
        Intent intent = getIntent();

        this.user_id = intent.getIntExtra("user_id", 0);
        this.sched_id = intent.getIntExtra("sched_id", 0);
        this.class_id = intent.getIntExtra("class_id", 0);
        this.bus_id = intent.getIntExtra("bus_id", 0);

//        this.userName = intent.getStringExtra("username");
        this.seat_count = intent.getIntExtra("seat_count", 0);
        this.seat_price = intent.getDoubleExtra("seat_price", 0);

        /**
         * accessing navigation items from different layout
         * because you cannot access external layouts or other resourses through this class
         * you need to find them by hierarchies or groups
         */
        NavigationView navigationView = (NavigationView) findViewById(R.id.florida_nav_view);
        View headerView = navigationView.getHeaderView(0);
//        TextView lblUserName = (TextView) headerView.findViewById(R.id.lblNavUsername);
//        lblUserName.setText("Howdy, " + this.userName);

    }

    private void menuLinksAction() {

        NavigationView nv = (NavigationView)findViewById(R.id.florida_nav_view);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.menu_make_reservation):
                        Intent intent = new Intent(getApplicationContext(),reservation_list_schedules.class);
                        intent.putExtra("user_id", SeatSelection.this.user_id);
                        intent.putExtra("username", SeatSelection.this.userName);
                        startActivity(intent);
                }
                return true;
            }
        });

    }

}
