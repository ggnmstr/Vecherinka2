package com.example.vecherinka.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vecherinka.MainActivity;
import com.example.vecherinka.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartScreenFramgment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartScreenFramgment extends Fragment implements View.OnClickListener {
    Button startButton;
    Button editThemesButton;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public StartScreenFramgment() {

    }


    public static StartScreenFramgment newInstance(String param1, String param2) {
        StartScreenFramgment fragment = new StartScreenFramgment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_screen,container,false);
        startButton = view.findViewById(R.id.playbutton);
        editThemesButton = view.findViewById(R.id.theme_editor);
        startButton.setOnClickListener(this);
        editThemesButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity){
            switch (v.getId()){
                case R.id.playbutton:
                    ((MainActivity) activity).startMenu();
                    break;
                case R.id.theme_editor:
                    ((MainActivity) activity).startEditThemes();
                    break;

            }

        }
    }
}