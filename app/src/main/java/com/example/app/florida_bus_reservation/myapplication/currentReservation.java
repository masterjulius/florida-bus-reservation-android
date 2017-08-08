package com.example.app.florida_bus_reservation.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class currentReservation extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ReservationsAdapter adapter;
    private List<ReservationsData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_reservation);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_current_reservations);
        dataList = new ArrayList<>();
        load_data_from_server(0, 9);

        /** ----------------------------------------------------- */
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new ReservationsAdapter(this, dataList);
        recyclerView.setAdapter(adapter);

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
}
