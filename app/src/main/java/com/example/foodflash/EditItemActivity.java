package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, EditItemActivity.class);
        return intent;
    }


//    private void refreshMenu(){
//        mMenuItemList = mItemDAO.getMenuItems();
//        if(!mMenuItemList.isEmpty()){
//            StringBuilder sb = new StringBuilder();
//            for(Item item : mMenuItemList){
//                sb.append(item.toString());
//            }
//            title.setText(sb.toString());
//        } else {
//            title.setText("hey");
//        }
//    }
}