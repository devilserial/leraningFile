package com.dev.remeberwords;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Word.class},version = 1,exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    private static WordDatabase INSTANCE;
    public static synchronized WordDatabase getINSTANCE(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,WordDatabase.class,"db_word")
            .build();
        }
        return  INSTANCE;
    }
    public abstract WordDao getWordDao();
}
