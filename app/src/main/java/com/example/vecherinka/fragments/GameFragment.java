package com.example.vecherinka.fragments;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.vecherinka.App;
import com.example.vecherinka.CountDownTimerWithPause;
import com.example.vecherinka.MainActivity;
import com.example.vecherinka.R;
import com.example.vecherinka.database.ThemeDAO;
import com.example.vecherinka.database.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.SENSOR_SERVICE;

public class GameFragment extends Fragment implements SensorEventListener {

    private Button pauseButton;

    private TextView timerTV;
    private CountDownTimerWithPause timer;

    private Sensor accelerometer;
    private SensorManager sensorManager;

    private ArrayList<String> endgameWords = new ArrayList<>();
    private ArrayList<Boolean> endgameBooleans = new ArrayList<>();

    private long timerLength = 120000;

    enum State {
        Ok, Pass, Idle
    }

    private GameFragment.State laststate = GameFragment.State.Idle;
    private boolean gameStarted;

    private static final String ARG_PARAM1 = "themeID";
    private long themeID;

    private ThemeDAO themeDAO;


    private TextView debugTV;
    int c;
    private List<Word> wordList;

    private MediaPlayer rightsound;
    private MediaPlayer wrongsound;

    public GameFragment() {
    }


    public static GameFragment newInstance(long param1) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            themeID = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);


        timerTV = view.findViewById(R.id.gameTimer);

        timerTV.setVisibility(View.INVISIBLE);


        themeDAO = App.getInstance().getThemeDatabase().themeDAO();
        debugTV = view.findViewById(R.id.debugGAMETV);
        wordList = themeDAO.getWordsbyTheme(themeID);
        Collections.shuffle(wordList);
        gameStarted = false;

        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        rightsound = MediaPlayer.create(getContext(), R.raw.rightans);
        wrongsound = MediaPlayer.create(getContext(), R.raw.wrongans);
        // Для акселемометра (работает) :
        // Нужна третья координата (event.values[2])
        // Правильный ответ: отрицательные значения
        // Неправильный ответ : положительные значения
        if (!gameStarted) {
            debugTV.setText("Переверните телефон - начнем игру!");
            int c = 0;
        }

        timer = new CountDownTimerWithPause(timerLength, 1000, false) {
            long mills;

            @Override
            public void onTick(long millisUntilFinished) {
                mills = millisUntilFinished;
                timerTV.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                FragmentActivity activity = getActivity();
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).showEndgameResults(endgameWords,endgameBooleans);
                    ((MainActivity) activity).getSupportFragmentManager().findFragmentByTag("GameStarted").onPause();
                }
            }

        };
        timer.create();
        pauseButton = view.findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity != null && activity instanceof MainActivity) {
                ((MainActivity) activity).pauseGame();
            }
        });
        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if ((int) event.values[0] == 9 && !gameStarted) {
            gameStarted = true;
            debugTV.setRotation(90);
            debugTV.setTextSize(40);
            timerTV.setVisibility(View.VISIBLE);
            timerTV.setRotation(90);
            timer.resume();

            debugTV.setText(wordList.get(c).wordSelf);
        }

        try {
            if ((int) event.values[2] >= 6 && gameStarted && laststate == GameFragment.State.Idle) {
                updateState(GameFragment.State.Pass);

            } else if ((int) event.values[2] <= -6 && gameStarted && laststate == GameFragment.State.Idle) {
                updateState(GameFragment.State.Ok);

            } else if ((int) event.values[2] == 0 && gameStarted) {
                updateState(GameFragment.State.Idle);
            }


        } catch (IndexOutOfBoundsException e) {
            Collections.shuffle(wordList);
            c = 0;
        }
    }

    public void updateState(GameFragment.State state) {
        if (laststate != GameFragment.State.Idle && state != GameFragment.State.Idle) {
            return;
        }
        switch (state) {
            case Ok:
                debugTV.setText("NICE!");
                rightsound.start();
                endgameWords.add(wordList.get(c).wordSelf);
                endgameBooleans.add(true);
                c++;
                debugTV.setText(wordList.get(c).wordSelf);
                //thread.start();
                break;
            case Pass:
                debugTV.setText("PASS!");
                wrongsound.start();
                endgameWords.add(wordList.get(c).wordSelf);
                endgameBooleans.add(false);
                c++;
                debugTV.setText(wordList.get(c).wordSelf);
                break;
        }
        laststate = state;

        Log.d("STATE", "Current state: " + state.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        if (gameStarted) {
            timer.resume();
        }
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    public void onPause() {
        timer.pause();
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}