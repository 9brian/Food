package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
    Button mBrowseButton;
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
        mBrowseButton = mActivityLandingPageBinding.landingButton;
        mSearchButton = mActivityLandingPageBinding.searchButton;
        mHistoryButton = mActivityLandingPageBinding.historyButton;
        mCancelButton = mActivityLandingPageBinding.cancelButton;
        mAdminButton = mActivityLandingPageBinding.adminButton;
        mProfile = mActivityLandingPageBinding.profileTextview;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();


//      https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//      Manual intent bc changing the function would break the activity
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");
        String name = getExtra();
        mGreeting.setText("Welcome to FoodFlash, " + name);

        User mExistingUser = userFinder(name);

        // Differentiate between admin and non-admin entities
        if(mExistingUser.isAdmin()){
            mAdminButton.setVisibility(View.VISIBLE);
        }else{
            mAdminButton.setVisibility(View.INVISIBLE);
        }

        // Browse the menu
        mBrowseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = BrowseMenuActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Search the menu
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//                Manual intent with extras bc changing the function would break the activity
//                Intent i = getIntent();
//                String name = i.getStringExtra("name");

//                Intent intent = new Intent(getApplicationContext(), SearchItemActivity.class);
//                intent.putExtra("name", name);

                String name = getExtra();

                Intent intent = SearchItemActivity.getIntent(getApplicationContext(), name);
                startActivity(intent);
            }
        });

        // Check cart history
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//                Manual intent bc changing the function would break the activity
//                Intent i = getIntent();
//                String name = i.getStringExtra("name");
                String name = getExtra();

//                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
//                intent.putExtra("name", name);
                Intent intent = HistoryActivity.getIntent(getApplicationContext(), name);
                startActivity(intent);
            }
        });

        // Cancel previous orders
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getExtra();


//                Intent intent = new Intent(getApplicationContext(), CancelActivity.class);
//                intent.putExtra("name", name);
                Intent intent = CancelActivity.getIntent(getApplicationContext(), name);
                startActivity(intent);
            }
        });

        // Admin Button to admin page
        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Profile "button"
        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//                Manual intent bc changing the function would break the activity
//                Intent intent = getIntent();
//                String name = intent.getStringExtra("name");
                String name = getExtra();

//                Intent intenter = new Intent(getApplicationContext(), ProfilePage.class);
//                intenter.putExtra("name", name);
                Intent intent = ProfilePage.getIntent(getApplicationContext(), name);
                startActivity(intent);
            }
        });
    }

    private User userFinder(String userName){
        mFoundUser = mUserDAO.getUserByName(userName);
        return mFoundUser;
    }

    public static Intent getIntent(Context context, String username){
        Intent intent = new Intent(context, LandingPageActivity.class);
        intent.putExtra("name", username);
        return intent;
    }

    public String getExtra(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        return name;
    }
}