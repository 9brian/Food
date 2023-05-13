package com.example.foodflash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodflash.DB.AppDataBase;
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.databinding.ActivityDeleteItemBinding;

public class DeleteItemActivity extends AppCompatActivity {

    EditText mItemName;
    Button mDeleteItem;
    TextView mItemFoundDisplay;

    Item locatedItem;

    ItemDAO mItemDAO;
    ActivityDeleteItemBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        binding = ActivityDeleteItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mItemName = binding.deleteItemNameEdittext;
        mDeleteItem = binding.deleteItemFoundButton;
        mItemFoundDisplay = binding.foundItemToDeleteTextview;

        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();

        mDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchItem = mItemName.getText().toString();

                if(itemExists(searchItem)){
                    mItemFoundDisplay.setText("");
                    locatedItem = mItemDAO.getItemByName(searchItem);

                    // https://www.youtube.com/watch?v=cPr22fRFGwg
                    // How to create a dialog found here ^
                    AlertDialog newAlert = new AlertDialog.Builder(DeleteItemActivity.this).create();
                    newAlert.setTitle("Are you sure you want to delete this item?");
                    newAlert.setMessage(locatedItem.toString());
                    newAlert.setButton(newAlert.BUTTON_POSITIVE, "NO",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                    newAlert.setButton(newAlert.BUTTON_NEGATIVE, "YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mItemDAO.delete(locatedItem);
                                    dialogInterface.dismiss();
                                }
                            });

                    newAlert.show();
                } else {
                    mItemFoundDisplay.setText("Item does not exist");
                }
            }
        });


    }

    private boolean itemExists(String itemName){
        return mItemDAO.getItemByName(itemName) != null;
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, DeleteItemActivity.class);
        return intent;
    }
}