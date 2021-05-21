package com.example.vecherinka;


import android.app.Application;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.vecherinka.database.ThemeDatabase;

public class App extends Application {
    private static App instance;

    private ThemeDatabase themeDatabase;

    @Override
    public void onCreate() {
        /*
        SharedPreferences preferences = getSharedPreferences("Settings",0);
        boolean firsttime = preferences.contains("firsttime");
        if (!firsttime){
            preferences.edit().putBoolean("firsttime",true);
        }
         */
        super.onCreate();
        instance = this;
        themeDatabase = Room.databaseBuilder(this, ThemeDatabase.class,"themes")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public ThemeDatabase getThemeDatabase() {
        return themeDatabase;
    }
}
