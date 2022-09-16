package com.hocok.eventmanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hocok.eventmanager.model.Event;
import com.hocok.eventmanager.MyCallBack;
import com.hocok.eventmanager.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    ArrayList<Event> events;
    MyCallBack callback;

    public EventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.events = events;
        this.callback = (MyCallBack) context;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_plan, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textName.setText(events.get(position).name);
        String time = events.get(position).timeStart;
        holder.textTime.setText(time);
        holder.layoutEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.toEventComment(events.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textTime;
        RelativeLayout layoutEvent;

        ViewHolder(View view) {
            super(view);
            textName = view.findViewById(R.id.nameEvent);
            textTime = view.findViewById(R.id.timeEvent);
            layoutEvent = view.findViewById(R.id.layoutEvent);
        }
    }
}
