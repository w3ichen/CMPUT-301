package com.example.wqiu1_rollcount;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Game
 * Purpose: the Game class models a Game session. It contains all of the attributes and getter/setter
 *      functions. It also initializes the attributes in the constructor. Notably, the `rollCounts` array
 *      is initialized with 0's and with a size according to `numRolls` and `numDiceSides`.
 * Design rationale: Game models Game session
 * Outstanding issues: None
 */
// Paercelable Credits: https://stackoverflow.com/a/2141166
public class Game implements Parcelable, Serializable {
    private String dateStarted; // date started (presented in yyyy-mm-dd format, editable and automatically filled)
    private String name; // name (textual, up to 40 characters)
    private Integer numRolls; // # rolls (N)
    private Integer numDiceSides; // # of dice sides (M)
    private ArrayList<Integer> rollCounts;


    protected Game(Parcel in) {
        dateStarted = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            numRolls = null;
        } else {
            numRolls = in.readInt();
        }
        if (in.readByte() == 0) {
            numDiceSides = null;
        } else {
            numDiceSides = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dateStarted);
        dest.writeString(name);
        if (numRolls == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numRolls);
        }
        if (numDiceSides == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numDiceSides);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

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

    public ArrayList<Integer> getRollCounts() {
        return rollCounts;
    }

    public void setRollCounts(ArrayList<Integer> rollCounts) {
        this.rollCounts = rollCounts;
    }


    public Integer getRollNumFromPosition(Integer position){
        return numRolls + position;
    }

    public Game(String dateStarted, String name, Integer numRolls, Integer numDiceSides) {
        this.dateStarted = dateStarted;
        this.name = name;
        this.numRolls = numRolls;
        this.numDiceSides = numDiceSides;
        // Assign all possible rolls to 0
        rollCounts = new ArrayList<>();
        for (int i=numRolls;i<=numRolls * numDiceSides;i++){
            rollCounts.add(0); // initialize count with zeroes
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Integer getTotalRolls(){
        return (int) rollCounts.stream().mapToDouble(x->x).sum();
    }
}
