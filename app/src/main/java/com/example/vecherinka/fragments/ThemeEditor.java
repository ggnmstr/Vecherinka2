package com.example.vecherinka.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vecherinka.App;
import com.example.vecherinka.MainActivity;
import com.example.vecherinka.R;
import com.example.vecherinka.database.Theme;
import com.example.vecherinka.database.ThemeDAO;
import com.example.vecherinka.database.Word;

import java.util.ArrayList;


public class ThemeEditor extends Fragment {

    EditText words;
    Button okButton;
    TextView themeName;
    ThemeDAO mDao;

    private static final String ARG_PARAM1 = "theme_name";


    private String theme_name;

    public ThemeEditor() {

    }


    public static ThemeEditor newInstance(String param1) {
        ThemeEditor fragment = new ThemeEditor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDao = App.getInstance().getThemeDatabase().themeDAO();
        if (getArguments() != null) {
            theme_name = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_theme_editor, container, false);
        words = view.findViewById(R.id.wordsET);
        themeName = view.findViewById(R.id.themeName);
        okButton = view.findViewById(R.id.sureAddButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = words.getText().toString();
                Theme theme = new Theme(0, themeName.getText().toString());
                // Паттерн для сплита, работает.
                String lines[] = text.split("\\r?\\n");
                long themeid = mDao.insertTheme(theme);
                for (int i = 0; i < lines.length; i++) {
                    Word word = new Word(themeid, lines[i]);
                    mDao.insertWord(word);
                }
                FragmentActivity activity = getActivity();
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).getSupportFragmentManager().popBackStack();
                }


            }
        });
        themeName.setText(theme_name);

        return view;
    }


}