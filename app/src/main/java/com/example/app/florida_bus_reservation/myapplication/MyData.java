package com.example.app.florida_bus_reservation.myapplication;

import java.sql.Time;
import java.util.Date;

class MyData {

    private int sched_id, bus_id, class_id, seat_count;
    private double seat_price;
    private int has_aircon;
    private String sched_date, sched_time, bus_number, class_name;

    public MyData(int sched_id, int bus_id, int class_id, int seat_count, double seat_price, int has_aircon, String sched_date, String sched_time, String bus_number, String class_name) {
        this.sched_id = sched_id;
        this.bus_id = bus_id;
        this.class_id = class_id;
        this.seat_count = seat_count;
        this.seat_price = seat_price;
        this.has_aircon = has_aircon;
        this.sched_date = sched_date;
        this.sched_time = sched_time;
        this.bus_number = bus_number;
        this.class_name = class_name;
    }

    public int get_sched_id(){
        return sched_id;
    }

    public void set_sched_id(int sched_id) {
        this.sched_id = sched_id;
    }

    public int get_bus_id(){
        return bus_id;
    }

    public void set_bus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public int get_class_id() {
        return class_id;
    }

    public void set_class_id(int class_id) {
        this.class_id = class_id;
    }

    public int get_seat_count() {
        return seat_count;
    }

    public void set_seat_count(int seat_count) {
        this.seat_count = seat_count;
    }

    public double get_seat_price() {
        return seat_price;
    }

    public void set_seat_price(double seat_price) {
        this.seat_price = seat_price;
    }

    public int get_has_aircon() {
        return has_aircon;
    }

    public void set_has_aircon(int has_aircon) {
        this.has_aircon = has_aircon;
    }

    public String get_class_name() {
        return class_name;
    }

    public void set_class_name(String class_name) {
        this.class_name = class_name;
    }

    public String get_sched_date(){
        return sched_date;
    }

    public void set_sched_date(String sched_date) {
        this.sched_date = sched_date;
    }

    public String get_sched_time(){
        return sched_time;
    }

    public void set_sched_time(String sched_time) {
        this.sched_time = sched_time;
    }

    public String get_bus_number(){
        return bus_number;
    }

    public void set_bus_number(String bus_number) {
        this.bus_number = bus_number;
    }

}
