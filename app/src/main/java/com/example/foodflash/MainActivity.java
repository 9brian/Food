package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mActivityMainBinding.getRoot();

        setContentView(view);

        mTitle = mActivityMainBinding.titleId;
        mLoginButton = mActivityMainBinding.loginButton;
        mSignUpButton = mActivityMainBinding.signupButton;

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