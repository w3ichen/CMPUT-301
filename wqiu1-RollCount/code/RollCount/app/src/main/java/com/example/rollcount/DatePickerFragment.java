package com.example.rollcount;

import static com.example.rollcount.NewGameFragment.setNewDateText;
import static com.example.rollcount.SelectedGameActivity.settings.SettingsFragment.setOldDateText;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

/**
 * DatePickerFragment
 * Purpose: date picker pop-up that allows users to easily change the date of the game session.
 *      This picker is used in the NewGameFragment and the SettingsFragment.
 * Design rationale: using a calendar selector instead of having the user input the date as text
 *      makes it easier for the user and it prevents errors (such as invalid dates).
 *      This date picker opens when the user clicks on the date text
 * Outstanding issues: None
 */
// Credits: https://developer.android.com/guide/topics/ui/controls/pickers#DatePickerFragment
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String currentDate = getArguments().getString("currentDate");

        String[] yyyyMMdd = currentDate.split("-");
        int year = Integer.parseInt(yyyyMMdd[0]);
        int month = Integer.parseInt(yyyyMMdd[1]) - 1;
        int day = Integer.parseInt(yyyyMMdd[2]);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String dateStr = String.format("%02d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day);
        String type = getArguments().getString("type");
        if (type == "NEW_GAME") {
            setNewDateText(dateStr); // set game text in the NEW_GAME fragment
        } else if (type == "GAME_SETTINGS") {
            setOldDateText(dateStr); // set game text in the Settings fragment
        }

    }
}