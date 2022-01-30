package com.example.rollcount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RollsList extends ArrayAdapter<Roll> {
    private ArrayList<Roll> rolls;
    private Context context;
    private Game game;
    private int gameIndex;
    private ArrayList<Integer> rollCounts;

    public RollsList(Context context, ArrayList<Roll> rolls, Game game, int gameIndex, ArrayList<Integer> rollCounts) {
        super(context, 0, rolls);
        this.rolls = rolls;
        this.context = context;
        this.game = game;
        this.gameIndex = gameIndex;
        this.rollCounts = rollCounts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Assemble View of each Roll item
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.play_roll_item, parent, false);
        }
        Roll roll = rolls.get(position);

        // Get the TextViews from the xml file
        TextView roll_name = view.findViewById(R.id.roll_name);
        TextView roll_count = view.findViewById(R.id.roll_count);
        Button roll_plus = view.findViewById(R.id.roll_plus);
        Button roll_minus = view.findViewById(R.id.roll_minus);

        // Set the values of the TextViews
        roll_name.setText("Roll " + String.valueOf(roll.getRollNum()));
        roll_count.setText(String.valueOf(roll.getCount()));

        // On click for the buttons, increase or decrease the buttons
        roll_plus.setOnClickListener((v) -> {
            roll.increaseCount(position, game, gameIndex, rollCounts); // Increase count by one
            roll_count.setText(String.valueOf(roll.getCount())); // refresh count text
        });
        roll_minus.setOnClickListener((v) -> {
            roll.decreaseCount(position, game, gameIndex, rollCounts); // Decrease count by one
            roll_count.setText(String.valueOf(roll.getCount())); // refresh count text
        });

        return view;
    }
}
