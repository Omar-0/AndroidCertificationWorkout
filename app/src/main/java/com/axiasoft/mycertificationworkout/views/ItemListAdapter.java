package com.axiasoft.mycertificationworkout.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.axiasoft.mycertificationworkout.R;
import com.axiasoft.mycertificationworkout.models.Item;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private final CheckBox checkBox;

        private ItemViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.tv_item_name);
            checkBox =  itemView.findViewById(R.id.cb_item_status);
        }
    }

    private final LayoutInflater mInflater;

    private List<Item> displayItems; // Cached copy of words

    ItemListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.check_list_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (displayItems != null) {
            Item current = displayItems.get(position);
            holder.wordItemView.setText(current.getValue());
            holder.checkBox.setChecked(current.isSorted());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<Item> items){
        displayItems = items;
        notifyDataSetChanged();
    }

    //TODO only delete and add are relevant for the livedata

    public void setDisplayItems(){

        //TODO sort the GUI list

    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (displayItems != null)
            return displayItems.size();
        else return 0;
    }
}
