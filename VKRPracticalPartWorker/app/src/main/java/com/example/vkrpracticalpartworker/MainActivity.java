package com.example.vkrpracticalpartworker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends AppCompatActivity {

    Button goToDishAddingButton;
    Button goToMenuButton;

    int ordersCount = 0;
    int orderNumber = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonInitialization();



        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("OrderNumber");
        query.orderByDescending("createdAt");

        query.getInBackground("iM4W3C4MBD", new GetCallback<ParseObject>() {
            public void done(ParseObject number, ParseException e) {
                if (e == null) {
                    ordersCount = Integer.parseInt(number.getNumber("OrdersCount").toString());
                }
                else
                {
                    Log.d("!!!!&", "!!!");
                }
            }
        });*/

        ParseQuery<ParseObject> queryOrders = ParseQuery.getQuery("ProductOrders");
        queryOrders.orderByDescending("createdAt");

        queryOrders.findInBackground((objects, e) -> {
            if(e == null)
            {
                int size = objects.size();
                int pastNumber = 0;
                for (int i = 0; i < size; i++)
                {
                    int number = Integer.parseInt(objects.get(i).getNumber("OrderNumber").toString());
                    if(number != pastNumber)
                    {
                        pastNumber = number;
                        Log.d("!!!", String.valueOf(pastNumber));
                        ShowOrders(number);
                    }
                }
            }
            else
            {
                Log.d("!!!!&", "!!!");
            }

        });


        /*for(int i = 0; i < 1; i++)//ordersCount; i++)
        {
            ShowOrders(orderNumber);
        }*/

        //ShowOrders();

    }

    private void ShowOrders(int _orderNumber)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OrderFragment orderFragment = new OrderFragment(_orderNumber);
        fragmentTransaction.add(R.id.orders_container, orderFragment);
        fragmentTransaction.commit();
    }


    private void ButtonInitialization()
    {
        goToDishAddingButton = findViewById(R.id.button_adding_dishes);
        goToMenuButton = findViewById(R.id.button_menu);

        goToDishAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DishAddingActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        goToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

    }
}