package com.axiasoft.mycertificationworkout.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Comparator;


/*Item is the object show on each row it will be the table*/

@Entity(tableName = "item_table")
public class Item implements Comparator<Item>, Comparable<Item> {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "value")
    private String value;

    @ColumnInfo(name = "sorted")
    private boolean sorted;

    //Methods that compare if item is equal or differente to another just taking in account the String value,
    //by making value to primary key we can skip these methods
    @Override
    public int compare(Item s, Item t1) {
        return s.value.compareTo(t1.value);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Item){
            return this.value.equals((((Item) o).value ));
        } return false;
    }

    @Override
    public int compareTo(@NonNull Item item) {
        return this.value.compareTo(item.value);
    }

    //region Getters & Setters
    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    //endregion

}
