package com.example.rollcount;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.rollcount.databinding.ActivitySelectedGameBinding;

public class SelectedGame extends AppCompatActivity {

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
        Intent intent = getIntent();
        Game game = intent.getParcelableExtra(MainActivity.SELECTED_GAME);
        setTitle(game.getName() + "  (" + game.getDateStarted() + ")");
    }
}