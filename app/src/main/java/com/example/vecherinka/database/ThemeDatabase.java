package com.example.vecherinka.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Theme.class, Word.class},version = 1)
public abstract class ThemeDatabase extends RoomDatabase {

    public abstract ThemeDAO themeDAO();

}
