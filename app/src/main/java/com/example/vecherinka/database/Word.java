package com.example.vecherinka.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true) public long wordId;
    public long wordThemeId;
    public @NonNull String wordSelf;
    
    public Word(long wordThemeId, String wordSelf){
        this.wordThemeId = wordThemeId;
        this.wordSelf = wordSelf;
    }

}
