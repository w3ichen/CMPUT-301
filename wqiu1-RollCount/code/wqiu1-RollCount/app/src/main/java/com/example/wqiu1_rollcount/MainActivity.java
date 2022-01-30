package com.example.wqiu1_rollcount;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * MainActivity
 * Purpose: the MainActivity is the first activity that the user sees when they open the app.
 * When they first open the app, they will see a list of all the game sessions
 * Design rationale: the list of game sessions is shown on app launch because the user must first select
 * an existing game session or create a new one before using the other features of the app, that is
 * counting rolls, seeing stats, and editing the game session.
 * Outstanding issues: None
 */
public class MainActivity extends AppCompatActivity implements NewGameFragment.OnFragmentInteractionListener {
    private ListView gameList;
    private static ArrayAdapter<Game> gameAdapter;
    private static ArrayList<Game> gameDataList;
    private static TextView totalRollsText;
    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    public static final String SELECTED_GAME = "com.example.rollcount.SELECTED_GAME";
    public static final String SELECTED_GAME_ROLL_COUNTS = "com.example.rollcount.SELECTED_GAME_ROLL_COUNTS";
    public static final String SELECTED_GAME_INDEX = "com.example.rollcount.SELECTED_GAME_INDEX";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // (1) Get game list ListView
        gameList = findViewById(R.id.game_list);
        // (2) Populate game list with saved data
        // Credit: https://developer.android.com/training/data-storage/shared-preferences
        // Access the saved store
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        // Parse and use the saved values if it exists
        Gson gson = new Gson();
        String json = sharedPref.getString("saved_game_list", null);
        Type type = new TypeToken<ArrayList<Game>>() {
        }.getType();
        if (json == null) {
            gameDataList = new ArrayList<Game>();
        } else {
            gameDataList = gson.fromJson(json, type);
        }

        // (3) Apply adapter
        gameAdapter = new GameList(this, gameDataList);
        gameList.setAdapter(gameAdapter);

        // (4) `New Game` should open fragment on click
        final Button newGameButton = findViewById(R.id.new_game_btn);
        newGameButton.setOnClickListener((v) -> {
            new NewGameFragment().show(getSupportFragmentManager(), "NEW_GAME");
        });

        // (5) On game item click, open new SelectedGame activity
        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Game selectedGame = (Game) gameList.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, SelectedGame.class);
                intent.putExtra(SELECTED_GAME, (Parcelable) selectedGame);
                intent.putExtra(SELECTED_GAME_ROLL_COUNTS, (Serializable) selectedGame.getRollCounts());
                intent.putExtra(SELECTED_GAME_INDEX, i);
                startActivity(intent); // Go to SelectedGame activity
            }
        });

        // (6) Count and set total rolls
        totalRollsText = findViewById(R.id.total_rolls);
        int totalRolls = 0;
        for (Game aGame : gameDataList) {
            totalRolls += aGame.getTotalRolls();
        }
        totalRollsText.setText(totalRolls + " total rolls");
    }

    @Override
    public void onAddGame(Game game) {
        gameAdapter.insert(game, 0);
        // Credits: https://stackoverflow.com/a/56682835
        save();
    }

    public static void updateGame(Integer index, Game game) {
        gameDataList.set(index, game);
        gameAdapter.notifyDataSetChanged();

        save();
    }

    public static void deleteGame(int index) {
        gameDataList.remove(index); // remove game at the index
        gameAdapter.notifyDataSetChanged();

        save();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void save() {
        // Save updated list to Android preferences
        Gson gson = new Gson();
        String json = gson.toJson(gameDataList);
        editor.putString("saved_game_list", json);
        editor.apply();

        // Re-count the total rolls
        int totalRolls = 0;
        for (Game aGame : gameDataList) {
            totalRolls += aGame.getTotalRolls();
        }
        totalRollsText.setText(totalRolls + " total rolls");
    }

}