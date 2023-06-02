package com.example.vkrpracticalpartworker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button goToDishAddingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonInitialization();


        for(int i = 0; i < 1; i++)
        {
            ShowOrders();
        }

        //ShowOrders();

    }

    private void ShowOrders()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrderFragment orderFragment = new OrderFragment();
        fragmentTransaction.add(R.id.orders_container, orderFragment);
        fragmentTransaction.commit();
    }


    private void ButtonInitialization()
    {
        goToDishAddingButton = findViewById(R.id.button_adding_dishes);

        goToDishAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DishAddingActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }
}