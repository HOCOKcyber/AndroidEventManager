package com.hocok.eventmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hocok.eventmanager.db.ManagerDB;

public class EventComment extends AppCompatActivity {
    Event event;
    TextView textName, textTimeStart;
    EditText edComment;
    Button btnBack, btnChange;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_comment);
        event = (Event) getIntent().getSerializableExtra("event");

        textName = findViewById(R.id.textNameEventInComment);
        textTimeStart = findViewById(R.id.textTimeEvent);
        edComment = findViewById(R.id.editTextComment);
        btnBack = findViewById(R.id.btnBackEventComment);
        btnChange = findViewById(R.id.btnChange);

        textName.setText(event.name);
        textTimeStart.setText(event.timeStart + " " + event.date);
        edComment.setText(event.comment);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeComment();
            }
        });
    }

    private void finishActivity() {
        this.finish();
    }

    private void changeComment(){
        ManagerDB db = new ManagerDB(this);
        db.openDB();
        db.changeEventComment(edComment.getText().toString(), event.date);
        db.closeDB();
        Toast.makeText(this,"Изменено", Toast.LENGTH_SHORT).show();
        finishActivity();
    }
}