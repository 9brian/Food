package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.CartDAO;
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityHistoryBinding;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    TextView mHistory;

    UserDAO mUserDAO;
    ItemDAO mItemDAO;
    CartDAO mCartDAO;

    User getUser;

    List<Cart> userPurchases;
    Item userItems;

    ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mHistory = binding.historyOrderTextview;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();
        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();
        mCartDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().CartDAO();


//      https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//      Manual intent bc changing the function would break the activity
        Intent i = getIntent();
        String name = i.getStringExtra("name");

        getUser = mUserDAO.getUserByName(name);
        int userId = getUser.getUserId();

        refreshDisplay(userId);
    }

    private void refreshDisplay(int userId){
        userPurchases = mCartDAO.getItemsByUserId(userId);
        if (! userPurchases.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(Cart purchases : userPurchases){
//                sb.append(purchases.toString());
                int itemId = purchases.getMenuId();


                userItems = mItemDAO.getItemsByUserId(itemId);
                // find the Item
                sb.append(userItems.getItemName() + "\n$" +
                        userItems.getItemPrice() + "\n" +
                        "=-=-=-=-=-=-=-=-=-=-=" + "\n");

            }
            mHistory.setText(sb.toString());
        }
        else{
            mHistory.setText("No purchases yet! Time to scrounge some scrilla");
        }
    }


    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, HistoryActivity.class);
        return intent;
    }
}