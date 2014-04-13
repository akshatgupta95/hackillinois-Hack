package com.katt.climateclock.climateclock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

/**
 * Created by Kevin on 4/12/14.
 */
public class AlarmManagerActivity extends Activity{
    private Button mStartBtn;
    private TimePicker mTimePicker;
    private Toast mToast;
    private Button mStopBtn;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_main);

        mTimePicker = (TimePicker) findViewById(R.id.timePicker);
        mTimePicker.setIs24HourView(true);
        mStartBtn = (Button) findViewById(R.id.btnSetAlarm);


        mStartBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try
                {
                    int h = mTimePicker.getCurrentHour();
                    int m = mTimePicker.getCurrentMinute();
                    Intent intent = new Intent(AlarmManagerActivity.this, AlarmReceiverActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(AlarmManagerActivity.this, 2,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);
//                    int ms = (h * 3600 * 1000) + (m * 60 * 1000);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, h);
                    calendar.set(Calendar.MINUTE, m);

                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*60*1, pendingIntent);


                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(getApplicationContext(),
                            "Alarm has been set for " + h + ":" + m, Toast.LENGTH_LONG);
                    mToast.show();
                }
                catch (NumberFormatException e)
                {
                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(AlarmManagerActivity.this, "Please enter a number and try again.",
                            Toast.LENGTH_LONG);
                    mToast.show();
                    Log.i("AlarmManagerActivity", "Number Format Exception");
                }

            }
        });

        mStopBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(AlarmManagerActivity.this, AlarmReceiverActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(AlarmManagerActivity.this, 3,
                            intent, 0);

                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.cancel(pendingIntent);

                if (mToast != null)
                    mToast.cancel();
                mToast = Toast.makeText(getApplicationContext(),
                    "Alarm has been dicontinued", Toast.LENGTH_LONG);
                mToast.show();
                }
        });


    }
}
