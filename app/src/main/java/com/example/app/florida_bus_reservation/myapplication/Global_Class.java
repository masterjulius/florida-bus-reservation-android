package com.example.app.florida_bus_reservation.myapplication;

import java.util.ArrayList;

public class Global_Class {
    public static String seatStr = "";
    public static ArrayList<Integer> seatList = new ArrayList<Integer>();
    public static void addSeat(int seatNumber) {
        seatList.add(seatNumber);
    }

    public static void setSeats(ArrayList<Integer> lists){
        seatList = lists;
    }

    public static void removeSeat(int index) {
        seatList.remove(index);
    }

    public static void removeByValue(int seatNumber) {
        seatList.remove(seatNumber);
    }
}
