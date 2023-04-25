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
import com.example.foodflash.DB.ItemDAO;
import com.example.foodflash.databinding.ActivityAddItemBinding;

import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    TextView title;
    EditText mItemName;
    EditText mItemDescription;
    EditText mItemPrice;
    Button mCreateButton;

    ItemDAO mItemDAO;

    ActivityAddItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        title = binding.addItemTitle;
        mItemName = binding.itemNameEdittext;
        mItemDescription = binding.itemDescriptionEdittext;
        mItemPrice = binding.itemPriceEdittext;
        mCreateButton = binding.itemCreateButton;

        mItemDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries().build().ItemDAO();

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitMenuItem();
                // Create Dialog to confirm item has been entered
            }
        });
    }
    private void submitMenuItem(){
        // Inspired by Dr. C's GymLog
        String strName = mItemName.getText().toString();
        String strDesc = mItemDescription.getText().toString();
        Double price = Double.parseDouble(mItemPrice.getText().toString());

        Item menuItem = new Item(strName, strDesc, price);

        mItemDAO.insert(menuItem);
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, AddItemActivity.class);
        return intent;
    }
}