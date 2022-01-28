package com.example.rollcount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewGameFragment.OnFragmentInteractionListener {
    ListView gameList;
    ArrayAdapter<Game> gameAdapter;
    ArrayList<Game> gameDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // (1) Get game list ListView
        gameList = findViewById(R.id.game_list);
        // (2) Populate game list with saved data
        gameDataList = new ArrayList<Game>();
        // (3) Apply adapter
        gameAdapter = new GameList(this, gameDataList);
        gameList.setAdapter(gameAdapter);

        // (4) `New Game` should open fragment on click
        final Button newGameButton = findViewById(R.id.new_game_btn);
        newGameButton.setOnClickListener((v) -> {
            new NewGameFragment().show(getSupportFragmentManager(), "NEW_GAME");
        });

    }

    @Override
    public void onAddGame(Game game) {
        gameAdapter.add(game);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}