package com.example.lonelytwitter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImportantTweet importantTweet = new ImportantTweet("");
        importantTweet.setMessage("HELLO");

        NormalTweet normalTweet = new NormalTweet("Hi!");

        ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
        tweetList.add(importantTweet);
        tweetList.add(normalTweet);
    }
}