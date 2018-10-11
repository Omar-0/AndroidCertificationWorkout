package com.axiasoft.mycertificationworkout.databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.axiasoft.mycertificationworkout.daos.ItemDao;
import com.axiasoft.mycertificationworkout.models.Item;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemRoomDatabase extends RoomDatabase {

    private static volatile ItemRoomDatabase INSTANCE;

    public abstract ItemDao wordDao();

    static ItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "item_database")
                            .addCallback(initRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    //populate db with default values

    private static RoomDatabase.Callback initRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDefaultDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDefaultDbAsync extends AsyncTask<Void, Void, Void> {

        private final ItemDao mDao;

        PopulateDefaultDbAsync(ItemRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.queryDeleteAll();
            //TODO read from file? from string array?
            Item item = new Item("Hello");
            mDao.insert(item);
            item = new Item("World");
            mDao.insert(item);
            return null;
        }
    }
}