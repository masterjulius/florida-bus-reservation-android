package com.example.app.florida_bus_reservation.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class reservationSaveRequest extends StringRequest {

    private static final String RESERVATION_SAVE_REQUEST_URL = MySQL_Queries.ConnectionString + "saveReservation.php";
    private Map<String, String> params;

    public reservationSaveRequest(int schedID, int busClass, int busID, String seatNumbers, int clientID, Response.Listener<String> listener) {

        super(Method.POST, RESERVATION_SAVE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("res_sched_id", Integer.toString(schedID));
        params.put("res_bus_class_id", Integer.toString(busClass));
        params.put("res_bus_id", Integer.toString(busID));
        params.put("res_seat_numbers", seatNumbers);
        params.put("res_client_id", Integer.toString(clientID));

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
