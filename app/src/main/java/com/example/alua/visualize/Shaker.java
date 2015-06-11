package com.example.alua.visualize;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;

public class Shaker implements SensorEventListener {

    private static final String SENSOR_SERVICE = Context.SENSOR_SERVICE;
    ArrayList<Float> valueStack;
    long lastUpdate = 0;
    private SensorManager sensorMgr;
    private Sensor mAccelerometer;
    private boolean accelSupported;
    private long timeInMillis;
    private long threshold;
    private OnShakerTreshold listener;
    private float last_x;
    private float last_y;
    private float last_z;

    public Shaker(Context context, OnShakerTreshold listener, long timeInMillis, long threshold) {
        try {
            this.timeInMillis = timeInMillis;
            this.threshold = threshold;
            this.listener = listener;
            if (timeInMillis < 100) {
                throw new Exception("timeInMillis < 100ms");
            }
            valueStack = new ArrayList<Float>((int) (timeInMillis / 100));
            sensorMgr = (SensorManager) context.getSystemService(SENSOR_SERVICE);
            mAccelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            accelSupported = sensorMgr.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
            if (!accelSupported) {
                stop();
                throw new Exception("Sensor is not supported");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            sensorMgr.unregisterListener(this, mAccelerometer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.finalize();
    }

    public void onSensorChanged(SensorEvent event) {
        try {
            if (event.sensor == mAccelerometer) {
                long curTime = System.currentTimeMillis();
                if ((curTime - lastUpdate) > getNumberOfMeasures()) {

                    lastUpdate = System.currentTimeMillis();
                    float[] values = event.values;
                    if (valueStack.size() > (int) getNumberOfMeasures())
                        valueStack.remove(0);
                    float x = (int) (values[SensorManager.DATA_X]);
                    float y = (int) (values[SensorManager.DATA_Y]);
                    float z = (int) (values[SensorManager.DATA_Z]);
                    float speed = Math.abs((x + y + z) - (last_x + last_y + last_z));

                    valueStack.add(speed);

                    String posText = String.format("X:%4.0f Y:%4.0f Z:%4.0f", (x - last_x), (y - last_y), (z - last_z));

                    last_x = (x);
                    last_y = (y);
                    last_z = (z);

                    float sumOfValues = 0;
                    float avgOfValues = 0;

                    for (float f : valueStack) {
                        sumOfValues = (sumOfValues + f);
                    }
                    avgOfValues = sumOfValues / (int) getNumberOfMeasures();

                    if (avgOfValues >= threshold) {
                        listener.onTreshold();
                        valueStack.clear();
                    }

                    System.out.println(String.format("M: %+4d A: %5.0f V: %4.0f %s", valueStack.size(), avgOfValues, speed, posText));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private long getNumberOfMeasures() {
        return timeInMillis / 100;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public interface OnShakerTreshold {
        public void onTreshold();
    }
}