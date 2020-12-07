package com.dev.remeberwords;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository wordRepository;
    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
    }
    public LiveData<List<Word>> getLiveDataWords(){
        return wordRepository.getLiveDataWords();
    }
    public LiveData<List<Word>> getSearchWords(String patten){
        return wordRepository.getSearchWords(patten);
    }

    public void insertWords(Word ... words){
        wordRepository.insertWords(words);
    }
    public void updateWords(Word ... words){
        wordRepository.updateWords(words);
    }
    public void deletWords(Word ... words){
        wordRepository.deleteWords(words);
    }
    public void deleteAllWords(){
        wordRepository.deleteAllWords();
    }
}
