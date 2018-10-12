package com.axiasoft.mycertificationworkout.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.axiasoft.mycertificationworkout.R;
import com.axiasoft.mycertificationworkout.models.Item;

import java.util.List;

public class WordsActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    //Access Room database with data access object (DAO)
    //Observe and respond to changing data using LiveData
    //Use a Repository to handle data operations
    //Read and parse raw resources or asset files

    private ItemViewModel itemViewModel;

    ItemListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        //we need the adapter as member to use it outside oncreate
        adapter = new ItemListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordsActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getAllWords().observe(this, listObserver);
        adapter.setItemViewModel(itemViewModel);
        adapter.setUndoDeleteCallback(undoDeleteCallback);
    }

    Observer<List<Item>> listObserver = new Observer<List<Item>>() {
        @Override
        public void onChanged(@Nullable final List<Item> words) {
            // Update the cached copy of the words in the adapter.
            adapter.setWords(words);
        }
    };

    interface UndoDeleteCallback{
        void showSnackbar(Item itemToRestore);
    }

    UndoDeleteCallback undoDeleteCallback = new UndoDeleteCallback() {
        @Override
        public void showSnackbar(Item itemToRestore) {
            //TODO show the snackbar and implement the action undo button
            Log.d("XXX", "Here will show the snackbar!!");
        }
    };


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Item word = new Item(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            //Just for purpose we evaluate outside. The DB insert returns -1 on duplicate, and we can
            // evaluate that insertion
            if(adapter.hasItem(word)){
                Toast.makeText(
                        getApplicationContext(),
                        R.string.item_already_exists_message,
                        Toast.LENGTH_LONG).show();
            } else {
                itemViewModel.insert(word);
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.item_already_exists_message,
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_words, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
