package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.CartDAO;
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivitySearchItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchItemActivity extends AppCompatActivity {

    //    https://www.youtube.com/watch?v=JB3ETK5mh3c
    //    Used this to find a way to have autocomplete based on db
    AutoCompleteTextView searchBar;
    Button fetchItemButton;
    TextView itemDesc;
    Button purchaseItemButton;
    List<Item> menuItems;

    ActivitySearchItemBinding binding;
    ItemDAO mItemDAO;
    Item mItemFinder;
    Item heldItem;

    Item mNewItem;


    CartDAO mCartDAO;
    UserDAO mUserDAO;
    User mFoundUser;
    User mNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        binding = ActivitySearchItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        searchBar = binding.searchAutocomplete;
        fetchItemButton = binding.searchItemButton;
        itemDesc = binding.searchedItemTextview;
        purchaseItemButton = binding.purchaseItemButton;


        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();
        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();
        mCartDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().CartDAO();

        menuItems = mItemDAO.getMenuItems();

        //    https://www.youtube.com/watch?v=JB3ETK5mh3c
        //    Used this to find a way to have autocomplete based on db
        List<String> items = new ArrayList<>();
        for(Item item : menuItems){
            String itemName = item.getItemName();
            items.add(itemName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        searchBar.setAdapter(adapter);

        // Search for the item
        fetchItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchBar.getText().toString();
                if (itemExists(search)){
                    heldItem = mItemDAO.getItemByName(search);
                    itemDesc.setText(heldItem.toString() + "\n Would you like to purchase?");
                }
                else{
                    itemDesc.setText("Item does not exist.");
                }

            }
        });

        // Purchase button
        purchaseItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = searchBar.getText().toString();
                if (itemExists(search)){
                    heldItem = mItemDAO.getItemByName(search);

//                  https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//                  Manual intent bc changing the function would break the activity
                    Intent i = getIntent();
                    String name = i.getStringExtra("name");

                    if(findUser(name)){
                        mNewUser = mUserDAO.getUserByName(name);
                        int mFoundId = mNewUser.getUserId();

                        int mItemId = heldItem.getItemId();

                        Cart newCart = new Cart(mItemId, mFoundId);
                        mCartDAO.insert(newCart);
                        Toast.makeText(SearchItemActivity.this, "Purchase was successful", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(SearchItemActivity.this, "This shouldn't pop up", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(SearchItemActivity.this, "Item does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean itemExists(String itemName){
        mItemFinder = mItemDAO.getItemByName(itemName);
        return mItemFinder != null;
    }

    private boolean findUser(String userName){
        mFoundUser = mUserDAO.getUserByName(userName);
        return mFoundUser != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SearchItemActivity.class);
        return intent;
    }
}