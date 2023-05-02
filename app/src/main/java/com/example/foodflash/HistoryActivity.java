package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.CartDAO;
import com.example.foodflash.DB.DiscountDAO;
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityHistoryBinding;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    TextView mHistory;
    TextView mTotal;

    UserDAO mUserDAO;
    ItemDAO mItemDAO;
    CartDAO mCartDAO;
    DiscountDAO mDiscountDAO;

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
        mTotal = binding.totalOrderTextview;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();
        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();
        mCartDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().CartDAO();
        mDiscountDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().DiscountDAO();


//      https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//      Manual intent bc changing the function would break the activity
        Intent i = getIntent();
        String name = i.getStringExtra("name");

        getUser = mUserDAO.getUserByName(name);
        int userId = getUser.getUserId();
        int discountId = getUser.getDiscountId();

        refreshDisplay(userId, discountId);
    }

    private void refreshDisplay(int userId, int discountIdentifier){
        userPurchases = mCartDAO.getItemsByUserId(userId);
        double subtotal = 0.00;

        Discount discount = mDiscountDAO.getDiscountById(discountIdentifier);
        double discountPercentage = discount.getDiscountPercentage();

        if (! userPurchases.isEmpty()){
            StringBuilder sb = new StringBuilder();
            StringBuilder stringBuilt = new StringBuilder();
            for(Cart purchases : userPurchases){
                int itemId = purchases.getMenuId();

                userItems = mItemDAO.getItemsByItemId(itemId);
                sb.append(userItems.getItemName() + "\n$" +
                        userItems.getItemPrice() + "\n" +
                        "=-=-=-=-=-=-=-=-=-=-=" + "\n");
                subtotal += userItems.getItemPrice();

            }

            double discountOff = discountPercentage * subtotal;
            stringBuilt.append("=-=-=-=-=-=-=-=-=--=-=-=-=-=").append("\n");
            stringBuilt.append("Order Summary").append("\n");
            stringBuilt.append("Subtotal: $").append(subtotal).append("\n");
            stringBuilt.append("Discounts: -$").append(discountOff).append("\n");

            double total = 0.00;
            total = subtotal - discountOff;
            stringBuilt.append("Order total: $").append(total).append("\n");

            mTotal.setText(stringBuilt);
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