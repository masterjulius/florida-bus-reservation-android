package com.example.app.florida_bus_reservation.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    protected Context context;
    protected List<MyData> my_data;

    /** Assigning the values will be in these variables */
    private int sched_id, bus_id, class_id, seat_count;
    private double seat_price;
    private int has_aircon;
    private String sched_date, sched_time, bus_number, class_name;
    private int client_id;

    public CustomAdapter(Context context, List<MyData> my_data, int client_id) {
        this.context = context;
        this.my_data = my_data;
        this.client_id = client_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_schedule, parent, false);
        return new ViewHolder(itemView, this.context, this.my_data, this.client_id);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.sched_bus_number.setText(my_data.get(position).get_bus_number());
        holder.sched_date.setText(my_data.get(position).get_sched_date());
        holder.sched_time.setText(my_data.get(position).get_sched_time());

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView sched_bus_number;
        public TextView sched_date;
        public TextView sched_time;

        public Context ctx;
        public List<MyData> my_list;
        public int client_id;

        public ViewHolder(View itemView, Context context, List<MyData> my_data, int client_id) {
            super(itemView);
            this.ctx = context;
            this.my_list = my_data;
            this.client_id = client_id;
            sched_bus_number = (TextView) itemView.findViewById(R.id.schedule_bus_number);
            sched_date = (TextView) itemView.findViewById(R.id.schedule_date);
            sched_time = (TextView) itemView.findViewById(R.id.schedule_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent intent = new Intent(this.ctx, busDetails.class);
            intent.putExtra("client_id", this.client_id);
            intent.putExtra("sched_id", my_list.get(position).get_sched_id());
            intent.putExtra("bus_id", my_list.get(position).get_bus_id());
            intent.putExtra("class_id", my_list.get(position).get_class_id());
            intent.putExtra("seat_count", my_list.get(position).get_seat_count());
            intent.putExtra("seat_price", my_list.get(position).get_seat_price());
            intent.putExtra("has_aircon", my_list.get(position).get_has_aircon());
            intent.putExtra("sched_date", my_list.get(position).get_sched_date());
            intent.putExtra("sched_time", my_list.get(position).get_sched_time());
            intent.putExtra("bus_number", my_list.get(position).get_bus_number());
            intent.putExtra("class_name", my_list.get(position).get_class_name());
            this.ctx.startActivity(intent);
        }
    }
}