package com.hocok.eventmanager;

import androidx.annotation.NonNull;

public class DateManager {
    private static String date;
    public static String numberDay = "01";
    public static String[] monthsName = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    public static String createDate(String year, String month, String day){
        if (month.length() == 1){
            month = "0" +month;
        }
        date = year + '-' + month +'-' + day;
        return date;
    }

    public static String createDate(Integer year, Integer m, String day){
        String month = Integer.toString(m);
        if (month.length() == 1){
            month = "0" + month;
        }
        date = Integer.toString(year) + '-' + month +'-' + day;
        return date;
    }

    public static String createDate(Integer year, Integer m){
        String month = Integer.toString(m);
        if (month.length() == 1){
            month = "0" + month;
        }
        date = Integer.toString(year) + '-' + month +'-';
        return date;
    }
    public static void modeNumberDay(String day){
        if(day.length() == 1){
            day = "0" + day;
        }
        numberDay = day;
    }

    public static String monthName(Integer month){
        return  monthsName[month];
    }
}
