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
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityLoginBinding;
import com.example.foodflash.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {
    private static final String LOGIN_ACTIVITY_NAME = "com.example.foodflash.LoginActivityName";

    EditText mUsername_edittext;
    EditText mPassword_edittext;
    Button mLoginLoginButton;
    TextView mLoginVerify;
    TextView mNoLogin;

    UserDAO mUserDAO;
    User mSpecificUser;
    User mExistingUser;

    SharedPreferences mSharedPreferences;

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
        mLoginVerify = mActivityLoginBinding.loginVerifierTextview;
        mNoLogin = mActivityLoginBinding.noLoginTextview;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

        mSharedPreferences = getSharedPreferences("loginName", Context.MODE_PRIVATE);

        mLoginLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername_edittext.getText().toString();
                String password = mPassword_edittext.getText().toString();

                if(findUser(username)){
                    mExistingUser = mUserDAO.getUserByName(username);
                    String p = mExistingUser.getPassWord();

                    if(p.equals(password)){
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString("loginName", username);
                        editor.apply();

//                  https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//                  Sending extras bc if I changed the function things would break
                        Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                        intent.putExtra("name", username);
                        startActivity(intent);
                    }
                    else{
                        mLoginVerify.setText("Username/Password are incorrect");
                    }
                }
                else{
                    mLoginVerify.setText("Username/Password are incorrect");
                }
            }
        });

        mNoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignUpActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

    }
    private boolean findUser(String userName){
        mSpecificUser = mUserDAO.getUserByName(userName);
        // Returns true of user does not exist
        return mSpecificUser != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}