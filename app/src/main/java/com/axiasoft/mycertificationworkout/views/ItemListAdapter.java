package com.axiasoft.mycertificationworkout.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.axiasoft.mycertificationworkout.R;
import com.axiasoft.mycertificationworkout.models.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private ItemViewModel itemViewModel;

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
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        if (displayItems != null) {
            final Item current = displayItems.get(position);
            holder.checkBox.setOnCheckedChangeListener(null);

            holder.wordItemView.setText(current.getValue());
            holder.checkBox.setChecked(current.isSorted());

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Item current = displayItems.get(holder.getAdapterPosition());
                    current.setSorted(b);
                    itemViewModel.update(current);
                }
            });

        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<Item> items){
        //order the items
        displayItems = orderAndDisplayItems(items);
        notifyDataSetChanged();
    }


    public ArrayList<Item> orderAndDisplayItems(List<Item> items){

        ArrayList<Item> unsorted = new ArrayList<>();
        ArrayList<Item> sorted = new ArrayList<>();
        ArrayList<Item> display = new ArrayList<>();

        for (Item i: items) {
            if(i.isSorted()){
                sorted.add(i);
            } else {
                unsorted.add(i);
            }
        }
        Collections.sort(sorted);
        display.addAll(unsorted);
        display.addAll(sorted);
        return display;
    }

    public void setItemViewModel(ItemViewModel itemViewModel){
        this.itemViewModel = itemViewModel;
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (displayItems != null)
            return displayItems.size();
        else return 0;
    }

    public boolean hasItem(Item item){
        return displayItems.contains(item);
    }
}
