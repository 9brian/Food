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

    UserDAO mUserDAO;
    List<User> mUserList;

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

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

//        mTitler.setMovementMethod(new ScrollingMovementMethod());

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPassword.getText().toString().equals(mRetype.getText().toString())){
                    mTitler.setText("Works");
                    // Username verifier
                    // Password longer than 4 characters verifier
                    // Retype password is same verifier
//                submitUserInfo();
                } else{
                    // Username verifier displays username is already taken
                    // Password verifier displays password is not longer than 4 characters
                    // Retype password displays not the same
//                submitUserInfo();
                }
//                mTitler.setText(mPassword.getText().toString().equals(mRetype.getText().toString()));

//                refreshDisplay();
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

    private void refreshDisplay(){
        mUserList = mUserDAO.getUsers();
        if(!mUserList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(User u : mUserList){
                sb.append(u.toString());
            }
            mTitler.setText(sb.toString());
        }else{
            mTitler.setText(R.string.no_users_msg);
        }
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }
}