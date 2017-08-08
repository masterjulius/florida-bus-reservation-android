package com.example.app.florida_bus_reservation.myapplication;

/**
 * Created by acer-pc on 08/06/2017.
 */

public class SeatsData {
    public int seat_number;
    public SeatsData(int seat_number) {
        this.seat_number = seat_number;
    }

    public int get_seat_number() {
        return this.seat_number;
    }

    public void set_seat_number(int seat_number) {
        this.seat_number = seat_number;
    }

}
