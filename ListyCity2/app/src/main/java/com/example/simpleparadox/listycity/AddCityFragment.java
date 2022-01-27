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
    private Integer position;
    private  City initialCity;


    public interface OnFragmentInteractionListener {
        void onAddCity(City newCity);

        void onEditCity(City updatedCity, int index);
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
        position = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        Bundle arguments = getArguments();
        boolean isEditCity = arguments != null;
        initialCity = isEditCity? (City) arguments.get("city") : null;

        if (isEditCity) {
            cityName.setText(initialCity.getCity());
            provinceName.setText(initialCity.getProvince());
            position = (int) getArguments().get("position");
        }


        return builder
                .setView(view)
                .setTitle("Add/edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String city = cityName.getText().toString();
                        String province = provinceName.getText().toString();
                        if (initialCity != null) {
                            initialCity.setCity(city);
                            initialCity.setProvince(province);
                            listener.onEditCity(initialCity, position);
                        } else {
                            listener.onAddCity(new City(city, province));
                        }
                    }
                }).create();
    }

    static AddCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        args.putInt("position", position);

        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
