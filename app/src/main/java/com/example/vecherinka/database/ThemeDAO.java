package com.example.vecherinka.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ThemeDAO {

    /*
    @Query("SELECT * FROM Theme WHERE stock == 0")
    public List<Theme> getNonStock();

     */


    @Transaction
    @Query("SELECT * FROM Theme")
    public List<ThemeWords> getThemeWords();


    @Query("SELECT * FROM Word WHERE wordThemeId =:wordThemeId")
    public List<Word> getWordsbyTheme(long wordThemeId);

    @Query("SELECT * FROM Word")
    public List<Word> getAllWords();

    @Insert
    public long insertTheme(Theme theme);

    @Insert
    public void insertWord(Word word);

    @Delete
    public void deleteTheme(Theme theme);

    @Delete
    public void deleteWords(List<Word> words);




    // long themeid = dao.insertheme(theme);
    // for i in range (list length) {
    // word = new Word(слово, themeid)
    // list.add(word)
    // insertWords(list)



}
