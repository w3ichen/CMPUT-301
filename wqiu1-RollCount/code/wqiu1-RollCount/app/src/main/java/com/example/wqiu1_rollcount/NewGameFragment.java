package com.example.wqiu1_rollcount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * NewGameFragment
 * Purpose: when the user clicks on the "New Game" button, the NewGameFragment opens up as a dialog pop-up.
 *      It will prompt the user for the game session's date, name, number of rolls, and number of dice sides.
 * Design rationale: a pop-up was used because we only want to see this form when the user wants to create
 *      a new game session, so in the interest of saving space in the app and not showing this form when not
 *      needed, a pop-up was used. All the values in the form are pre-filled with common values to make it
 *      easier on the user to create a game session. Error validation is also put in place to ensure accurate date,
 *      and the "Create" button is disabled when there are errors to prevent submitting and saving bad data
 * Outstanding issues: None
 */
public class NewGameFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date today = new Date();
    private AlertDialog dialog;
    private static TextView gameDate;
    private EditText gameName;
    private EditText gameNumRolls;
    private EditText gameNumDiceSides;

    public interface OnFragmentInteractionListener {
        void onAddGame(Game game);
    }
    public void setDate(String newDate){
        gameDate.setText(newDate);
    }

    private void enablePositiveBtn() {
        // Disable fragment dialog button if there is an error
        if (TextUtils.isEmpty(gameDate.getError()) &&
                TextUtils.isEmpty(gameName.getError()) &&
                TextUtils.isEmpty(gameNumRolls.getError()) &&
                TextUtils.isEmpty(gameNumDiceSides.getError())
        ) {
            dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true); // enable
        } else {
            dialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false); // disable
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException((context.toString()
                    + "must implement OnFragmentInteractionListener"));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // (1) Get views
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.new_game_fragment, null);
        gameDate = view.findViewById(R.id.edit_game_date);
        gameName = view.findViewById(R.id.edit_game_name);
        gameNumRolls = view.findViewById(R.id.edit_num_rolls);
        gameNumDiceSides = view.findViewById(R.id.edit_num_dice_sides);

        // (2) Populate views
        gameDate.setText(dateFormat.format(today));
        // Set onclick for date
        gameDate.setOnClickListener((v)->{
            DialogFragment newFragment = new DatePickerFragment();
            // Pass in the current date
            Bundle args = new Bundle();
            args.putString("currentDate", gameDate.getText().toString());
            args.putString("type", "NEW_GAME");
            newFragment.setArguments(args);

            newFragment.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
        });

        // (3) Add validation to text fields
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
                } finally {
                    enablePositiveBtn();
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
                }else{
                    gameName.setError(null);
                }
                enablePositiveBtn();
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
                    }else{
                        gameNumRolls.setError(null);
                    }
                } catch (NumberFormatException e) {
                    gameNumRolls.setError("# of rolls is required");
                } finally {
                    enablePositiveBtn();
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
                    }else{
                        gameNumDiceSides.setError(null);
                    }
                } catch (NumberFormatException e) {
                    gameNumDiceSides.setError("# of rolls is required");
                } finally {
                    enablePositiveBtn();

                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        dialog = builder
                .setView(view)
                .setTitle("New game session")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dateStarted = gameDate.getText().toString();
                        String name = gameName.getText().toString();
                        Integer numRolls = Integer.parseInt(gameNumRolls.getText().toString());
                        Integer numDiceSides = Integer.parseInt(gameNumDiceSides.getText().toString());
                        listener.onAddGame(new Game(dateStarted, name, numRolls, numDiceSides));

                    }
                }).create();
        return dialog;
    }
    public static void setNewDateText(String dateStr) {
        if (gameDate != null) gameDate.setText(dateStr);
    }


}
