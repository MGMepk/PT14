package com.dam.manuelgarcia.pt14;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle("Alarm");
    }

    public void startAlert(View view) {
        EditText text = findViewById(R.id.time);
        if (text.getText().toString().equals("")) {
            Toast.makeText(this, "Insert a number!!",
                    Toast.LENGTH_LONG).show();
        } else {
            int i = Integer.parseInt(text.getText().toString());

            Intent intent = new Intent(this, TimerReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                        + (i * 1000), pendingIntent);
            }
            Toast.makeText(this, "Alarm set in " + i + " seconds",
                    Toast.LENGTH_LONG).show();
        }
    }
}
