package com.example.wqiu1_rollcount;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * GameList
 * Purpose: the GameList is an ArrayAdapter that transforms the ArrayList of Game objects into the
 *      ListView which is displayed to the user
 * Design rationale: each of the Game items in the list displays its name, date, and dice which
 *      are the three most important attributes of each game session.
 *      The name of the game session is the largest and in bold because it is the identifier of each
 *      game session and helps the user to easily identify each session when it is larger.
 * Outstanding issues: None
 */
public class GameList extends ArrayAdapter<Game> {

    private ArrayList<Game> games;
    private Context context;

    public GameList(Context context, ArrayList<Game> games) {
        super(context, 0, games);
        this.games = games;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Assemble View of each Game item
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.game_item, parent, false);
        }
        Game game = games.get(position);

        // Get the TextViews from the xml file
        TextView game_name = view.findViewById(R.id.game_name);
        TextView game_date = view.findViewById(R.id.game_date);
        TextView game_dice = view.findViewById(R.id.game_dice);

        // Set the values of the TextViews
        game_name.setText(game.getName());
        game_date.setText(game.getDateStarted());
        game_dice.setText(game.getNumRolls() + "d" + game.getNumDiceSides());

        return view;
    }
}
