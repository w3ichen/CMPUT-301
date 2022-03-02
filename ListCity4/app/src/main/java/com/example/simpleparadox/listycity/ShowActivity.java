package com.example.simpleparadox.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("SELECTED_CITY");

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.selected_city);
        textView.setText(cityName);
    }

    public void goBack(View view){
        finish(); // go back to MainActivity
    }

}