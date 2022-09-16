package com.hocok.eventmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hocok.eventmanager.model.Event;

import java.util.ArrayList;

public class ManagerDB {
    private Context context;
    private EventManagerDBHelper dbHelper;
    private SQLiteDatabase db;

    public ManagerDB(Context context) {
        this.context = context;
        dbHelper = new EventManagerDBHelper(context);
    }

    public void openDB() {
        db = dbHelper.getWritableDatabase();
    }

    public void insertToDBPlanDay(String date, String plan) {
        ContentValues cv = new ContentValues();
        Cursor cursor = db.query(ConstantsDB.TABLE_NAME_PLAN_DAY, null, ConstantsDB.DATE + " = ?",
                new String[]{date}, null, null, null);
        if (cursor.moveToNext()) {
            cv.put(ConstantsDB.PLAN, plan);
            db.update(ConstantsDB.TABLE_NAME_PLAN_DAY, cv, ConstantsDB.DATE + " = ?", new String[]{date});
        } else {
            cv.put(ConstantsDB.DATE, date);
            cv.put(ConstantsDB.PLAN, plan);
            db.insert(ConstantsDB.TABLE_NAME_PLAN_DAY, null, cv);
        }
    }

    public String selectPlan(String date) {
        Cursor cursor = db.query(ConstantsDB.TABLE_NAME_PLAN_DAY, null, ConstantsDB.DATE + " = ?", new String[]{date}, null, null, null);
        cursor.moveToFirst();
        String plan = cursor.getString(cursor.getColumnIndexOrThrow(ConstantsDB.PLAN));
        cursor.close();
        return plan;
    }

    public ArrayList<String> selectDay(String firstDate, String lastDate) {
        Cursor cursor;
        ArrayList<String> days = new ArrayList<>();

        cursor = db.query(ConstantsDB.TABLE_NAME_PLAN_DAY, null, "? <= " + ConstantsDB.DATE + " AND " + ConstantsDB.DATE + " <= ?",
                new String[]{firstDate, lastDate}, null, null, null);

        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow(ConstantsDB.DATE));
            days.add(date);
        }

        cursor = db.query(ConstantsDB.TABLE_NAME_EVENT, null, "? <= " + ConstantsDB.DATE + " AND " + ConstantsDB.DATE + " <= ?",
                new String[]{firstDate, lastDate}, null, null, null);

        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndexOrThrow(ConstantsDB.DATE));
            days.add(date);
        }

        cursor.close();
        return days;
    }

    public void insertToDBEvent(String date, String name, String start, String remind, String comment) {
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.DATE, date);
        cv.put(ConstantsDB.NAME, name);
        cv.put(ConstantsDB.START_TIME, start);
        cv.put(ConstantsDB.REMIND_TIME, remind);
        cv.put(ConstantsDB.COMMENT, comment);
        db.insert(ConstantsDB.TABLE_NAME_EVENT, null, cv);
    }

    public ArrayList<Event> selectDayEvents(String date) { // сделать класс Event что бы было легче,а то список в списке не кртуо
        ArrayList<Event> events = new ArrayList<>();

        Cursor cursor = db.query(ConstantsDB.TABLE_NAME_EVENT, null, ConstantsDB.DATE + " = ?",
                new String[]{date}, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ConstantsDB.NAME));
            String start = cursor.getString(cursor.getColumnIndexOrThrow(ConstantsDB.START_TIME));
            String remind = cursor.getString(cursor.getColumnIndexOrThrow(ConstantsDB.REMIND_TIME));
            String comment = cursor.getString(cursor.getColumnIndexOrThrow(ConstantsDB.COMMENT));
            String dateEvent = cursor.getString(cursor.getColumnIndexOrThrow(ConstantsDB.DATE));
            Event event = new Event(name, start, remind, comment, dateEvent);
            events.add(event);
        }
        cursor.close();
        return events;
    }

    public void changeEventComment(String comment,String date){
        ContentValues cv = new ContentValues();
        cv.put(ConstantsDB.COMMENT, comment);
        db.update(ConstantsDB.TABLE_NAME_EVENT, cv, ConstantsDB.DATE + " = ?", new String[]{date});
    }

    public void closeDB() {
        dbHelper.close();
    }
}
