package com.example.app.florida_bus_reservation.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by julius on 7/28/2017.
 */

public class LoginRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = MySQL_Queries.ConnectionString + "signIn.php";
    private Map<String, String> params;


    public LoginRequest(String username, String password, Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
