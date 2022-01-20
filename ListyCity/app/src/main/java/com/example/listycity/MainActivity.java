package com.example.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    public int selectItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectItemPosition = -1;

        cityList = findViewById(R.id.city_list);
        String [] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi", "Toronto", "Montreal", "Ottawa", "Cambridge"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);

        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               selectItemPosition = position;
            }
        });
    }

    public void addCity(View view){
        // Make the textfield and confirm buttons visible
        EditText editText = (EditText)  findViewById(R.id.new_city);
        Button confirmButton = (Button)  findViewById(R.id.add_city_confirm);

        if (editText.getVisibility() == View.VISIBLE || confirmButton.getVisibility() == View.VISIBLE){
            editText.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
        }else{
            editText.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
        }
    }

    public void addCityConfirm(View view){
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText)  findViewById(R.id.new_city);
        String new_city = editText.getText().toString();

        // Add to list
        dataList.add(new_city);
        cityAdapter.notifyDataSetChanged();

        // Hide keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        // Hide textfield and confirm button
        Button confirmButton = (Button)  findViewById(R.id.add_city_confirm);
        confirmButton.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);



    }
    public void deleteCity(View view){
        if (selectItemPosition >= 0){ // if an item is selected
            dataList.remove(selectItemPosition); // remove city from dataList
            cityAdapter.notifyDataSetChanged();
        }
    }
}