package com.example.vecherinka.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vecherinka.MainActivity;
import com.example.vecherinka.R;


public class AddThemeDialogFragment
        extends DialogFragment
        implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

    EditText themeName;
    Button continueButton;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public AddThemeDialogFragment() {
    }


    public static AddThemeDialogFragment newInstance(String param1, String param2) {
        AddThemeDialogFragment fragment = new AddThemeDialogFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_theme, container, false);
        themeName = view.findViewById(R.id.themeNameET);
        continueButton = view.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = getActivity();
                if (activity != null && activity instanceof MainActivity){
                    String tName = themeName.getText().toString();
                    if (!tName.isEmpty()){
                        ((MainActivity) activity).goContinueTheme(themeName.getText().toString());
                        dismiss();
                    }
                }
            }
        });
        return view;
    }
}