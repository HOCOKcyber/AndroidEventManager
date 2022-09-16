package com.hocok.eventmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hocok.eventmanager.R;
import com.hocok.eventmanager.db.ManagerDB;
import com.hocok.eventmanager.manager.DateManager;

public class PlanDay extends AppCompatActivity {
    EditText textPlan;
    Button btnBack, btnSafe;
    String plan, year, date;
    int month;
    ManagerDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_day);
        year = getIntent().getStringExtra("year");
        month = getIntent().getIntExtra("month", 1);

        textPlan = findViewById(R.id.EditTextPlanDay);
        btnBack = findViewById(R.id.PlanDayBackToMain);
        btnSafe = findViewById(R.id.btnSafePlanDay);


        date = DateManager.createDate(year, Integer.toString(month), DateManager.numberDay);
        System.out.println(DateManager.numberDay);
        System.out.println(date);
        db = new ManagerDB(this);
        try {
            db.openDB();
            plan = db.selectPlan(date);
            textPlan.setText(plan);
            db.closeDB();
        } catch (Exception e) {
            System.out.println("Что то пошло не так с дб вывод");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        btnSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plan = textPlan.getText().toString();
                db.openDB();
                db.insertToDBPlanDay(date, plan);
                db.closeDB();
                finishActivity();
                Toast.makeText(getApplicationContext(), "Сорханено", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void finishActivity() {
        this.finish();
    }
}