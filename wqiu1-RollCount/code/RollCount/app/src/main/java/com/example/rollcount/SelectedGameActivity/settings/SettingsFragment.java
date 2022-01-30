package com.example.rollcount.SelectedGameActivity.settings;

import static com.example.rollcount.MainActivity.deleteGame;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rollcount.MainActivity;
import com.example.rollcount.R;
import com.example.rollcount.databinding.SettingsTabBinding;

public class SettingsFragment extends Fragment {

    private SettingsTabBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SettingsTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set onClick for buttons
        Button deleteGameBtn = root.findViewById(R.id.delete_game_btn);
        deleteGameBtn.setOnClickListener((v) -> {
            Intent intent = getActivity().getIntent();
            int gameIndex = intent.getIntExtra(MainActivity.SELECTED_GAME_INDEX, -1);
            deleteGame(gameIndex); // delete the game

            getActivity().finish(); // go back to main activity

        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}