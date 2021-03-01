package com.example.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener {

    ListView cityList;
    ArrayAdapter<City> cityAdapter;
    ArrayList<City> dataList;
    public static final String EXTRA_MESSAGE = "com.example.ListCity.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton","Vancouver","Toronto","Hamilton","Denver","Los Angeles"};
        String []province = {"AB","BC","ON","ON","CO","CA"};

        dataList = new ArrayList<>();

        for(int i=0; i<cities.length; i++) {
            dataList.add(new City(cities[i],province[i]));
        }

        cityAdapter = new CustomList(this, dataList);
        cityList.setAdapter(cityAdapter);


        //Click on list item
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //on clicking an item
                //open a new fragment but Construct it with the current city and the position
                new AddCityFragment(dataList.get(position),position).show(getSupportFragmentManager(), "EDIT_CITY");
            }
        });


        final FloatingActionButton addCityButton = findViewById(R.id.add_edit_button);
        addCityButton.setOnClickListener((v)-> {
            new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
        });

    }

    @Override
    public void onOkPressed(City newCity,Boolean isNew,int position){
        //If adding a new city
        if(isNew) {
            cityAdapter.add(newCity);
        }else{  //editing a city
            //swap out the old city object with new city object
            dataList.set(position,newCity);
            cityAdapter.notifyDataSetChanged(); //update listview

        }
    }

}