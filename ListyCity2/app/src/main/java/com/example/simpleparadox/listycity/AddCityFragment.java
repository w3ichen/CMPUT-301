package com.example.simpleparadox.listycity;

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

public class AddCityFragment extends DialogFragment {
    private EditText cityName;
    private EditText provinceName;
    private OnFragmentInteractionListener listener;


    public interface OnFragmentInteractionListener {
        void onOkPressed(City newCity);
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_fragment_layout, null);
        cityName = view.findViewById(R.id.city_name_editText);
        provinceName = view.findViewById(R.id.province_name_editText);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                    .setView(view)
                    .setTitle("Add City")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String city = cityName.getText().toString();
                            String province = provinceName.getText().toString();
                            listener.onOkPressed(new City(city, province));
                        }
                    }).create();
    }
}
