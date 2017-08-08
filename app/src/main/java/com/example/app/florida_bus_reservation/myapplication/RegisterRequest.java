package com.example.app.florida_bus_reservation.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = MySQL_Queries.ConnectionString + "register.php";
    private Map<String, String> params;

    public RegisterRequest(String firstName, String middleName, String lastName, String username, String password, String address, String contact, Response.Listener<String> listener) {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("middleName", middleName);
        params.put("lastName", lastName);
        params.put("username", username);
        params.put("password", password);
        params.put("address", address);
        params.put("contact", contact);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
