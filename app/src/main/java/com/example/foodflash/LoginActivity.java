package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodflash.databinding.ActivityLoginBinding;
import com.example.foodflash.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {

    EditText mUsername_edittext;
    EditText mPassword_edittext;
    Button mLoginLoginButton;

    ActivityLoginBinding mActivityLoginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mActivityLoginBinding = mActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mActivityLoginBinding.getRoot());

        mUsername_edittext = mActivityLoginBinding.usernameEdittext;
        mPassword_edittext = mActivityLoginBinding.passwordEdittext;
        mLoginLoginButton = mActivityLoginBinding.loginLoginButton;

        mLoginLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String workingClick = mLoginLoginButton.getText().toString();
                String username = mUsername_edittext.getText().toString();
                mLoginLoginButton.setText(username);
            }
        });

    }
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}