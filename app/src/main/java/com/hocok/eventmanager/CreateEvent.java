package com.hocok.eventmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hocok.eventmanager.db.ManagerDB;

public class CreateEvent extends AppCompatActivity {
    String year;
    int month;
    TextView textDate;
    Button back, safe;
    EditText editNameEvent, editStartEvent, editRemindEvent, editCommentEvent;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        year = getIntent().getStringExtra("year");
        month = getIntent().getIntExtra("month", 1);

        textDate = findViewById(R.id.textDate);
        textDate.setText(DateManager.numberDay + ' ' + DateManager.monthName(month) + ' ' + year);

        editNameEvent = findViewById(R.id.editTextNameEvent);
        editStartEvent = findViewById(R.id.editTextTimeStartEvent);
        editCommentEvent = findViewById(R.id.editTextCommentEvent);
        editRemindEvent = findViewById(R.id.editTextTimeRemindEvent);

        back = findViewById(R.id.btnBackDateEvent);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        ManagerDB db = new ManagerDB(this);
        safe = findViewById(R.id.btnSafeDateEvent);
        safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.openDB();
                String date = DateManager.createDate(year, Integer.toString(month), DateManager.numberDay);
                db.insertToDBEvent(date, editNameEvent.getText().toString(), editStartEvent.getText().toString(),
                        editRemindEvent.getText().toString(), editCommentEvent.getText().toString());
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