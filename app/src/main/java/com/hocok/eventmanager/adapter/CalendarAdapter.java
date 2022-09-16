package com.hocok.eventmanager.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hocok.eventmanager.manager.DateManager;
import com.hocok.eventmanager.manager.ManagerCalendarText;
import com.hocok.eventmanager.MyCallBack;
import com.hocok.eventmanager.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private int countMonthDays;
    private ArrayList<String> arrayNameDays = new ArrayList<>(Arrays.asList("пн", "вт", "ср", "чт", "пт", "сб", "вс"));
    private ArrayList<String> eventDays = new ArrayList<>();
    ManagerCalendarText managerCalendar;
    MyCallBack callBack;

    public CalendarAdapter(Context context, int countMonthDays, ArrayList<String> fakeDay, ManagerCalendarText managerCalendar, ArrayList<String> eventDays) {
        this.countMonthDays = countMonthDays;
        this.inflater = LayoutInflater.from(context);
        this.managerCalendar = managerCalendar;
        arrayNameDays.addAll(fakeDay);
        this.callBack = (MyCallBack) context;
        this.eventDays = eventDays;
    }

    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_day, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String day;
        if (position < arrayNameDays.size()) {
            day = arrayNameDays.get(position);
        } else {
            day = Integer.toString(position - arrayNameDays.size() + 1);
            holder.textDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    managerCalendar.reverseText();
                    callBack.newChoiceDay(Integer.toString(position - arrayNameDays.size() + 1));
                    holder.textDay.setBackgroundResource(R.drawable.outline_green);
                }
            });
            managerCalendar.addToTextViewList(holder.textDay);
            if (eventDays.contains(day) || eventDays.contains("0"+day)) {
                holder.textDay.setBackgroundResource(R.drawable.outline_grey);
            }
            if (Objects.equals(day, DateManager.numberDay) || Objects.equals("0" + day, DateManager.numberDay)) {
                holder.textDay.setBackgroundResource(R.drawable.outline_green);
            }
        }
        holder.textDay.setText(day);
    }

    @Override
    public int getItemCount() {
        return countMonthDays + arrayNameDays.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textDay;

        ViewHolder(View view) {
            super(view);
            textDay = view.findViewById(R.id.textNameDayCalendar);
        }
    }
}
