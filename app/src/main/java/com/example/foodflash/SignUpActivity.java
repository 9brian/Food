package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    List<User> mUserList;
    User mSpecificUser;
    User deleter;

    ActivitySignUpBinding mActivitySignUpBinding;

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

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate Username
                if (uniqueUsername(mUsername.getText().toString())){
                    if (mUsername.getText().toString().length() < 4){
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

                // If all validaters pass
                if(uniqueUsername(mUsername.getText().toString())){
                    // Usrename is valid
                    if (mUsername.getText().toString().length() > 4){
                        // Password is valid
                        if(mPassword.getText().toString().length() > 4){
                            // Retype password is valid
                            if(mPassword.getText().toString().equals(mRetype.getText().toString())){
//                            usernameVerification();
                                // Send Intent
                                submitUserInfo();
                                Intent intent = LandingPageActivity.getIntent(getApplicationContext());
                                startActivity(intent);

                            }
                        }
                    }
                }

                // Manually delete entries
                if(mUsername.getText().toString().equals("DeleteMyAccount")){
                    String hardCodedString = "admin2";
                    deleter = mUserDAO.getUserByName(hardCodedString);

                    if(!uniqueUsername(hardCodedString)){
                        Log.d("DELETE?", deleter.toString());
                        mUserDAO.delete(deleter);
                    }
                    else{
                        Log.d("DELETE", deleter.toString());
                    }
                }

                refreshDisplay();
            }


        });

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

                // If all validaters pass
                if(uniqueUsername(mUsername.getText().toString())){
                    // Usrename is valid
                    if (mUsername.getText().toString().length() > 4){
                        // Password is valid
                        if(mPassword.getText().toString().length() > 4){
                            // Retype password is valid
                            if(mPassword.getText().toString().equals(mRetype.getText().toString())){
//                            usernameVerification();
                                // Send Intent
                                submitAdminInfo();
                                Intent intent = LandingPageActivity.getIntent(getApplicationContext());
                                startActivity(intent);
                            }
                        }
                    }
                }

                // Manually delete entries
                if(mUsername.getText().toString().equals("DeleteMyAccount")){
                    String hardCodedString = "Yes";
                    deleter = mUserDAO.getUserByName(hardCodedString);

                    if(uniqueUsername(hardCodedString)){
                        Log.d("DELETE?", deleter.toString());
                        mUserDAO.delete(deleter);
                    }
                }

                refreshDisplay();
                return false;
            }
        });

        mNoSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });


        refreshDisplay();
    }//end of onCreate

//    private void deleteUserInfo(){
//        String username = mUsername.getText().toString();
//        String password = mPassword.getText().toString();
//        String discounter = "1";
//        boolean isAdmin = true;
//
//        User user = new User(username, password, discounter, isAdmin);
//
//        mUserDAO.delete(user);
//    }
    private void submitUserInfo(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String discounter = "1";
        boolean isAdmin = false;

        User user = new User(username, password, discounter, isAdmin);

        mUserDAO.insert(user);
    }

    private void submitAdminInfo(){
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String discounter = "1";
        boolean isAdmin = true;

        User admin = new User(username, password, discounter, isAdmin);

        mUserDAO.insert(admin);
    }

    private void refreshDisplay(){
//        mUserList = mUserDAO.getUsers();
//        if(!mUserList.isEmpty()){
//            StringBuilder sb = new StringBuilder();
//            for(User u : mUserList){
//                sb.append(u.toString());
//            }
////            mTitler.setText(sb.toString());
//        }else{
//            mTitler.setText(R.string.no_users_msg);
//        }

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