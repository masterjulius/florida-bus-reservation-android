package com.example.app.florida_bus_reservation.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer-pc on 08/06/2017.
 */

class SeatsAdapter extends RecyclerView.Adapter<SeatsAdapter.ViewHolder> {
    protected Context context;
    protected List<SeatsData> my_data;
    protected int seat_count;
    protected double seat_price;
    protected TextView txtViewTotal;

    /** Variables */


    /** Constructor */
    public SeatsAdapter(Context context, List<SeatsData> my_data, int seat_count, double seat_price, TextView txtViewTotal) {
        this.context = context;
        this.my_data = my_data;
        this.seat_count = seat_count;
        this.seat_price = seat_price;
        this.txtViewTotal = txtViewTotal;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_seats, parent, false);
        return new ViewHolder(itemView, this.context, this.my_data, this.seat_price, this.txtViewTotal);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int seatNumber = position + 1;
//        String testName = Integer.toString(seatNumber) + Integer.toString(my_data.get(0).get_seat_number()) + " A";
        holder.myCard.setCardBackgroundColor(Color.rgb(255, 64, 129));
        boolean isAvailable = true;
        for (int i = 0; i < my_data.size(); i++) {
            if (seatNumber == my_data.get(i).get_seat_number()) {
                isAvailable = false;
                break;
            }
        }
        if (isAvailable == false) {
            holder.myCard.setCardBackgroundColor(Color.rgb(0, 150, 136));
        }
        holder.txtBusNumber.setTextColor(Color.WHITE);
        holder.txtBusNumber.setText(Integer.toString(seatNumber));
    }

    @Override
    public int getItemCount() {
        return this.seat_count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int bus_number;
        private Context ctx;
        private List<SeatsData> my_data;
        private double seat_price;

        protected ArrayList<Integer> selectedSeats = new ArrayList<Integer>();

        private CardView myCard;

        public TextView txtBusNumber, txtViewTotal;

        public ViewHolder(View itemView, Context context, List<SeatsData> my_data, double seat_price, TextView txtViewTotal) {
            super(itemView);
            this.ctx = context;
            this.my_data = my_data;
            this.seat_price = seat_price;

            this.txtViewTotal = txtViewTotal;

            this.txtBusNumber = (TextView) itemView.findViewById(R.id.txtCardSeatNumber);

            this.myCard = (CardView) itemView.findViewById(R.id.card_seats);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int seatNumber = position + 1;

            boolean isSelected = false;
            int theIndex = 0;

            /** Check first if in available group */
            boolean isAvailable = true;
            for (int i = 0; i < my_data.size(); i++) {
                if (seatNumber == my_data.get(i).get_seat_number()) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable != false) {
                /** Then check if it is already checked */
                if (this.selectedSeats.contains(seatNumber) != true) {
                    // add
                    isSelected = true;
                    double seatCalculated = Calculations_Class.seatComputedPrice;
                    Calculations_Class.sumNumber(seat_price);
                    this.selectedSeats.add(seatNumber);
                    Global_Class.addSeat(seatNumber);
                    this.myCard.setCardBackgroundColor(Color.rgb(158, 158, 158));
                } else {
                    // remove
                    isSelected = false;
                    int index = 0;
                    for (int i = 0; i < my_data.size(); i++) {
                        if (seatNumber == my_data.get(i).get_seat_number()) {
                            index = i;
                            break;
                        }
                    }


                    theIndex = Global_Class.seatList.indexOf(seatNumber);
                    this.txtViewTotal.setText(Double.toString(25.1));
                    this.selectedSeats.remove(index);
                    Global_Class.seatList.remove(theIndex);
                    this.myCard.setCardBackgroundColor(Color.rgb(255, 64, 129));
                }

            } else {
                Toast.makeText(this.ctx, "This seat is already reserved.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
