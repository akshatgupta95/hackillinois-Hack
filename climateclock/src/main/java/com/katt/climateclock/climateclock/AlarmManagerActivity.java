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

/**
 * Created by Kevin on 4/12/14.
 */
public class AlarmManagerActivity extends Activity{
    private Button mStartBtn;
    private EditText mTimeSetter;
    private Toast mToast;

    @Override
    public void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_main);

        mStartBtn = (Button) findViewById(R.id.btnSetAlarm);
        mTimeSetter = (EditText) findViewById(R.id.timeSetter);

        mStartBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try
                {
                    int i = Integer.parseInt(mTimeSetter.getText().toString());
                    Intent intent = new Intent(AlarmManagerActivity.this, AlarmReceiverActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(AlarmManagerActivity.this, 2,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, (long) (i *1000 * 60), pendingIntent);

                    if (mToast != null)
                        mToast.cancel();
                    mToast = Toast.makeText(getApplicationContext(),
                            "Alarm has been set for " + i, Toast.LENGTH_LONG);
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


    }
}
