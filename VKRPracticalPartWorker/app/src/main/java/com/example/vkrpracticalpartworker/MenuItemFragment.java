package com.example.vkrpracticalpartworker;

import android.content.res.TypedArray;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MenuItemFragment extends Fragment {

    Button deleteItemButton;
    TextView dishNameText;
    TextView dishPriceText;
    ImageView dishPicture;


    String dishName;
    String dishPrice;
    int category;
    int number;
    Image dishImage;
    String productId = "";
    int size;

    public MenuItemFragment() {

    }
    public MenuItemFragment(String name, String price) {
        dishName = name;
        dishPrice = price;
    }

    public MenuItemFragment(String name, String price, int _category, int _number) {
        dishName = name;
        dishPrice = price;
        category = _category;
        number = _number;
        //dishImage = image;
    }


    public static MenuItemFragment newInstance() {
        MenuItemFragment fragment = new MenuItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();


        deleteItemButton = view.findViewById(R.id.menu_item_delete);
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentCLose();
            }
        });

        dishNameText = view.findViewById(R.id.food_name);
        dishPriceText = view. findViewById(R.id.food_price);
        dishPicture = view.findViewById(R.id.big_food_image);

        FillingFragment();

    }

    private void fragmentCLose1()
    {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .remove(this);

        fragmentTransaction.commit();
    }

    private void FillingFragment()
    {
        dishNameText.setText(dishName);
        dishPriceText.setText(dishPrice);
        if(category == 0)
        {
            TypedArray images = getResources().obtainTypedArray(R.array.SaladsImages);
            dishPicture.setImageResource(images.getResourceId(number,0));
        }

        if(category == 1)
        {
            TypedArray images = getResources().obtainTypedArray(R.array.SoupsImages);
            dishPicture.setImageResource(images.getResourceId(number,0));
        }

        if(category == 2)
        {
            TypedArray images = getResources().obtainTypedArray(R.array.HotDishesImages);
            dishPicture.setImageResource(images.getResourceId(number,0));
        }

        if(category == 3)
        {
            TypedArray images = getResources().obtainTypedArray(R.array.SecondDishesImages);
            dishPicture.setImageResource(images.getResourceId(number,0));
        }

        if(category == 4)
        {
            TypedArray images = getResources().obtainTypedArray(R.array.DrinksImages);
            dishPicture.setImageResource(images.getResourceId(number,0));
        }

        if(category == 5)
        {
            TypedArray images = getResources().obtainTypedArray(R.array.DessertsImages);
            dishPicture.setImageResource(images.getResourceId(number,0));
        }
    }

    private void fragmentCLose()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu");
        query.orderByDescending("createdAt");


        query.findInBackground((objects, e) -> {
            if(e == null)
            {
                size = objects.size();

                for(int i = 0; i < size; i++)
                {
                    if(objects.get(i).getString("DishName").equals(dishName) )
                    {
                        productId = objects.get(i).getObjectId();

                        //Log.d("!!!", productId);

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





        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .remove(this);

        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_item, container, false);
    }
}