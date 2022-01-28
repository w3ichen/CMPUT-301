package com.example.rollcount;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewGameFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-mm-dd");
    Date today = new Date();

    public interface OnFragmentInteractionListener {
        void onAddGame(Game game);
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
        EditText gameDate = view.findViewById(R.id.edit_game_date);
        EditText gameName = view.findViewById(R.id.edit_game_name);
        EditText gameNumRolls = view.findViewById(R.id.edit_num_rolls);
        EditText gameNumDiceSides = view.findViewById(R.id.edit_num_dice_sides);

        // (2) Populate views
        try {
            gameDate.setText((CharSequence) dateFormat.parse(today.toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("New game session")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Date dateStarted = null;
                        try {
                            dateStarted = dateFormat.parse(gameDate.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String name = gameName.getText().toString();
                        Integer numRolls = Integer.parseInt(gameNumRolls.getText().toString());
                        Integer numDiceSides = Integer.parseInt(gameNumDiceSides.getText().toString());
                        listener.onAddGame(new Game(dateStarted, name, numRolls, numDiceSides));

                    }
                }).create();
    }

}
