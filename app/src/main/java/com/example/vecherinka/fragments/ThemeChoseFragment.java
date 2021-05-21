package com.example.vecherinka.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vecherinka.App;
import com.example.vecherinka.MainActivity;
import com.example.vecherinka.R;
import com.example.vecherinka.database.ThemeDAO;
import com.example.vecherinka.database.ThemeWords;

import java.util.List;


public class ThemeChoseFragment extends Fragment {

    LinearLayout themesLL;
    ThemeDAO themeDAO;

    public ThemeChoseFragment() {

    }



    public static ThemeChoseFragment newInstance(String param1, String param2) {
        ThemeChoseFragment fragment = new ThemeChoseFragment();
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
        View view = inflater.inflate(R.layout.fragment_theme_chose, container, false);
        themesLL = view.findViewById(R.id.gameStartLL);
        themesLL.setPadding(10,10,10,10);
        themeDAO = App.getInstance().getThemeDatabase().themeDAO();
        List<ThemeWords> themesList = themeDAO.getThemeWords();
        // непонятно, насколько полезная вещь
        if (themesList.size() == 0){
            TextView noThemetv = new TextView(getContext());
            noThemetv.setText("Нет тем :(");
            noThemetv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            themesLL.addView(noThemetv);
        }

        for (int i = 0; i < themesList.size(); i++) {
            long curThemeId = themesList.get(i).theme.themeId;
            Button button = new Button(getContext());
            button.setText(themesList.get(i).theme.name);
            button.setBackgroundColor(Color.parseColor("#FF018786"));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Theme Chose", button.getText().toString());
                    FragmentActivity activity = getActivity();
                    if (activity instanceof MainActivity){
                        ((MainActivity) activity).startGame(curThemeId);
                    }
                }
            });

            themesLL.addView(button);

        }
        return view;
    }
    
}