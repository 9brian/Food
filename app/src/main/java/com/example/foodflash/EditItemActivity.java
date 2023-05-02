package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.databinding.ActivityEditItemBinding;
import com.example.foodflash.databinding.ActivityLandingPageBinding;

import java.util.ArrayList;
import java.util.List;

public class EditItemActivity extends AppCompatActivity {

    EditText searchItem;
    EditText changeDescription;
    EditText changePrice;
    Button editButton;

    ItemDAO mItemDAO;
    Item mItemFinder;

    ActivityEditItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        searchItem = binding.editItemNameEdittext;
        changeDescription = binding.editItemDescriptionEdittext;
        changePrice = binding.changeItemPriceEdittext;
        editButton = binding.editItemButton;

        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = searchItem.getText().toString();
                String desc = changeDescription.getText().toString();
                Double price = Double.valueOf(changePrice.getText().toString());
                if(itemExists(item)){
                    Item foundItem = mItemDAO.getItemByName(item);

                    foundItem.setItemDescription(desc);
                    foundItem.setItemPrice(price);

                    mItemDAO.update(foundItem);
                    Toast.makeText(EditItemActivity.this, "Item has been updated", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(EditItemActivity.this, "Item does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean itemExists(String itemName){
        mItemFinder = mItemDAO.getItemByName(itemName);
        return mItemFinder != null;
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