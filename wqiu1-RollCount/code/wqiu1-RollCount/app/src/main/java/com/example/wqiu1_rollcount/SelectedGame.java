package com.example.wqiu1_rollcount;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.wqiu1_rollcount.databinding.ActivitySelectedGameBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * SelectedGame
 * Purpose: SelectedGame is another activity that can be reached from the MainActivity by clicking on
 *      one of the game sessions from the list. This new activity serves to show data and actions for only
 *      one game session, that is the game session the user has selected.
 * Design rationale: a new activity was made to separate the selected game from the MainActivity, which
 *      is a list of all the games. This helps to separate the data.
 *      This activity uses the BottomNavigation activity to provide 3 tabs: Play, Stats, Settings.
 *      Where each tab serves its own unique purpose. The first tab is the Play tab which contains
 *      the list of rolls. This is the first tab the user sees when this activity is launched because
 *      it should be the most commonly used. This is because the user uses this tab to play the dice game.
 * Outstanding issues: None
 */
public class SelectedGame extends AppCompatActivity {
    private Intent intent;
    private Game game;
    private ActivitySelectedGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySelectedGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_play, R.id.navigation_stats, R.id.navigation_settings).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_selected_game);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Change navbar title to game name
        intent = getIntent();
        game = intent.getParcelableExtra(MainActivity.SELECTED_GAME);
        setTitle(game.getName() + "  (" + game.getDateStarted() + ")");
    }
}