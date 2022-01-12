package com.example.lonelytwitter;

import java.util.Date;

public class NormalTweet extends Tweet{

    public NormalTweet(Date date, String message) {
        super(date, message);
    }

    public NormalTweet(String message) {
        super(message);
    }

    @Override
    public Boolean isImportant() {
        return Boolean.FALSE;
    }
}
