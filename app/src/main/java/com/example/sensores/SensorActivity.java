package com.example.sensores;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SensorActivity extends AppCompatActivity {

    SensorManager mySensorManager;

    private AnimatedView mAnimatedView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        mAnimatedView = new AnimatedView(this);
        setContentView(mAnimatedView);

        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor accelSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mySensorManager.registerListener(
                accelerometerSensorListener,
                accelSensor,
                SensorManager.SENSOR_DELAY_FASTEST);

    }

    private final SensorEventListener accelerometerSensorListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                mAnimatedView.onSensorEvent(event);

            }

        }

    };

    public class AnimatedView extends View {

        private static final int radioBall = 50; //pixels

        private Paint mPaint;
        private int x;
        private int y;
        private int viewWidth;
        private int viewHeight;

        public AnimatedView(Context context) {
            super(context);
            mPaint = new Paint();
            mPaint.setColor(Color.BLUE);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;
        }

        public void onSensorEvent (SensorEvent event) {
            int temp_r = radioBall;
            x = x - (int) event.values[0];
            y = y + (int) event.values[1];

            if (x <= temp_r) {
                x = temp_r;
            }
            if (x >= viewWidth - temp_r) {
                x = viewWidth - temp_r;
            }
            if (y <= temp_r) {
                y = temp_r;
            }
            if (y >= viewHeight - temp_r) {
                y = viewHeight - temp_r;
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(x, y, radioBall, mPaint);
            invalidate();
        }
    }
}


