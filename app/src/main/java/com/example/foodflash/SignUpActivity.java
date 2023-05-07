package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityMainBinding;
import com.example.foodflash.databinding.ActivitySignUpBinding;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    TextView mTitler;
    EditText mUsername;
    EditText mPassword;
    EditText mRetype;
    Button mSignup;
    TextView mNoSignup;

    TextView mUsernameVerifier;
    TextView mPasswordVerifier;
    TextView mRetypeVerifier;

    UserDAO mUserDAO;
    User mSpecificUser;
    User deleter;

    ActivitySignUpBinding mActivitySignUpBinding;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mActivitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(mActivitySignUpBinding.getRoot());

        mTitler = mActivitySignUpBinding.beginSignupPage;
        mUsername = mActivitySignUpBinding.usernameEdittextSignup;
        mPassword = mActivitySignUpBinding.passwordEdittextSignup;
        mRetype = mActivitySignUpBinding.retypePasswordEdittextSignup;


        mSignup = mActivitySignUpBinding.signupButtonSignup;
        mNoSignup = mActivitySignUpBinding.noSignupTextview;

        mUsernameVerifier = mActivitySignUpBinding.usernameTextviewVerifier;
        mPasswordVerifier = mActivitySignUpBinding.passwordTextviewVerifier;
        mRetypeVerifier = mActivitySignUpBinding.retypePasswordTextviewVerifer;


        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

        mSharedPreferences = getSharedPreferences("loginName", Context.MODE_PRIVATE);

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();

                // Validate Username
                if (uniqueUsername(username)){
                    if (username.length() < 4){
                        mUsernameVerifier.setText("Username must be longer than 4 characters");
                    }
                    else{
                        mUsernameVerifier.setText("");
                    }
                } else{
                    mUsernameVerifier.setText("Username is already taken.");
                }

                // Validate Password
                if(mPassword.getText().toString().length() > 4){
                    mPasswordVerifier.setText("");
                    if(mPassword.getText().toString().equals(mRetype.getText().toString())){
                        mRetypeVerifier.setText("");
                    } else { // Passwords dont match
                        mRetypeVerifier.setText("Passwords do not match");
                    }
                } else{
                    mPasswordVerifier.setText("Password must be longer than 4 characters");
                }

                // If all validator pass
                if(uniqueUsername(username)){
                    // Username is valid
                    if (username.length() > 4){
                        // Password is valid
                        if(mPassword.getText().toString().length() > 4){
                            // Retype password is valid
                            if(mPassword.getText().toString().equals(mRetype.getText().toString())){
                                // Send Intent
                                submitUserInfo();
//                                Intent intent = LandingPageActivity.getIntent(getApplicationContext());
//                                startActivity(intent);

//                              https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//                              How to  use extras

                                SharedPreferences.Editor editor = mSharedPreferences.edit();
                                editor.putString("loginName", username);
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                                intent.putExtra("name", username);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });

        // Manually create an admin
        mSignup.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Validate Username
                if (uniqueUsername(mUsername.getText().toString())){
                    if (mUsername.getText().toString().length() < 4){
                        mUsernameVerifier.setText("Username must be longer than 4 characters");
                    }
                    else{
                        mUsernameVerifier.setText("");
                    }
                } else{
//                    mUsernameVerifier.setText("Username is already taken.");
                    Toast.makeText(SignUpActivity.this, "Username is already taken", Toast.LENGTH_SHORT).show();
                }

                // Validate Password
                if(mPassword.getText().toString().length() > 4){
                    mPasswordVerifier.setText("");
                    if(mPassword.getText().toString().equals(mRetype.getText().toString())){
                        mRetypeVerifier.setText("");
                    } else { // Passwords dont match
                        mRetypeVerifier.setText("Passwords do not match");
                    }
                } else{
                    mPasswordVerifier.setText("Password must be longer than 4 characters");
                }

                // If all validators pass
                if(uniqueUsername(mUsername.getText().toString())){
                    // Username is valid
                    if (mUsername.getText().toString().length() > 4){
                        // Password is valid
                        if(mPassword.getText().toString().length() > 4){
                            // Retype password is valid
                            if(mPassword.getText().toString().equals(mRetype.getText().toString())){
                                // Send Intent
                                submitAdminInfo();

                                String username = mUsername.getText().toString();

//                              https://www.geeksforgeeks.org/how-to-send-data-from-one-activity-to-second-activity-in-android/
//                                how to use extras
//                                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
//                                intent.putExtra("name", username);
//                                startActivity(intent);

                                Intent intent = LandingPageActivity.getIntent(getApplicationContext(), username);
                                startActivity(intent);
                            }
                        }
                    }
                }
                return false;
            }
        });

        // Reroute to login page
        mNoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }//end of onCreate

    private void submitUserInfo(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        int discounter = 1;
        boolean isAdmin = false;

        User user = new User(username, password, discounter, isAdmin);

        mUserDAO.insert(user);
    }

    private void submitAdminInfo(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        int discounter = 1;
        boolean isAdmin = true;

        User admin = new User(username, password, discounter, isAdmin);

        mUserDAO.insert(admin);
    }

    private boolean uniqueUsername(String userName){
        mSpecificUser = mUserDAO.getUserByName(userName);
        return mSpecificUser == null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }
}