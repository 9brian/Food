package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.DB.UserDAO;
import com.example.foodflash.databinding.ActivityBrowseMenuBinding;

import java.util.List;

public class BrowseMenuActivity extends AppCompatActivity {

    TextView mDisplayMenu;
    ItemDAO mItemDAO;
    List<Item> mMenuList;

    ActivityBrowseMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_menu);

        binding = ActivityBrowseMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDisplayMenu = binding.menuItemsTextview;

        mDisplayMenu.setMovementMethod(new ScrollingMovementMethod());

        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();

        refreshDisplay();
    }
    private void refreshDisplay(){
        mMenuList = mItemDAO.getMenuItems();
        if (! mMenuList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for(Item item : mMenuList){
                sb.append(item.toString());
            }
            mDisplayMenu.setText(sb.toString());
        }
        else{
            mDisplayMenu.setText("No recipes yet! Time to gain some cooking time.");
        }
    }
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, BrowseMenuActivity.class);
        return intent;
    }
}