package com.hocok.eventmanager.db;

public class ConstantsDB {
    public static final String DATABASE_NAME = "EventManager.db";
    public static final int DATABASE_VERSION = 2;


    public static final String TABLE_NAME_PLAN_DAY = "plan_day";
    public static final String ID = "id";
    public static final String PLAN = "plan";
    public static final String DATE = "DATE";
    public static final String SQL_CREATE_TABLE_PLAN_DAY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PLAN_DAY + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DATE + " DATE, " +
            PLAN + " TEXT);";



    public static final String TABLE_NAME_EVENT = "event";
    public static final String NAME = "name";
    public static final String START_TIME = "start";
    public static final String REMIND_TIME = "remind";
    public static final String COMMENT = "comment";
    public static final String SQL_CREATE_TABLE_EVENT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_EVENT + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DATE + " DATE, " +
            NAME + " TEXT, " +
            START_TIME + " TEXT, " +
            REMIND_TIME + " TEXT, " +
            COMMENT + " TEXT);";

    public static final String SQL_DELETE_TABLE_PLAN_DAY = "DROP TABLE IF EXISTS " + TABLE_NAME_PLAN_DAY;
    public static final String SQL_DELETE_TABLE_EVENT = "DROP TABLE IF EXISTS " + TABLE_NAME_EVENT;

}
