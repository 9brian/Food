package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.DiscountDAO;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityDiscountBinding;

public class DiscountActivity extends AppCompatActivity {
    TextView discountTitlePage;
    EditText discountSearch;
    Button searchDiscountButton;
    TextView resultSearch;
    DiscountDAO mDiscountDAO;
    UserDAO mUserDAO;
    Discount mSpecificDiscount;
    ActivityDiscountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        binding = ActivityDiscountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        discountTitlePage = binding.discountTitleTextview;
        discountSearch = binding.discountCodeEdittext;
        searchDiscountButton = binding.deleteItemFoundButton;
        resultSearch = binding.displayDiscountSearchConfirmation;

        mDiscountDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().DiscountDAO();
        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();




        searchDiscountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = discountSearch.getText().toString();
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");

                User foundUser = mUserDAO.getUserByName(name);

                if(searchForDiscount(search)){
                    mSpecificDiscount = mDiscountDAO.getDiscountByCode(search);
                    resultSearch.setText(mSpecificDiscount.toString());

                    int discountId = mSpecificDiscount.getDiscountId();
                    foundUser.setDiscountId(discountId);

                    mUserDAO.update(foundUser);
                    Toast.makeText(DiscountActivity.this, "Your discount has been updated", Toast.LENGTH_SHORT).show();

                } else {
                    resultSearch.setText("No discounts found!");
                }
            }
        });

        // Manual insertion of discounts
        searchDiscountButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String search = discountSearch.getText().toString();
                Discount newDiscount = new Discount(search, 0.5);
                mDiscountDAO.insert(newDiscount);
                return false;
            }
        });


    }
    private boolean searchForDiscount(String discountCode){
        return mDiscountDAO.getDiscountByCode(discountCode) != null;
    }
    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, DiscountActivity.class);
        intent.putExtra("name", username);
        return intent;
    }
}