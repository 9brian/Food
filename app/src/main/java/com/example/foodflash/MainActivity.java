package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.DiscountDAO;
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    // Adding a comment

    TextView mTitle;
    Button mLoginButton;
    Button mSignUpButton;


    UserDAO mUserDAO;
    User mSpecificUser;

    ItemDAO mItemDAO;
    Item mSpecificItem;

    DiscountDAO mDiscountDAO;
    Discount mSpecificDiscount;

    ActivityMainBinding mActivityMainBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mActivityMainBinding.getRoot();
        setContentView(view);



        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();
        mDiscountDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().DiscountDAO();
        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();


        // Pre-existing Users
        if (!userExists("testuser1")) {
            User preUser = new User("testuser1", "testuser1", 1, false);
            mUserDAO.insert(preUser);
        }
        if (!userExists("admin2")) {
            User preAdmin = new User("admin2", "admin2", 1, true);
            mUserDAO.insert(preAdmin);
        }

        // Pre-existing discounts
        if (!discountExists("default")) {
            Discount defaultDiscount = new Discount("default", 0.0);
            mDiscountDAO.insert(defaultDiscount);
        }
        if (!discountExists("inception")) {
            Discount inceptionDiscount = new Discount("inception", 0.05);
            mDiscountDAO.insert(inceptionDiscount);
        }

        // Pre-existing recipes
        if(!itemExists("Pizza")){
            Item pizzaItem = new Item("Pizza",
                    "This pizza was made from the best tomatoes found in the #338 tomato field.", 12.00);
            mItemDAO.insert(pizzaItem);
        }
        if(!itemExists("Chicken alfredo")){
            Item chicken_alfredo = new Item("Chicken alfredo",
                    "Chicken Alfredo, the best chicken alfredo you can find anywhere in the world. " +
                            "Topped with the best sauce anyone can find. ", 24.00);
            mItemDAO.insert(chicken_alfredo);
        }
        if(!itemExists("Ramen")){
            Item ramen = new Item("Ramen",
                    "Ramen.", 1000.00);
            mItemDAO.insert(ramen);
        }
        if(!itemExists("tacos")){
            Item tacos = new Item("tacos",
                    "3 chicken tacos, topped with salsa, cilantro, and onion.", 10.00);
            mItemDAO.insert(tacos);
        }

        // Type converter for decision



//        Uncomment if delete account breaks again
//        mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", Context.MODE_PRIVATE);
//        mSharedPreferences.edit().clear().apply();

        mTitle = mActivityMainBinding.titleId;
        mLoginButton = mActivityMainBinding.loginButton;
        mSignUpButton = mActivityMainBinding.signupButton;

        mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", Context.MODE_PRIVATE);

        if(mSharedPreferences.contains("loginName")){
//            https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//            Sending extras bc if I changed the function things would break

            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
            String username = mSharedPreferences.getString("loginName", "");

            intent.putExtra("name", username);
            startActivity(intent);
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignUpActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    private boolean userExists(String userName){
        mSpecificUser = mUserDAO.getUserByName(userName);
        return mSpecificUser != null;
    }

    private boolean discountExists(String discount){
        mSpecificDiscount = mDiscountDAO.getDiscountByCode(discount);
        return mSpecificDiscount != null;
    }

    private boolean itemExists(String itemName){
//        mSpecificDiscount = mDiscountDAO.getDiscountByCode(discount);
        mSpecificItem = mItemDAO.getItemByName(itemName);
        return mSpecificItem != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}