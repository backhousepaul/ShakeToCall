package com.example.alua.visualize;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    public PowerManager.WakeLock wl;
    protected PowerManager.WakeLock mWakeLock;
    Context context = this;
    private ShakeEventListener mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); ///////////////////////////////////////////////////////////////
        setContentView(R.layout.activity_main);
        //StartService();
        startService(new Intent(getBaseContext(), RunService.class));
        Button serve=(Button) findViewById(R.id.button2);
        serve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartService();
            }
        });
       Button off=(Button) findViewById(R.id.button);
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartService();
                String idialer = "com.android.idialer";

                try
                {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.setComponent(ComponentName.unflattenFromString(idialer));
                    intent.addCategory(Intent.CATEGORY_LAUNCHER );
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    e.printStackTrace();
                }


            }
 });
       /* mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeEventListener(new ShakeEventListener.OnShakeListener() {
            @Override
            public void onShake() {
                // getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                Toast.makeText(getBaseContext(), "Its woking", Toast.LENGTH_LONG).show();
                // Do stuff!
                ///THIS INTENT BELOW STARTS THE PHONE DIALER TO ENABLE A PERSON CALL.
                Intent intent = new Intent("android.intent.action.DIAL");
                startActivity(intent);
                //THIS IS A METHOD CALL THAT STARTS AN THE SERVICE

            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        //StartService();
        //mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        //mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    private void unlockScreen() {
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    private void screenOnOff() {


    }

    //THIS IS THE METHOD THAT STARTS THE SERVICE
    private void StartService() {
// Explicitly start My Service
        Intent intent = new Intent(this, RunService.class);
// TODO Add extras if required.
        startService(intent);
        Toast.makeText(this, "started service", Toast.LENGTH_SHORT).show();
    }

}



