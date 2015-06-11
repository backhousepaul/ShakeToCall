package com.example.alua.visualize;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class RunService extends Service {
    private ShakeEventListener mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private NotificationManager mNM;
    private int NOTIFICATION = R.string.local_service_started;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotification();
        Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNM.cancel(NOTIFICATION);
        mSensorManager.unregisterListener(mShakeDetector);
        Toast.makeText(this, "Service Destroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
        Toast.makeText(this, "on start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        //Toast.makeText(this,"Service start",Toast.LENGTH_SHORT);
        //Intent intents = new Intent(getBaseContext(),MainActivity.class);
        //intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intents);
        Toast.makeText(this, "on start command", Toast.LENGTH_SHORT).show();
        Log.d("TAG", "onStart");

        // mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeEventListener(new ShakeEventListener.OnShakeListener() {
            @Override
            public void onShake() {
                // getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                Toast.makeText(getBaseContext(), "Its woking", Toast.LENGTH_LONG).show();
                // Do stuff!
                ///THIS INTENT BELOW STARTS THE PHONE DIALER TO ENABLE A PERSON CALL.
                Intent intent = new Intent("android.intent.action.DIAL");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //THIS IS A METHOD CALL THAT STARTS AN THE SERVICE

            }
        });
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

        return START_STICKY;

    }

    void shakeListner() {
        //mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        //mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeEventListener(new ShakeEventListener.OnShakeListener() {
            @Override
            public void onShake() {
                Toast.makeText(getBaseContext(), "onShake", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getBaseContext(), "Its woking", Toast.LENGTH_SHORT).show();
                // Do stuff!
                ///THIS INTENT BELOW STARTS THE PHONE DIALER TO ENABLE A PERSON CALL.
                Intent intent = new Intent("android.intent.action.DIAL");
                startActivity(intent);
                //THIS IS A METHOD CALL THAT STARTS AN THE SERVICE
            }
        });
    }

    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(R.string.local_service_started);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.iphone, text,
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, getText(R.string.local_service_label),
                text, contentIntent);

        // Send the notification.
        mNM.notify(NOTIFICATION, notification);


    }
}