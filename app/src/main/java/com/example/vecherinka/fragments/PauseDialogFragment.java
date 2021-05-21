package com.example.vecherinka.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.vecherinka.MainActivity;
import com.example.vecherinka.R;


public class PauseDialogFragment extends DialogFragment {

    Button contGame, stopGame;
    boolean flag = false;
    public PauseDialogFragment() {
    }

    public static PauseDialogFragment newInstance() {
        PauseDialogFragment fragment = new PauseDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pause_dialog, container, false);
        contGame = view.findViewById(R.id.continueGameButton);
        stopGame = view.findViewById(R.id.exitGameButton);
        contGame.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).resumeGame();
                dismiss();
            }
        });
        stopGame.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).getSupportFragmentManager().popBackStack();
            }
            flag = true;
            dismiss();
        });
        return view;
    }

    @Override
    public void onDestroy() {
        if (!flag){
            FragmentActivity activity = getActivity();
            if (activity instanceof MainActivity) {
                ((MainActivity) activity).resumeGame();
            }
        }
        super.onDestroy();
    }
}