package com.example.app.florida_bus_reservation.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.password;

public class currentReservationDetails extends AppCompatActivity {

    /** Variables */
    private int reservation_id;

    ProgressDialog progressDialog;
    boolean isSucess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_reservation_details);

        this.init_all();

        Toast.makeText(getApplicationContext(), Integer.toString(reservation_id), Toast.LENGTH_SHORT).show();

    }

    private void init_all() {
        this.get_intent_params();
        this.btnTrigger();
    }

    private void get_intent_params() {
        Intent intent = getIntent();
        this.reservation_id = intent.getIntExtra("reservation_id", 0);
        final TextView txtReservationCode = (TextView) findViewById(R.id.txtReservationCode);
        txtReservationCode.setText(intent.getStringExtra("reservation_code"));
    }

    private void btnTrigger() {
        final Button btnCancel = (Button) findViewById(R.id.btnCancelReservation);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button Clicked!", Toast.LENGTH_SHORT).show();
//                save_data_to_server(0, reservation_id);
                new InsertAsync().execute(reservation_id);
            }
        });
    }

    private void save_data_to_server(int i, int reservation_id) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(MySQL_Queries.ConnectionString + "cancelReservation.php?reservation_id=" + reservation_id)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    String resp = response.body().string();
//                    Log.v(TAG_REGISTER, resp);
                    Toast.makeText(getApplicationContext(), "Response: " + resp, Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Response: " + resp, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(currentReservationDetails.this, currentReservation.class);
                        currentReservationDetails.this.startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    // Log.e(TAG_REGISTER, "Exception caught: ", e);
                    System.out.println("Exception caught" + e.getMessage());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /** ----------------------------------------------------------------------------------------------------- */
    class InsertAsync extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(MySQL_Queries.ConnectionString + "cancelReservation.php?reservation_id=" + params[0])
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String result = response.body().string();

                JSONObject jsonObject = new JSONObject(result);

                isSucess = jsonObject.getBoolean("success");

                if (isSucess) {
                    Intent intent = new Intent(currentReservationDetails.this, currentReservation.class);
                    currentReservationDetails.this.startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(currentReservationDetails.this);
            progressDialog.setMessage("Pleas wait...");
            progressDialog.setTitle("Warnig");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
        }
    }

    /** ------------------------------------------------------------------------------------------------------ */

}
