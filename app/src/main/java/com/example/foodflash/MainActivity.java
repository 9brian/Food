package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.DiscountDAO;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    // Adding a comment

    int count = 0;
    TextView mTitle;
    Button mLoginButton;
    Button mSignUpButton;
    UserDAO mUserDAO;
    User mSpecificUser;
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

//        Log.d("true", discountExistsAlready(defaultDiscount.getDiscountCode()) + "");

        if (!findUser("testuser1")) {
            User preUser = new User("testuser1", "testuser1", 1, false);
            mUserDAO.insert(preUser);
        }
        if (!findUser("admin2")) {
            User preAdmin = new User("admin2", "admin2", 1, true);
            mUserDAO.insert(preAdmin);
        }

        if (!discountExistsAlready("default")) {
            Discount defaultDiscount = new Discount("default", 0.0);
            mDiscountDAO.insert(defaultDiscount);
        }

        Log.d("tag", count + " ");
//        Uncomment if delete account breaks again
//        mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", Context.MODE_PRIVATE);
//        mSharedPreferences.edit().clear().apply();

        mTitle = mActivityMainBinding.titleId;
        mLoginButton = mActivityMainBinding.loginButton;
        mSignUpButton = mActivityMainBinding.signupButton;

        mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", Context.MODE_PRIVATE);

        if(mSharedPreferences.contains("loginName")){
//                 https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//                  Sending extras bc if I changed the function things would break
            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
            String username = mSharedPreferences.getString("loginName", "");
            intent.putExtra("name", username);
            startActivity(intent);
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 1;
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 1;
                Intent intent = SignUpActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

    }
//    private void refreshDisplay(){
//        mGymLogList = mGymLogDAO.getGymLogs();
//        if (! mGymLogList.isEmpty()){
//            StringBuilder sb = new StrinBuilder();
//            for(GymLog log : mGymLogList){
//                sb.append(log.toString());
//            }
//        }
//        else{
//            mMainDisplay.setText("");
//        }
//    }


    private boolean findUser(String userName){
        mSpecificUser = mUserDAO.getUserByName(userName);
        // Returns true of user does not exist
        return mSpecificUser != null;
    }

    private boolean discountExistsAlready(String discount){
        mSpecificDiscount = mDiscountDAO.getDiscountByCode(discount);

        return mSpecificDiscount != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}