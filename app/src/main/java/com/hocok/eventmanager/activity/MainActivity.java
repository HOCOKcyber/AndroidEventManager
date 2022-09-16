package com.hocok.eventmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hocok.eventmanager.MyCallBack;
import com.hocok.eventmanager.R;
import com.hocok.eventmanager.adapter.CalendarAdapter;
import com.hocok.eventmanager.adapter.EventAdapter;
import com.hocok.eventmanager.db.ManagerDB;
import com.hocok.eventmanager.manager.DateManager;
import com.hocok.eventmanager.manager.ManagerCalendarText;
import com.hocok.eventmanager.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements MyCallBack {
    Calendar calendar;
    Button btnNextMonth, btnOldMonth, btnGoPlanDay, btnGoCreateEvent;
    TextView textYear, textMonth;
    ManagerCalendarText managerCalendar;
    RecyclerView CalendarRecyclerView, EventRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Date currentTime = Calendar.getInstance().getTime();
        // currentTime.getYear()+1900 - год
        //currentTime.getMonth() - месяц
        //currentTime.getDate() - число

        calendar = new GregorianCalendar(currentTime.getYear()+1900, currentTime.getMonth(), 1);
        DateManager.modeNumberDay(Integer.toString(currentTime.getDate()));
        CalendarRecyclerView = findViewById(R.id.recycleCalendar);
        EventRecyclerView = findViewById(R.id.recycleEvent);
        createCalendar();

        btnNextMonth = findViewById(R.id.btnNextMonth);
        btnOldMonth = findViewById(R.id.btnOldMonth);
        btnGoPlanDay = findViewById(R.id.btnToPlanDay);
        btnGoCreateEvent = findViewById(R.id.btnToCreateEvent);

        textYear = findViewById(R.id.textYear);
        textMonth = findViewById(R.id.textMonth);
        textYear.setText(Integer.toString(calendar.get(Calendar.YEAR)));
        textMonth.setText(DateManager.monthName(calendar.get(Calendar.MONTH)));

        btnNextMonth.setOnClickListener(this::onClick);
        btnOldMonth.setOnClickListener(this::onClick);

        btnGoPlanDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlanDay.class);
                intent.putExtra("year", Integer.toString(calendar.get(Calendar.YEAR)));
                intent.putExtra("month", calendar.get(Calendar.MONTH));
                startActivity(intent);
            }
        });

        btnGoCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateEvent.class);
                intent.putExtra("year", Integer.toString(calendar.get(Calendar.YEAR)));
                intent.putExtra("month", calendar.get(Calendar.MONTH));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        newChoiceDay(DateManager.numberDay);
    }

    public void createCalendar() {
        /*
        calendar.get(Calendar.DAY_OF_MONTH)         Число месяца
        calendar.get(Calendar.DATE)                 Число месяца
        calendar.get(Calendar.DAY_OF_WEEK)          День недели
        calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH) Кол недель в месяце
        calendar.get(Calendar.DAY_OF_YEAR)          Номер деня в году ()
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH) Максимальное чило которое может принимать константа число месяца (кол дней в месяца)
        */

        int countMonthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);// Кол дней в месяце
        int CountFakeDays; // например если 1-ое число - пятница
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            CountFakeDays = 6;
        } else {
            CountFakeDays = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        }

        // Создание не существующих дней (если например первое число чт)
        ArrayList<String> fakeDay = new ArrayList<>();
        for (int i = 0; i < CountFakeDays; i++) {
            fakeDay.add("*");
        }

        // Получение дней где есть какие то планы или события
        String date = DateManager.createDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        String firstDate = date + "01";
        String lastDate = date + "31";
        ManagerDB db = new ManagerDB(this);
        db.openDB();
        ArrayList<String> eventDays_full = db.selectDay(firstDate, lastDate);
        db.closeDB();

        ArrayList<String> eventDays = new ArrayList<>();
        for (String day : eventDays_full) {
            eventDays.add(day.substring(8));
        }

        // Создание менеджера для bg текста
        managerCalendar = new ManagerCalendarText(this);

        // Создание и вывод календаря на экран
        CalendarAdapter adapter = new CalendarAdapter(this, countMonthDays, fakeDay, managerCalendar, eventDays);
        CalendarRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        CalendarRecyclerView.setAdapter(adapter);
    }

    public void createEventRecycle() {
        String date = DateManager.createDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), DateManager.numberDay);

        ManagerDB db = new ManagerDB(this);
        db.openDB();
        ArrayList<Event> events = db.selectDayEvents(date);
        db.closeDB();
        EventAdapter adapter = new EventAdapter(this, events);
        EventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventRecyclerView.setAdapter(adapter);
    }


    @SuppressLint("SetTextI18n")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNextMonth:
                calendar.add(Calendar.MONTH, +1);
                break;
            case R.id.btnOldMonth:
                calendar.add(Calendar.MONTH, -1);
                break;
        }
        createCalendar();
        textYear.setText(Integer.toString(calendar.get(Calendar.YEAR)));
        textMonth.setText(DateManager.monthName(calendar.get(Calendar.MONTH)));
    }

    @Override
    public void newChoiceDay(String choiceDay) {
        DateManager.modeNumberDay(choiceDay);
        createEventRecycle();
        managerCalendar.pointDay(DateManager.createDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)));
    }

    @Override
    public  void toEventComment(Event event){
        Intent intent = new Intent(MainActivity.this, EventComment.class);
        intent.putExtra("event", event);
        System.out.println(event);
        startActivity(intent);
    }
}