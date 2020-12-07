package com.dev.remeberwords;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private LiveData<List<Word>>   liveDataWords;
    private WordDao wordDao;

    public WordRepository(Context context){
        WordDatabase wordDatabase = WordDatabase.getINSTANCE(context);
        wordDao =wordDatabase.getWordDao();
        liveDataWords =wordDao.getAllWords();

    }

    public LiveData<List<Word>> getLiveDataWords() {
        return liveDataWords;
    }
    public LiveData<List<Word>> getSearchWords(String patten) {
        return wordDao.searchWords("%"+patten+"%");
    }
    public void insertWords(Word ... words){
        new InsertAsyncTask(wordDao).execute(words);
    }
    public void updateWords(Word ... words){
        new UpdateAsyncTask(wordDao).execute(words);
    }
    public void deleteWords(Word ... words){
        new DeleteAsyncTask(wordDao).execute(words);
    }
    public void deleteAllWords(Word ... words){
        new DeleteAllAsyncTask(wordDao).execute();
    }
    class InsertAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;
        public InsertAsyncTask(WordDao wordDao){
            this.wordDao  = wordDao;
        }
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }
    class UpdateAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;
        public UpdateAsyncTask(WordDao wordDao){
            this.wordDao  = wordDao;
        }
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }
    class DeleteAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;
        public DeleteAsyncTask(WordDao wordDao){
            this.wordDao  = wordDao;
        }
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }
    class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private WordDao wordDao;
        public DeleteAllAsyncTask(WordDao wordDao){
            this.wordDao  = wordDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }

}
