package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodflash.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    // Adding a comment

    TextView mTitle;
    Button mLoginButton;
    Button mSignUpButton;

    ActivityMainBinding mActivityMainBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mActivityMainBinding.getRoot();

        setContentView(view);

//        Uncomment if delete account breaks again
//        mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", Context.MODE_PRIVATE);
//        mSharedPreferences.edit().clear().apply();

        mTitle = mActivityMainBinding.titleId;
        mLoginButton = mActivityMainBinding.loginButton;
        mSignUpButton = mActivityMainBinding.signupButton;
        Log.d("WORKING", "working");


        mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", Context.MODE_PRIVATE);

        if(mSharedPreferences.contains("loginName")){
            Log.d("HEBF", "JERE");
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
//                String workingClick = mTitle.getText().toString();
//                mTitle.setText("Welcome");
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String workingClick = mTitle.getText().toString();
//                mTitle.setText("Bye");
                Intent intent = SignUpActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

    }
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}