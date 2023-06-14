package com.example.vkrpracticalpartworker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OrderFragment extends Fragment {

    private Button deleteOrderButton;
    private TextView orderNumberText;
    private TextView orderSumPrice;
    private LinearLayout orderNameList;
    private LinearLayout orderPriceList;
    private Integer sumPrice = 0;
    private String productId;
    private String productName;
    private String productNameForPrice;
    private int productPrice;
    private int ordersCount;
    private int orderNumber;
    private int dishCount = 0;
    private String[] dishArrayInOrder;

    public OrderFragment(int _orderNumber) {
        orderNumber = _orderNumber;
    }

    public OrderFragment() {

    }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();

        deleteOrderButton = view.findViewById(R.id.button_order_delete);
        orderNumberText = view.findViewById(R.id.order_number_text);
        orderNumberText.setText(String.valueOf(orderNumber));
        orderSumPrice = view.findViewById(R.id.order_price);
        orderNameList = view.findViewById(R.id.order_name_list);
        orderPriceList = view.findViewById(R.id.order_price_list);

        deleteOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderDelete();
                fragmentCLose();
            }
        });

        fillingOrderNameLayout(String.valueOf(orderNumber));
        //fillingOrderPriceLayout(String.valueOf(orderNumber));
        //getSumOrderPrice(360);
    }

    private void fragmentCLose()
    {
        /*ParseQuery<ParseObject> query = ParseQuery.getQuery("OrderNumber");
        query.orderByDescending("createdAt");

        query.getInBackground("iM4W3C4MBD", new GetCallback<ParseObject>() {
            public void done(ParseObject number, ParseException e) {
                if (e == null) {
                    ordersCount = Integer.parseInt(number.getNumber("OrdersCount").toString());
                    ordersCount--;
                    number.put("OrdersCount", ordersCount);
                    number.saveInBackground();
                }
                else
                {
                    Log.d("!!!!&", "!!!");
                }
            }
        });*/

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .remove(this);

        fragmentTransaction.commit();
    }

    private void fillingOrderNameLayout(String number)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ProductOrders");
        query.orderByDescending("createdAt");

        query.findInBackground((objects, e) -> {
            if(e == null)
            {
                int size = objects.size();
                for (int i = 0; i < size; i++)
                {
                    String n = (objects.get(i).getNumber("OrderNumber")).toString();
                    if(n.equals(number))
                    {
                        productName = objects.get(i).getString("DishName");

                        TextView textView = new TextView(getContext());
                        textView.setText(productName);
                        orderNameList.addView(textView);
                        dishCount++;
                    }
                }
                dishArrayInOrder = new String[dishCount];
                fillingOrderPriceLayout(number);
            }
            else
            {
                Log.d("!!!!&", "!!!");
            }

        });


    }

    private void fillingOrderPriceLayout(String number)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ProductOrders");
        query.orderByDescending("createdAt");
        query.findInBackground((objects, e) -> {
            if(e == null)
            {
                int j = 0;
                int size = objects.size();
                for (int i = 0; i < size; i++)
                {
                    String n = objects.get(i).getNumber("OrderNumber").toString();
                    if(n.equals(number))
                    {
                        productNameForPrice = objects.get(i).getString("DishName");
                        //Log.d("!!!!", productNameForPrice);
                        dishArrayInOrder[j] = productNameForPrice;
                        j++;
                    }
                }
                getPriceFromDashboard();
            }
            else
            {
                Log.d("!!!!&", "!!!");
            }

        });
    }

    private void getPriceFromDashboard()
    {
        ParseQuery<ParseObject> queryMenu = ParseQuery.getQuery("Menu");
        queryMenu.orderByDescending("createdAt");
        queryMenu.findInBackground((orderObjects, er) -> {
            if(er == null)
            {
                int orderSize = orderObjects.size();
                for (int i = 0; i < orderSize; i++)
                {
                    for(int w = 0; w < dishArrayInOrder.length; w++)
                    {
                        if(orderObjects.get(i).getString("DishName").equals(dishArrayInOrder[w]))
                        {
                            //Log.d("!!!", String.valueOf(j));

                            productPrice = Integer.parseInt((orderObjects.get(i).getNumber("DishPrice")).toString());
                            //Log.d("!!!", String.valueOf(productPrice));
                            TextView textView = new TextView(getContext());
                            textView.setText(String.valueOf(productPrice));
                            orderPriceList.addView(textView);
                            getSumOrderPrice(productPrice);
                        }
                    }
                }
            }
            else
            {
                Log.d("!!!!&", "!!!");
            }

        });
    }

    private void getSumOrderPrice(Integer price)
    {
        sumPrice = sumPrice + price;
        orderSumPrice.setText(sumPrice.toString());
    }

    private void orderDelete()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ProductOrders");
        query.orderByDescending("createdAt");

            query.findInBackground((objects, e) -> {
                if(e == null)
                {
                    int size = objects.size();
                    for (int i = 0; i < size; i++)
                    {
                        if(objects.get(i).getNumber("OrderNumber").toString().equals(orderNumberText.getText()))
                        {
                            productId = objects.get(i).getObjectId();

                            query.getInBackground(productId, new GetCallback<ParseObject>() {
                                public void done(ParseObject object, ParseException e) {
                                    if (e == null) {
                                        object.deleteInBackground();
                                    }

                                    else
                                    {
                                        Log.d("!", "productId");
                                    }
                                }
                            });
                        }
                    }
                }
                else
                {
                    Log.d("!!!!&", "!!!");
                }

            });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
}