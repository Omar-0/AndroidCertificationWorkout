package com.axiasoft.mycertificationworkout.views;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.axiasoft.mycertificationworkout.databases.ItemRepository;
import com.axiasoft.mycertificationworkout.models.Item;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository mRepository;

    private LiveData<List<Item>> mAllItems;

    public ItemViewModel (Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    LiveData<List<Item>> getAllWords() { return mAllItems; }

    public void insert(Item item) { mRepository.insert(item); }

    public void update(Item item){
        mRepository.update(item);
    }

    public void delete(Item item){
        mRepository.delete(item);
    }
}
