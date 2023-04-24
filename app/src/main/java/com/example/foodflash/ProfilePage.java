package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodflash.databinding.ActivityProfilePageBinding;

public class ProfilePage extends AppCompatActivity {
    TextView mProfileUsername;
    Button discountButton;
    Button deleteButton;
    Button logOutButton;
    ActivityProfilePageBinding mActivityProfilePageBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        mActivityProfilePageBinding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(mActivityProfilePageBinding.getRoot());

        mProfileUsername = mActivityProfilePageBinding.profilePageTextview;
        discountButton = mActivityProfilePageBinding.discountButton;
        deleteButton = mActivityProfilePageBinding.deleteAccountButton;
        logOutButton = mActivityProfilePageBinding.logoutButton;

        mSharedPreferences = getSharedPreferences("loginName", Context.MODE_PRIVATE);

//        /https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//      Manual intent bc changing the function would break the activity
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        mProfileUsername.setText(name);
        // Set up profile page

        discountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", Context.MODE_PRIVATE);
                mSharedPreferences.edit().clear().apply();

                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, ProfilePage.class);
        return intent;
    }
}