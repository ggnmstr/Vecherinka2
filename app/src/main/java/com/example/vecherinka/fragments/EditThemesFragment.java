package com.example.vecherinka.fragments;

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
import android.widget.Toast;

import com.example.vecherinka.App;
import com.example.vecherinka.MainActivity;
import com.example.vecherinka.R;
import com.example.vecherinka.database.Theme;
import com.example.vecherinka.database.ThemeDAO;
import com.example.vecherinka.database.ThemeWords;

import java.util.List;


public class EditThemesFragment extends Fragment {
    Button addThemeButton;
    LinearLayout themeLL;
    ThemeDAO themeDAO;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public EditThemesFragment() {
        // Required empty public constructor
    }


    public static EditThemesFragment newInstance(String param1, String param2) {
        EditThemesFragment fragment = new EditThemesFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_themes, container, false);
        addThemeButton = view.findViewById(R.id.addThemeButton);
        themeDAO = App.getInstance().getThemeDatabase().themeDAO();
        themeLL = view.findViewById(R.id.showThemeLL);
        Toast.makeText(getContext(),"Удерживайте для удаления",Toast.LENGTH_SHORT).show();


        List<ThemeWords> themesList = themeDAO.getThemeWords();
        for (int i = 0; i < themesList.size(); i++) {
            TextView test = new TextView(getContext());
            test.setTextSize(30);
            test.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            themeLL.addView(test);
            test.setText(themesList.get(i).theme.name);
            int finalI = i;
            test.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("LOGTAG01", test.getText().toString());
                    // Сначала удаление слов по теме, потом тему.
                    themeDAO.deleteWords(themeDAO.getWordsbyTheme(themesList.get(finalI).theme.themeId));
                    themeDAO.deleteTheme(themesList.get(finalI).theme);
                    themeLL.removeView(test);
                    return true;
                }
            });

        }
        addThemeButton.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity != null && activity instanceof MainActivity) {
                ((MainActivity) activity).startDialogAddTheme();
            }
        });
        return view;
    }
}