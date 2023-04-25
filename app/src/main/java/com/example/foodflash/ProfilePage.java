package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityProfilePageBinding;

import java.util.List;

public class ProfilePage extends AppCompatActivity {
    TextView mProfileUsername;
    Button discountButton;
    Button deleteButton;
    Button logOutButton;

    UserDAO mUserDAO;
    List<User> mUserList;
    User deleteUser;
    User mSpecificUser;

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

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

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
                // https://www.youtube.com/watch?v=cPr22fRFGwg
                // How to create a dialog found here ^
                AlertDialog newAlert = new AlertDialog.Builder(ProfilePage.this).create();
                newAlert.setTitle("Are you sure you want to delete your account?");
//                newAlert.setMessage("(Please don't)");
                newAlert.setButton(newAlert.BUTTON_POSITIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                newAlert.setButton(newAlert.BUTTON_NEGATIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                    deleteUser = mUserDAO.getUserByName(name);

                                    if(!uniqueUsername(name)){
                                        Log.d("DELETE?", deleteUser.toString());
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
//                                        https://stackoverflow.com/questions/3687315/how-to-delete-shared-preferences-data-from-app-in-android
//                                      Clear shared preferences (clear all of them)
                                        mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", Context.MODE_PRIVATE);
                                        mSharedPreferences.edit().clear().apply();
                                        mUserDAO.delete(deleteUser);
                                    }
                                    else{
                                        Log.d("Why", "We shouldn't be here");
                                    }
                                dialogInterface.dismiss();
                            }
                        });

                newAlert.show();
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//              https://stackoverflow.com/questions/3687315/how-to-delete-shared-preferences-data-from-app-in-android
//              Clear shared preferences (clear all of them)
                mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", Context.MODE_PRIVATE);
                mSharedPreferences.edit().clear().apply();

                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    private boolean uniqueUsername(String userName){
        mSpecificUser = mUserDAO.getUserByName(userName);

        return mSpecificUser == null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, ProfilePage.class);
        return intent;
    }
}