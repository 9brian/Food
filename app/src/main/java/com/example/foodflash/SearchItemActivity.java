package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.databinding.ActivityEditItemBinding;
import com.example.foodflash.databinding.ActivitySearchItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchItemActivity extends AppCompatActivity {

    //    https://www.youtube.com/watch?v=JB3ETK5mh3c
    //    Used this to find a way to have autocomplete based on db
    AutoCompleteTextView searchBar;
    List<Item> menuItems;

    ActivitySearchItemBinding binding;
    ItemDAO mItemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        binding = ActivitySearchItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        searchBar = binding.searchAutocomplete;

        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();

        menuItems = mItemDAO.getMenuItems();

        List<String> items = new ArrayList<>();
        for(Item item : menuItems){
            String itemName = item.getItemName();
            items.add(itemName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        searchBar.setAdapter(adapter);
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SearchItemActivity.class);
        return intent;
    }
}