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
import com.example.foodflash.databinding.ActivityCancelBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CancelActivity extends AppCompatActivity {

    //    https://www.youtube.com/watch?v=JB3ETK5mh3c
    //    Used this to find a way to have autocomplete based on db
    AutoCompleteTextView cancelBar;
    Button fetchItem;
    TextView confirmItem;
    Button cancelItemButton;

    ActivityCancelBinding binding;

    CartDAO mCartDAO;
    ItemDAO mItemDAO;
    UserDAO mUserDAO;
    List<Cart> userCart;

    User mFoundUser;
    User mUser;
    User mNewUser;

    Item heldItem;
    Item mItemFinder;

    Cart mNewCart;
    Cart mCartFinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        binding = ActivityCancelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cancelBar = binding.cancelAutocomplete;
        fetchItem = binding.cancelSearchItemButton;
        confirmItem = binding.searchedItemTextview;
        cancelItemButton = binding.cancelItemButton;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();
        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();
        mCartDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().CartDAO();

//        https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//      Manual intent bc changing the function would break the activity
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");


        if (findUser(name)){
            mUser = mUserDAO.getUserByName(name);
            int userId = mUser.getUserId();

            userCart = mCartDAO.getItemsByUserId(userId);

            List<String> itemName = new ArrayList<>();
            for(Cart cart : userCart){
                int itemId = cart.getMenuId();

                Item item = mItemDAO.getItemsByItemId(itemId);
                itemName.add(item.getItemName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemName);
            cancelBar.setAdapter(adapter);

            Log.d("tagging", userCart.toString());

            fetchItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String search = cancelBar.getText().toString();

                    if (itemExists(search)){
                        heldItem = mItemDAO.getItemByName(search);
                        int menuId = heldItem.getItemId();
                        String itemName = heldItem.getItemName();

                        if(cartExists(userId, menuId)){
                            confirmItem.setText(heldItem.toString() + "\n Would you like to cancel?");
                        } else {
                            confirmItem.setText(name + " does not have " + itemName + " in their cart");
                        }


//                        Log.d("tag", heldItem.toString());


                    }
                    else{
                        confirmItem.setText("Item does not exist.");
                    }
                }
            });

            cancelItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String search = cancelBar.getText().toString();

                    if (itemExists(search)){
                        heldItem = mItemDAO.getItemByName(search);
//                        https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//                        Manual intent bc changing the function would break the activity
                        Intent i = getIntent();
                        String name = i.getStringExtra("name");


                        int menuId = heldItem.getItemId();
                        String item = heldItem.getItemName();
                        if(findUser(name)){

                            mNewUser = mUserDAO.getUserByName(name);
                            int userId = mNewUser.getUserId();

                            if (cartExists(userId, menuId)){
                                mNewCart = mCartDAO.getCartByUserMenu(menuId, userId);

//                            Log.d("tag", mNewCart.toString());
//                            Cart newCart = new Cart(mItemId, mFoundId);
                                mCartDAO.delete(mNewCart);
                                confirmItem.setText("");
//                        Log.d("tagging", newCart.toString());
                                Toast.makeText(CancelActivity.this, "Cancel was successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CancelActivity.this, name + " does not have " + item + " in their cart", Toast.LENGTH_SHORT).show();
                            }


                        } else{
                            Toast.makeText(CancelActivity.this, "This shouldn't pop up", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CancelActivity.this, "This shouldn't pop up", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        } else {
            confirmItem.setText("We shouldn't be here");
        }

    }

    private boolean cartExists(int userId, int menuId){
        mCartFinder = mCartDAO.getCartByUserMenu(menuId, userId);
        // Returns true if user does exist
        return mCartFinder != null;
    }

    private boolean itemExists(String itemName){
        mItemFinder = mItemDAO.getItemByName(itemName);
        // Returns true if user does exist
        return mItemFinder != null;
    }

    private boolean findUser(String userName){
        mFoundUser = mUserDAO.getUserByName(userName);
        return mFoundUser != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, CancelActivity.class);
        return intent;
    }
}