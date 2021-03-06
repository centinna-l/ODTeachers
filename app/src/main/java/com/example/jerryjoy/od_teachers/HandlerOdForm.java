package com.example.jerryjoy.od_teachers;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class HandlerOdForm extends RecyclerView.Adapter<HandlerOdForm.ViewHolder> {

    private Context context;
    private ArrayList<String> names;
    private ArrayList<String> regno;
    private ArrayList<String> sec;
    private ArrayList<String> reasons;
    private ArrayList<String> dates;
    public MyCallback callback;

    public HandlerOdForm(Context act, ArrayList<String> name, ArrayList<String> regno, ArrayList<String> sec, ArrayList<String> reason, ArrayList<String> date, MyCallback call) {
        this.context = act;
        this.names = name;
        this.regno = regno;
        this.sec = sec;
        this.reasons = reason;
        this.dates = date;
        this.callback = call;
    }

    public static interface MyCallback {
        void handleAccept(int position);

        void handleReject(int position);
    }

    @Override
    public HandlerOdForm.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rows, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HandlerOdForm.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(names.get(i));
        viewHolder.reg.setText(regno.get(i));
        viewHolder.section.setText(sec.get(i));
        viewHolder.reason.setText(reasons.get(i));
        viewHolder.date.setText(dates.get(i));
        viewHolder.callback = callback;
        viewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.handleAccept(viewHolder.getAdapterPosition());
            }
        });
        viewHolder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.handleReject(viewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected MyCallback callback;
        private TextView name;
        private TextView reg;
        private TextView section;
        private TextView reason;
        private TextView date;
        private Button accept;
        private Button reject;

        public ViewHolder(View view) {

            super(view);
            name = view.findViewById(R.id.name);
            reg = view.findViewById(R.id.reg);
            section = view.findViewById(R.id.sec);
            reason = view.findViewById(R.id.reason);
            date = view.findViewById(R.id.date);
            accept = view.findViewById(R.id.accept);
            reject = view.findViewById(R.id.reject);
        }
    }

}