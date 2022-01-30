package com.example.wqiu1_rollcount;

import static com.example.wqiu1_rollcount.MainActivity.deleteGame;
import static com.example.wqiu1_rollcount.MainActivity.updateGame;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import com.example.wqiu1_rollcount.databinding.SettingsTabBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * SettingsFragment
 * Purpose: renders the view of the "Settings" tab which contains the ability to view the
 *      game session attributes, edit these attributes, and delete the game session
 * Design rationale: this was split off as its own class so each tab can be designed separately.
 *      The ability to edit and delete the game session was grouped together because it made logical
 *      sense since both actions modify the game session in some way.
 *      The game session attributes are displayed with the ability to edit it so that view/edit game
 *      attributes can be accomplished at the same time (saving code and space in the app)
 * Outstanding issues: None
 */
public class SettingsFragment extends Fragment {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SettingsTabBinding binding;
    private static TextView gameDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = SettingsTabBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get selected game values
        Intent intent = getActivity().getIntent();
        Game game = intent.getParcelableExtra(MainActivity.SELECTED_GAME);
        int gameIndex = intent.getIntExtra(MainActivity.SELECTED_GAME_INDEX, -1);
        ArrayList<Integer> rollCounts = (ArrayList<Integer>) intent.getSerializableExtra(MainActivity.SELECTED_GAME_ROLL_COUNTS);


        // Get views
        gameDate = root.findViewById(R.id.edit_game_date);
        EditText gameName = root.findViewById(R.id.edit_game_name);
        EditText gameNumRolls = root.findViewById(R.id.edit_num_rolls);
        EditText gameNumDiceSides = root.findViewById(R.id.edit_num_dice_sides);

        // Populate views with game values
        Date today = new Date();
        gameDate.setText(dateFormat.format(today));
        gameName.setText(game.getName());
        gameNumRolls.setText(String.valueOf(game.getNumRolls()));
        gameNumDiceSides.setText(String.valueOf(game.getNumDiceSides()));

        // Set onClick for buttons
        Button deleteGameBtn = root.findViewById(R.id.delete_game_btn);
        deleteGameBtn.setOnClickListener((v) -> {
            deleteGame(gameIndex); // delete the game

            getActivity().finish(); // go back to main activity
        });

        // Set onclick for date picker
        gameDate.setOnClickListener((v) -> {
            DialogFragment newFragment = new DatePickerFragment();
            // Pass in the current date
            Bundle args = new Bundle();
            args.putString("currentDate", gameDate.getText().toString());
            args.putString("type", "GAME_SETTINGS");
            newFragment.setArguments(args);

            newFragment.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
        });

        Button saveChangesBtn = root.findViewById(R.id.save_changes_btn);
        saveChangesBtn.setOnClickListener((v) -> {
            if (TextUtils.isEmpty(gameDate.getError()) &&
                    TextUtils.isEmpty(gameName.getError()) &&
                    TextUtils.isEmpty(gameNumRolls.getError()) &&
                    TextUtils.isEmpty(gameNumDiceSides.getError())
            ) {
                // No errors, make the change
                Game updatedGame = new Game(gameDate.getText().toString(), gameName.getText().toString(),
                        Integer.parseInt(gameNumRolls.getText().toString()), Integer.parseInt(gameNumDiceSides.getText().toString()));
                // if numRoll and numDiceSides has not changed, then should use the old rollCounts
                if (game.getNumRolls() == updatedGame.getNumRolls() && game.getNumDiceSides() == updatedGame.getNumDiceSides()) {
                    updatedGame.setRollCounts(rollCounts); // use old roll counts
                } // else reset rollCounts to 0
                updateGame(gameIndex, updatedGame); // update
                // exit the activity
                getActivity().finish();
            } else {
                // has errors, do nothing
            }
        });

        // Add validation to text fields
        gameDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    dateFormat.parse(gameDate.getText().toString());
                    gameDate.setError(null);
                } catch (ParseException e) {
                    gameDate.setError("Date must match the yyyy-MM-dd pattern");
                }
            }
        });
        gameName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (gameName.getText().toString().length() < 1) {
                    gameName.setError("Name is required");
                } else {
                    gameName.setError(null);
                }
            }
        });
        gameNumRolls.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Integer N = Integer.parseInt(gameNumRolls.getText().toString());
                    if (N < 1) {
                        gameNumRolls.setError("# of rolls must be at least 1");
                    } else if (N > 3) {
                        gameNumRolls.setError("# of rolls must be no more than 3");
                    } else {
                        gameNumRolls.setError(null);
                    }
                } catch (NumberFormatException e) {
                    gameNumRolls.setError("# of rolls is required");
                }
            }
        });
        gameNumDiceSides.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Integer M = Integer.parseInt(gameNumDiceSides.getText().toString());
                    if (M < 1) {
                        gameNumDiceSides.setError("# of dice sides must be at least 1");
                    } else {
                        gameNumDiceSides.setError(null);
                    }
                } catch (NumberFormatException e) {
                    gameNumDiceSides.setError("# of rolls is required");
                }
            }
        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void setOldDateText(String dateStr) {
        if (gameDate != null) gameDate.setText(dateStr);
    }
}