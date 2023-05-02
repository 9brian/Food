package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityAllUserBinding;

import java.util.List;

public class AllUserActivity extends AppCompatActivity {

    TextView mDisplayAllUsers;

    UserDAO mUserDAO;
    List<User> mUserList;
    ActivityAllUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);

        binding = ActivityAllUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDisplayAllUsers = binding.allUsersTextview;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().UserDAO();

        refreshDisplay();
    }

    private void refreshDisplay(){
        mUserList = mUserDAO.getAllUsers();
        if (! mUserList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(User user : mUserList){
                sb.append(user.toString());
            }
            mDisplayAllUsers.setText(sb.toString());
        }
        else{
            mDisplayAllUsers.setText("No users yet! Time to gain some popularity.");
        }
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, AllUserActivity.class);
        return intent;
    }
}