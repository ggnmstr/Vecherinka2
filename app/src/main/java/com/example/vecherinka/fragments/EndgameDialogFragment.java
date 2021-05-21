package com.example.vecherinka.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vecherinka.MainActivity;
import com.example.vecherinka.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class EndgameDialogFragment extends DialogFragment {

    private LinearLayout wordsLL;
    private Button continueButton;


    private static final String ARG_PARAM1 = "endgameWords";
    private static final String ARG_PARAM2 = "endgameBooleans";

    private ArrayList<String> endgameWords;
    private ArrayList<Boolean> endgameBooleans;

    public EndgameDialogFragment() {
    }

    public static EndgameDialogFragment newInstance(ArrayList<String> param1, ArrayList<Boolean> param2) {
        EndgameDialogFragment fragment = new EndgameDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            endgameWords = (ArrayList<String>) getArguments().getSerializable(ARG_PARAM1);
            endgameBooleans = (ArrayList<Boolean>) getArguments().getSerializable(ARG_PARAM2);
            setCancelable(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_endgame_dialog, container, false);
        wordsLL = view.findViewById(R.id.endgameWordsLL);
        continueButton = view.findViewById(R.id.endgameContinue);
        for (int i = 0; i < endgameWords.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(endgameWords.get(i));
            textView.setTextSize(15);
            textView.setGravity(Gravity.CENTER);
            if (endgameBooleans.get(i)) {
                textView.setTextColor(Color.GREEN);
            } else {
                textView.setTextColor(Color.RED);
            }
            wordsLL.addView(textView);
        }
        continueButton.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).getSupportFragmentManager().popBackStack();

            }
            dismiss();
            // TODO: 02.05.2021 Add behaviour (similar to pause menu) 
        });
        return view;

    }
}