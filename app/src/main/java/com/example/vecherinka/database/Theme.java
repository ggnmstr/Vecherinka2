package com.example.vecherinka.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

@Entity
public class Theme implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long themeId;

    public @NonNull int stock;

    public @NonNull String name;

    public Theme(@NonNull int stock,@NonNull String name){
        this.stock = stock;
        this.name = name;
    }


}

