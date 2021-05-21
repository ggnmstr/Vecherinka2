package com.example.vecherinka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.vecherinka.database.Theme;
import com.example.vecherinka.database.ThemeDAO;
import com.example.vecherinka.fragments.AddThemeDialogFragment;
import com.example.vecherinka.fragments.EditThemesFragment;
import com.example.vecherinka.fragments.EndgameDialogFragment;
import com.example.vecherinka.fragments.GameFragment;
import com.example.vecherinka.fragments.PauseDialogFragment;
import com.example.vecherinka.fragments.StartScreenFramgment;
import com.example.vecherinka.fragments.ThemeChoseFragment;
import com.example.vecherinka.fragments.ThemeEditor;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    private ThemeDAO themeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container,new StartScreenFramgment())
                .commit();
        themeDAO = App.getInstance().getThemeDatabase().themeDAO();
        SharedPreferences settings = getSharedPreferences("Settings",0);
        if (settings.getBoolean("firsttime", true)) {
            Log.d("MAINACTIVITY","firtst time!");
            // TODO: 02.05.2021 first-time launch, add something though.
            settings.edit().putBoolean("firsttime", false).commit();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void startMenu(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,new ThemeChoseFragment())
                .addToBackStack("toMenu")
                .commit();
    }


    public void startEditThemes(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,new EditThemesFragment())
                .addToBackStack("toEditThemes")
                .commit();
        Log.d("MAINACTIVITY",String.valueOf(themeDAO.getAllWords().size()));
    }

    public void startDialogAddTheme() {
        AddThemeDialogFragment fragment = new AddThemeDialogFragment();
        fragment.show(getSupportFragmentManager(),"AddTheme");
    }

    public void goContinueTheme(String name) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, ThemeEditor.newInstance(name))
                .addToBackStack("toThemeEditor")
                .commit();
    }

    public void startGame(long themeId){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,GameFragment.newInstance(themeId),"GameStarted")
                .addToBackStack("startGame")
                .commit();
    }

    public void pauseGame(){
        PauseDialogFragment fragment = new PauseDialogFragment();
        getSupportFragmentManager().findFragmentByTag("GameStarted").onPause();
        fragment.show(getSupportFragmentManager(),"PauseGame");
    }

    public void resumeGame(){
        getSupportFragmentManager().findFragmentByTag("GameStarted").onResume();
    }

    public void showEndgameResults(ArrayList<String> endgameWords, ArrayList<Boolean> endgameBooleans){
        EndgameDialogFragment fragment = EndgameDialogFragment.newInstance(endgameWords,endgameBooleans);
        fragment.show(getSupportFragmentManager(),"EndgameResults");

    }
}