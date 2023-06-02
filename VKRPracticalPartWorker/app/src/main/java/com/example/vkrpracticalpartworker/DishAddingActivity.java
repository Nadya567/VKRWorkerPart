package com.example.vkrpracticalpartworker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DishAddingActivity extends AppCompatActivity {

    Button goToOrdersButton;
    Button choosePicture;
    Button addDishToMenuButton;
    ImageView dishPicture;
    private final int Pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_adding);

        ButtonInitialization();
        dishPicture = findViewById(R.id.dish_image);
    }

    private void ButtonInitialization()
    {
        goToOrdersButton = findViewById(R.id.button_orders);
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
                Toast.makeText(DishAddingActivity.this, "Добавлено", Toast.LENGTH_SHORT).show();
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
