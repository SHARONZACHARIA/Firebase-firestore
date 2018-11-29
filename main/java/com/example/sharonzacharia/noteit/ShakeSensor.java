package com.example.sharonzacharia.noteit;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

public  class  ShakeSensor  implements SensorEventListener
{
     private Sensor  Accelrometer;
     public static SensorManager  sensorManager;
     private static final float SHAKE_THRESHOLD = 7.0f; // m/S**2
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;
    public  static  String INTENT_KEY = "com.example.sharonzacharia.noteit.SHAKE_SENSOR";
     Context c;


    public   void initialize(Context c)
    {     this.c = c;
         sensorManager = (SensorManager) c.getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

                float x =sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                double acceleration = Math.sqrt(Math.pow(x, 2) +
                        Math.pow(y, 2) +
                        Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;
                Log.d("noteit", "Acceleration is " + acceleration + "m/s^2");

                if (acceleration > SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime;
                    Log.d("noteit", "Shake, Rattle, and Roll");

                    Intent i = new Intent();
                    i.setAction(INTENT_KEY);
                    c.sendBroadcast(i);
                }
            }
        }
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}