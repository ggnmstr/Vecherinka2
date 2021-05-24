package com.example.vecherinka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.vecherinka.database.Theme;
import com.example.vecherinka.database.ThemeDAO;
import com.example.vecherinka.database.Word;
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
            firstTimeLaunch();
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

    public void firstTimeLaunch(){
        Theme theme1 = new Theme(1,"Animals");
        long themeid = themeDAO.insertTheme(theme1);
        Word word = new Word(themeid, "Lion");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Baboon");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Anteater");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Anchovies");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Arctic Fox");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Butterfly");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Cockroach");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Crab");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Duck");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Frog");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Gorilla");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Horse");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Iguana");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Jaguar");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Koala");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Lemur");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Lobster");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Lizard");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Monkey");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Mouse");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Octopus");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Ocelot");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Pig");
        themeDAO.insertWord(word);
        word = new Word(themeid, "Raccoon");
        themeDAO.insertWord(word);




    }
}