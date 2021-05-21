package com.example.vecherinka.database;


import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.vecherinka.database.Theme;
import com.example.vecherinka.database.Word;

import java.util.List;

public class ThemeWords {

    @Embedded public Theme theme;
    @Relation(
            parentColumn = "themeId",
            entityColumn = "wordThemeId"
    )
    public List<Word> words;
    
}
