package com.example.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    public static final String EXTRA_MESSAGE = "com.example.ListCity.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton","Vancouver","Moscow","Sydney","Berlin","Vienna","Tokyo","Beijing","Osaka","New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content,dataList);
        cityList.setAdapter(cityAdapter);

        //DELETE ITEM ON CLICK
        //README citation 1
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataList.remove(position);
                cityAdapter.notifyDataSetChanged();
            }
        });

    }

    /** Called when the user taps the Add City button */
    //README citation 3
    public void addCity(View view) {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String city = editText.getText().toString();
        dataList.add(city);
        cityAdapter.notifyDataSetChanged();

    }

}