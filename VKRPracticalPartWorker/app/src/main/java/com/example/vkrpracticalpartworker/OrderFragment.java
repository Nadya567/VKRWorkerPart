package com.example.vkrpracticalpartworker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderFragment extends Fragment {

    private Button deleteOrderButton;
    private TextView orderNumberText;
    private TextView orderSumPrice;
    private LinearLayout orderNameList;
    private LinearLayout orderPriceList;
    private Integer sumPrice = 0;


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
        orderSumPrice = view.findViewById(R.id.order_price);
        orderNameList = view.findViewById(R.id.order_name_list);
        orderPriceList = view.findViewById(R.id.order_price_list);

        deleteOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentCLose();
            }
        });

        fillingOrderNameLayout("Морс");
        fillingOrderPriceLayout("70");
        getSumOrderPrice(360);
    }

    private void fragmentCLose()
    {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .remove(this);

        fragmentTransaction.commit();
    }

    private void fillingOrderNameLayout(String name)
    {
        TextView textView = new TextView(getContext());
        textView.setText(name);
        orderNameList.addView(textView);
    }

    private void fillingOrderPriceLayout(String price)
    {
        TextView textView = new TextView(getContext());
        textView.setText(price);
        orderPriceList.addView(textView);
    }

    private void getSumOrderPrice(Integer price)
    {
        sumPrice = sumPrice + price;
        orderSumPrice.setText(sumPrice.toString());
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