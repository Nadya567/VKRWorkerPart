package com.example.vkrpracticalpartworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DishAddingActivity extends AppCompatActivity {

    Button goToOrdersButton;
    Button goToMenuButton;
    Button choosePicture;
    Button addDishToMenuButton;

    private final int Pick_image = 1;

    EditText dishNameText;
    EditText dishPriceText;
    EditText dishDescriptionText;
    ImageView dishPicture;
    Spinner dishCategoryText;
    int dishNumber = 3;
    String dishName;
    int dishPrice;
    String dishDescription;
    int dishCategory;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_adding);

        ButtonInitialization();
        dishPicture = findViewById(R.id.dish_image);
        dishNameText = findViewById(R.id.dish_name_enter);
        dishPriceText = findViewById(R.id.dish_price_enter);
        dishDescriptionText = findViewById(R.id.dish_composition_enter);
        dishCategoryText = findViewById(R.id.dish_category_enter);
    }

    private void ButtonInitialization()
    {
        goToOrdersButton = findViewById(R.id.button_orders);
        goToMenuButton = findViewById(R.id.button_menu);
        choosePicture = findViewById(R.id.add_picture_button);
        addDishToMenuButton = findViewById(R.id.add_dish_button);

        goToOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DishAddingActivity.this, MainActivity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        goToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DishAddingActivity.this, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        choosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent, Pick_image);

            }
        });

        addDishToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readDishCharacteristics();
                sendDishToDashboard();
                Toast.makeText(DishAddingActivity.this, "Добавлено", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void readDishCharacteristics()
    {
        dishName = dishNameText.getText().toString();
        dishPrice = Integer.valueOf(dishPriceText.getText().toString());
        dishDescription = dishDescriptionText.getText().toString();
        category = dishCategoryText.getSelectedItem().toString();

        String[] categoriesArray = getResources().getStringArray(R.array.categories);
        for(int i = 0; i < categoriesArray.length; i++)
        {
            if(category.equals(categoriesArray[i]))
            {
                dishCategory = i;
            }
        }
    }

    private void sendDishToDashboard()
    {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu");
        query.orderByDescending("createdAt");
        query.findInBackground((objects, e) -> {
            if(e == null)
            {
                ParseObject dish = new ParseObject("Menu");

                dish.put("CategoryNumber", dishCategory);
                dish.put("DishNumber", dishNumber);
                dish.put("DishName", dishName);
                dish.put("DishPrice", dishPrice);
                dish.put("DishDescription", dishDescription);

                dish.saveInBackground();
            }
            else
            {
                Log.d("!!!!!", "!!!");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        dishPicture.setImageBitmap(selectedImage);
                    }

                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }}}
