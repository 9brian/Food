package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
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

    EditText mUsername_edittext;
    EditText mPassword_edittext;
    Button mLoginLoginButton;
    TextView mLoginVerify;
    TextView mNoLogin;

    UserDAO mUserDAO;
    User mSpecificUser;
    User mExistingUser;

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

        mLoginLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername_edittext.getText().toString();
                String password = mPassword_edittext.getText().toString();

                Log.d("username", username);

                mExistingUser = mUserDAO.getUserByName(username);
                String p = mExistingUser.getPassWord();

                if (findUser(username) && p.equals(password)){
                    Log.d("UNIQUE", "Logging In");
                    // Send intent
                    Intent intent = LandingPageActivity.getIntent(getApplicationContext());
                    startActivity(intent);

                } else if (password.length() < 4) {
//                    Log.d("Left blank", "Enter password");
                    mLoginVerify.setText("Password must be longer than 4 characters");
                } else {
//                    Log.d("UNIQUE", "Username/Password dne");
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