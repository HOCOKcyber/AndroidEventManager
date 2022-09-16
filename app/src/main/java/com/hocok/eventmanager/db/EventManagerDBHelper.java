package com.hocok.eventmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventManagerDBHelper extends SQLiteOpenHelper {

    public EventManagerDBHelper(Context context) {
        super(context, ConstantsDB.DATABASE_NAME, null, ConstantsDB.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConstantsDB.SQL_CREATE_TABLE_PLAN_DAY);
        db.execSQL(ConstantsDB.SQL_CREATE_TABLE_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(ConstantsDB.SQL_DELETE_TABLE_PLAN_DAY);
        db.execSQL(ConstantsDB.SQL_DELETE_TABLE_EVENT);
        onCreate(db);
    }
}
