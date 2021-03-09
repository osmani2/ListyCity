package com.example.simpleparadox.listycity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        String city = getIntent().getStringExtra("City");

        TextView display = (TextView) findViewById(R.id.city_display);
        display.setText(city);
        display.invalidate();
    }

    public void backButtonPressed(View view){
        finish();
    }

}