package com.example.vkrpracticalpartworker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MenuActivity extends AppCompatActivity {

    Button goToDishAddingButton;
    Button goToOrdersButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textView = findViewById(R.id.menu_text);


        ButtonInitialization();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu");
        query.orderByDescending("createdAt");
        query.findInBackground((objects, e) -> {
            if(e == null)
            {
                //textView.setText((objects.get(3).getNumber("DishPrice")).toString());
                //int s = Integer.parseInt((objects.get(2).getNumber("DishPrice")).toString());

                //textView.setText((objects.get(2).getNumber("DishPrice")).toString());

                int size = objects.size();

                for(int i = 0; i < size; i++)
                {
                    ShowMenuItems(objects.get(i).getString("DishName"), (objects.get(i).getNumber("DishPrice")).toString(),
                            Integer.parseInt((objects.get(i).getNumber("CategoryNumber")).toString()), Integer.parseInt((objects.get(i).getNumber("DishNumber")).toString()));
                }

            }
            else
            {
                Log.d("!!!!&", "!!!");
            }
        });
    }

    private void ButtonInitialization()
    {
        goToDishAddingButton = findViewById(R.id.button_adding_dishes);

        goToDishAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, DishAddingActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        goToOrdersButton = findViewById(R.id.button_orders);

        goToOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }


    private void ShowMenuItems(String name, String price, int category, int number)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MenuItemFragment menuItemFragment = new MenuItemFragment(name, price, category, number);
        fragmentTransaction.add(R.id.dishes_container, menuItemFragment);
        fragmentTransaction.commit();
    }
}