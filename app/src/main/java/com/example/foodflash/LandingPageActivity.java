package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityLandingPageBinding;
import com.example.foodflash.databinding.ActivityMainBinding;

public class LandingPageActivity extends AppCompatActivity {

    TextView mGreeting;
    Button mLandingButton;
    Button mSearchButton;
    Button mHistoryButton;
    Button mCancelButton;
    Button mAdminButton;
    TextView mProfile;

    UserDAO mUserDAO;
    User mFoundUser;

    ActivityLandingPageBinding mActivityLandingPageBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mActivityLandingPageBinding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        View view = mActivityLandingPageBinding.getRoot();

        setContentView(view);

        mGreeting = mActivityLandingPageBinding.beginLoginPage;
        mLandingButton = mActivityLandingPageBinding.landingButton;
        mSearchButton = mActivityLandingPageBinding.searchButton;
        mHistoryButton = mActivityLandingPageBinding.historyButton;
        mCancelButton = mActivityLandingPageBinding.cancelButton;
        mAdminButton = mActivityLandingPageBinding.adminButton;
        mProfile = mActivityLandingPageBinding.profileTextview;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

//      https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//      Manual intent bc changing the function would break the activity
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mGreeting.setText("Welcome to FoodFlash, " + name);

        User mExistingUser = userFinder(name);

        if(mExistingUser.isAdmin()){
            mAdminButton.setVisibility(View.VISIBLE);
        }else{
            mAdminButton.setVisibility(View.INVISIBLE);
        }

        mLandingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//      https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//      Manual intent bc changing the function would break the activity
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                Log.d("profile", "reroute");

                Intent intenter = new Intent(getApplicationContext(), ProfilePage.class);
                intenter.putExtra("name", name);
                startActivity(intenter);
            }
        });
    }

    private User userFinder(String userName){
        mFoundUser = mUserDAO.getUserByName(userName);
        // Returns true of user does not exist
        return mFoundUser;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, LandingPageActivity.class);
        return intent;
    }
}