package com.example.rollcount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game {
    private String dateStarted; // date started (presented in yyyy-mm-dd format, editable and automatically filled)
    private String name; // name (textual, up to 40 characters)
    private Integer numRolls; // # rolls (N)
    private Integer numDiceSides; // # of dice sides (M)

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumRolls() {
        return numRolls;
    }

    public void setNumRolls(Integer numRolls) {
        this.numRolls = numRolls;
    }

    public Integer getNumDiceSides() {
        return numDiceSides;
    }

    public void setNumDiceSides(Integer numDiceSides) {
        this.numDiceSides = numDiceSides;
    }

    public Game(String dateStarted, String name, Integer numRolls, Integer numDiceSides) {
        this.dateStarted = dateStarted;
        this.name = name;
        this.numRolls = numRolls;
        this.numDiceSides = numDiceSides;
    }
}
