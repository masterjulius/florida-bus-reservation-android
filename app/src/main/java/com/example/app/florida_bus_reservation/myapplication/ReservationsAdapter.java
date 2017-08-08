package com.example.app.florida_bus_reservation.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by acer-pc on 08/08/2017.
 */

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ViewHolder> {

    protected Context context;
    protected List<ReservationsData> my_data;

    /** Constructor */
    public ReservationsAdapter(Context context, List<ReservationsData> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_current_reservation, parent, false);
        return new ViewHolder(itemView, this.context, this.my_data);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTicketNumber.setText(my_data.get(position).get_res_code());
    }

    @Override
    public int getItemCount() {
        return this.my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context ctx;
        private List<ReservationsData> my_data;
        public TextView txtTicketNumber;

        public ViewHolder(View itemView, Context context, List<ReservationsData> my_data) {
            super(itemView);
            this.ctx = context;
            this.my_data = my_data;

            this.txtTicketNumber = (TextView) itemView.findViewById(R.id.txtCardTicketNumber);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
