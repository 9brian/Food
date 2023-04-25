package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foodflash.databinding.ActivityAdminPageBinding;

public class AdminPage extends AppCompatActivity {

    Button addButton;
    Button editButton;
    Button deleteButton;
    Button viewButton;

    ActivityAdminPageBinding mActivityAdminPageBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        mActivityAdminPageBinding = ActivityAdminPageBinding.inflate(getLayoutInflater());
        View view = mActivityAdminPageBinding.getRoot();

        setContentView(view);

        addButton = mActivityAdminPageBinding.addItemButton;
        editButton = mActivityAdminPageBinding.editItemButton;
        deleteButton = mActivityAdminPageBinding.deleteItemButton;
        viewButton = mActivityAdminPageBinding.viewUsersButton;

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AllUserActivity.class);
                startActivity(intent);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AllUserActivity.class);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, AdminPage.class);
        return intent;
    }
}