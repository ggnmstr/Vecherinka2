package com.example.vecherinka;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public abstract class GameLEGACY extends AppCompatActivity implements SensorEventListener {
    private Sensor accelerometer;
    private SensorManager sensorManager;


    private final String LOG_TAG = "GameActivity";


    private TextView textView;
    int c;
    private boolean gameStarted;


    private String[] words = {"A","B","C","D","E","F"};


    enum State{
        Ok,Pass,Idle
    }
    private State laststate = State.Idle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        //textView = findViewById(R.id.gametext);
        gameStarted = false;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Для акселемометра (работает) :
        // Нужна третья координата (event.values[2])
        // Киваешь головой (да): отрицательные значения
        // Неправильный ответ : положительные значения
        if (!gameStarted) {
            textView.setText("Переверните телефон - начнем игру!");
            int c = 0;
        }
    }




    @Override
    public void onSensorChanged(SensorEvent event) {

        if ((int)event.values[0] == 9 && !gameStarted) {
            gameStarted = true;
            textView.setText(words[c]);
        }

        try{
            if ((int)event.values[2] >= 6 && gameStarted) {
                updateState(State.Pass);

            } else if ((int)event.values[2] <= -6 && gameStarted) {
                updateState(State.Ok);
            } else if ((int)event.values[2] == 0 && gameStarted){
                updateState(State.Idle);
            }
        } catch (ArrayIndexOutOfBoundsException e){
            c = 0;
        }



    }

    public void updateState(State state){
        if (laststate != State.Idle && state != State.Idle){
            return;
        }
        switch(state){
            case Ok:
                textView.setText("NICE!");
                c++;
                textView.setText(words[c]);
                break;
            case Pass:
                textView.setText("PASS!");
                c++;
                textView.setText(words[c]);
                break;
        }
        laststate = state;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}
