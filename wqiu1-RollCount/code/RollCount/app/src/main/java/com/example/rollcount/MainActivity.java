package com.example.rollcount;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewGameFragment.OnFragmentInteractionListener {
    private ListView gameList;
    private  ArrayAdapter<Game> gameAdapter;
    private ArrayList<Game> gameDataList;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    public static final String SELECTED_GAME = "com.example.rollcount.SELECTED_GAME";


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
        String json = sharedPref.getString("saved_game_list",null);
        Type type = new TypeToken<ArrayList<Game>>() {}.getType();
        if (json == null){
            gameDataList = new ArrayList<Game>();
        }else{
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
                intent.putExtra(SELECTED_GAME, selectedGame);
                startActivity(intent); // Go to SelectedGame activity
            }
        });
    }

    @Override
    public void onAddGame(Game game) {
        gameAdapter.insert(game, 0);
        // Credits: https://stackoverflow.com/a/56682835
        // Save new list to Android preferences
        Gson gson = new Gson();
        String json = gson.toJson(gameDataList);
        editor.putString("saved_game_list", json);
        editor.apply();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}