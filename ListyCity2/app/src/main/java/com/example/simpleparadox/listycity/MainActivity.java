package com.example.simpleparadox.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener {

    // Declare the variables so that you will be able to reference it later.
    ListView cityList;
    ArrayAdapter<City> cityAdapter;
    ArrayList<City> cityDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Vancouver", "Toronto", "Hamilton"};
        String[] provinces = {"AB", "BC", "ON", "ON"};

        cityDataList = new ArrayList<>();

        // Add all the data
        for (int i = 0; i < cities.length; i++) {
            cityDataList.add(new City(cities[i], provinces[i]));
        }

        cityAdapter = new CustomList(this, cityDataList);

        cityList.setAdapter(cityAdapter);


        final FloatingActionButton addCityButton = findViewById(R.id.add_city_button);
        addCityButton.setOnClickListener((v) -> {
            new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
        });

        // Allow editing on item click
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City selectedCity = (City) cityList.getItemAtPosition(i);
                AddCityFragment.newInstance(selectedCity, i).show(getSupportFragmentManager(), "EDIT_CITY");
            }
        });
    }

    @Override
    public void onAddCity(City newCity) {
        cityAdapter.add(newCity);
    }

    @Override
    public void onEditCity(City updatedCity, int index) {
        cityDataList.set(index, updatedCity);
        cityAdapter.notifyDataSetChanged();
    }

}
