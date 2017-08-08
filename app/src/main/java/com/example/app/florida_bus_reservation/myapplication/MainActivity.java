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

public class MainActivity extends AppCompatActivity {

    // Initialize Variables
//    private TextView registerLink = (TextView) findViewById(R.id.lnkRegister);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Ititialize all */
        this.init_all();
    }

    /**
     * Methods
     */

//  Initialize all
    private void init_all() {
        this.login_action();
        this.register_link_action();
    }
//    End of intialize all

    private void login_action() {

        /** Get names */
        final EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                /** Get Values */
                String g_username = txtUsername.getText().toString();
                String g_password = txtPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                  @Override
                  public void onResponse(String response) {

                      try {
                          JSONObject jsonResponse = new JSONObject(response);
                          boolean success = jsonResponse.getBoolean("success");

                          if (success) {

                            /** Get response results */
                              String resUserID = jsonResponse.getString("user_id");
                              String resUserName = jsonResponse.getString("username");

                              Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                              intent.putExtra("user_id", resUserID);
                              intent.putExtra("username", resUserName);
                              MainActivity.this.startActivity(intent);
                          } else {
                              AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                              builder.setMessage("Login Failed!")
                                      .setNegativeButton("Retry", null)
                                      .create()
                                      .show();
                          }

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }


                  }
                };
                LoginRequest loginRequest = new LoginRequest(g_username, g_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });

    }

    private void register_link_action() {
        final TextView registerLink = (TextView) findViewById(R.id.lnkRegister);
        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, registerActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });
    }

}
