package com.axiasoft.mycertificationworkout.databases;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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

        insertAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}