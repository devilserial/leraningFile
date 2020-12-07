package com.dev.remeberwords;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void insertWords(Word... words);
    @Delete
    void deleteWords(Word... words);
    @Update
    void updateWords(Word... words);
    @Query("delete from word")
    void deleteAllWords();
    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAllWords();
    @Query("select * from word where english like :patten order by id desc")
    LiveData<List<Word>> searchWords(String patten);
}
