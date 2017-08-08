package com.example.app.florida_bus_reservation.myapplication;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class registerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /** Call init */
        final EditText firstName = (EditText) findViewById(R.id.txtFirstName);
        final EditText middleName = (EditText) findViewById(R.id.txtMiddleName);
        final EditText lastName = (EditText) findViewById(R.id.txtLastName);
        final EditText username = (EditText) findViewById(R.id.txtUsername);
        final EditText password = (EditText) findViewById(R.id.txtPassword);
        final EditText address = (EditText) findViewById(R.id.txtAddress);
        final EditText birthPlace = (EditText) findViewById(R.id.txtBirthPlace);
        final EditText contact = (EditText) findViewById(R.id.txtContact);
        final Button btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                /** Get String Values */
                String g_firstName = firstName.getText().toString();
                String g_middleName = middleName.getText().toString();
                String g_lastName = lastName.getText().toString();
                String g_username = username.getText().toString();
                String g_password = password.getText().toString();
                String g_address = address.getText().toString();
                String g_birthPlace = birthPlace.getText().toString();
                String g_contact = contact.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                Toast.makeText(getApplicationContext(), "Successfully registered. Login your account now :)", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(registerActivity.this, MainActivity.class);
                                registerActivity.this.startActivity(intent);

                            } else {

                                boolean isDuplicated = jsonResponse.getBoolean("duplicated");
                                Toast.makeText(getApplicationContext(), Boolean.toString(isDuplicated), Toast.LENGTH_SHORT).show();

                                if ( isDuplicated ) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);
                                    builder.setMessage("Registration Failed! This Account already exists!")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                } else {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity.this);
                                    builder.setMessage("Registration Failed!")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(g_firstName, g_middleName, g_lastName, g_username, g_password, g_address, g_birthPlace, g_contact, responseListener);
                RequestQueue queue = Volley.newRequestQueue(registerActivity.this);
                queue.add(registerRequest);

            }
        });

    }

    /**
     * Methods
     */

    /** Intialize all */

}
