package com.axiasoft.mycertificationworkout.databases;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.axiasoft.mycertificationworkout.daos.ItemDao;
import com.axiasoft.mycertificationworkout.models.Item;

import java.util.List;

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> allItems;

    public ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.wordDao();
        allItems = mItemDao.queryGetAllItems();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }


    public void insert (Item word) {
        new insertAsyncTask(mItemDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao mAsyncTaskDao;
        private long result = 0l;

        insertAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            result = mAsyncTaskDao.insert(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("XXX", "La insercion fue: " + result);

        }
    }

    public void update(Item item){
        new updateItemAsyncTask(mItemDao).execute(item);
    }

    private static class updateItemAsyncTask extends AsyncTask<Item, Void,Void>{

        private ItemDao mAsyncTaskDao;

        updateItemAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... items) {
            mAsyncTaskDao.updateItem(items[0]);
            return null;
        }
    }

    public void delete(Item item){
        new deleteItemAsyncTask(mItemDao).execute(item);
    }

    private static class deleteItemAsyncTask extends AsyncTask<Item, Void, Void>{

        private ItemDao mAsyncTaskDao;

        deleteItemAsyncTask(ItemDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Item... items) {
            mAsyncTaskDao.deleteItem(items[0]);
            return null;
        }
    }

}