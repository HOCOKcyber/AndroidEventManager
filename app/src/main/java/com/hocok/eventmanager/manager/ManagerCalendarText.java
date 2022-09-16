package com.hocok.eventmanager.manager;

import android.content.Context;
import android.widget.TextView;

import com.hocok.eventmanager.MyCallBack;
import com.hocok.eventmanager.R;
import com.hocok.eventmanager.db.ManagerDB;
import com.hocok.eventmanager.manager.DateManager;

import java.util.ArrayList;

public class ManagerCalendarText{
    public ArrayList<TextView> textList = new ArrayList<TextView>();
    MyCallBack callBack;
    private Context context;

    public ManagerCalendarText(Context context) {
        this.context = context;
    }

    public void reverseText() {
        for (int i = 0; i < textList.size(); i++) {
            TextView textI = textList.get(i);
            textI.setBackgroundResource(R.drawable.outline_black);
        }
    }

    public void pointDay(String date) {
        String firstDate = date + "01";
        String lastDate = date + "31";
        ManagerDB db = new ManagerDB(context);
        db.openDB();
        ArrayList<String> days = db.selectDay(firstDate, lastDate);
        db.closeDB();

        for (int i = 0; i < textList.size(); i++) {
            TextView textDay = textList.get(i);
            String allDate;
            if ((textDay.getText().toString()).length() == 1) {
                allDate = date + "0" + textDay.getText().toString();
            } else {
                allDate = date + textDay.getText().toString();
            }
            if (days.contains(allDate) && !(allDate.equals(date+ DateManager.numberDay)) ) {
                textDay.setBackgroundResource(R.drawable.outline_grey);
            }
        }
    }

    public void addToTextViewList(TextView textView){
        textList.add(textView);
    }

}
