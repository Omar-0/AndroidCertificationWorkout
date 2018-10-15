package com.axiasoft.mycertificationworkout.views.words;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
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

    CoordinatorLayout coordinatorLayout;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawer();

        coordinatorLayout = findViewById(R.id.coordinator);

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
        public void showSnackbar(final Item itemToRestore) {
            Log.d("XXX", "Here will show the snackbar!!");
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, getString(R.string.snackbar_message_delete), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.snackbar_message_undo), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            itemViewModel.insert(itemToRestore);
                        }
                    });

            snackbar.show();
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

    private void initDrawer(){
        mDrawerLayout = findViewById(R.id.drawer_layout);

        //Set the toolbar as the action bar

        ActionBar actionbar = getSupportActionBar();
        //Add the nav drawer button
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Handle navigation click events:
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        //Listen for open/close events and other state changes
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
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

        switch (item.getItemId()) {
            //Open the drawer when the button is tapped
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
