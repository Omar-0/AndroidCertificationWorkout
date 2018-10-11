package com.axiasoft.mycertificationworkout.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.axiasoft.mycertificationworkout.models.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item word);

    @Query("DELETE FROM item_table")
    void deleteAll();

    /*We will sort the list later*/
    @Query("SELECT * from item_table")
    LiveData<List<Item>> getAllWords();

    //@Query("SELECT * from item_table WHERE sorted = false")
    //LiveData<List<Item>> getSortedItems();

}
