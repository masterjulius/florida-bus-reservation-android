package com.example.app.florida_bus_reservation.myapplication;

/**
 * Created by acer-pc on 08/08/2017.
 */

public class ReservationsData {

    private int sched_id, res_id, res_sched_id, res_type, res_bus_class_id, res_bus_id;
    private double class_seat_price, trans_total_payment;
    private String sched_date, sched_departure_time, res_code, res_seat_numbers, class_name, bus_number;

    public ReservationsData(int sched_id, int res_id, int res_sched_id, int res_type, int res_bus_class_id, int res_bus_id, double class_seat_price, double trans_total_payment, String sched_date, String sched_departure_time, String res_code, String res_seat_numbers, String class_name, String bus_number) {
        this.sched_id = sched_id;
        this.res_id = res_id;
        this.res_sched_id = res_sched_id;
        this.res_type = res_type;
        this.res_bus_class_id = res_bus_class_id;
        this.res_bus_id = res_bus_id;
        this.class_seat_price = class_seat_price;
        this.trans_total_payment = trans_total_payment;
        this.sched_date = sched_date;
        this.sched_departure_time = sched_departure_time;
        this.res_code = res_code;
        this.res_seat_numbers = res_seat_numbers;
        this.class_name = class_name;
        this.bus_number = bus_number;
    }

    public int get_sched_id() {
        return this.sched_id;
    }

    public void set_sched_id(int sched_id) {
        this.sched_id = sched_id;
    }

    public int get_res_id() {
        return this.res_id;
    }

    public void set_res_id(int res_id) {
        this.res_id = res_id;
    }

    public int get_res_sched_id() {
        return this.res_sched_id;
    }

    public void set_res_sched_id(int res_sched_id) {
        this.res_sched_id = res_sched_id;
    }

    public int get_res_type() {
        return this.res_type;
    }

    public void set_res_type(int res_type) {
        this.res_type = res_type;
    }

    public int get_res_bus_class_id() {
        return this.res_bus_class_id;
    }

    public void set_res_bus_class_id(int res_bus_class_id) {
        this.res_bus_class_id = res_bus_class_id;
    }

    public int get_res_bus_id() {
        return this.res_bus_id;
    }

    public void set_res_bus_id(int res_bus_id) {
        this.res_bus_id = res_bus_id;
    }

    /** -------------------------------------------------------- */
    public double get_class_seat_price() {
        return this.class_seat_price;
    }

    public void set_class_seat_price(double class_seat_price) {
        this.class_seat_price = class_seat_price;
    }

    public double get_trans_total_payment() {
        return this.trans_total_payment;
    }

    public void set_trans_total_payment(double trans_total_payment) {
        this.trans_total_payment = trans_total_payment;
    }

    /** -------------------------------------------------------- */
    public String get_sched_date() {
        return this.sched_date;
    }

    public void set_sched_date(String sched_date) {
        this.sched_date = sched_date;
    }


    public String get_sched_departure_time() {
        return this.sched_departure_time;
    }

    public void set_sched_departure_time(String sched_departure_time) {
        this.sched_departure_time = sched_departure_time;
    }

    public String get_res_code() {
        return this.res_code;
    }

    public void set_res_code(String res_code) {
        this.res_code = res_code;
    }

    public String get_res_seat_numbers() {
        return this.res_seat_numbers;
    }

    public void set_(String res_seat_numbers) {
        this.res_seat_numbers = res_seat_numbers;
    }

    public String get_class_name() {
        return this.class_name;
    }

    public void set_class_name(String class_name) {
        this.class_name = class_name;
    }

    public String get_bus_number() {
        return this.bus_number;
    }

    public void set_bus_number(String bus_number) {
        this.bus_number = bus_number;
    }


}
