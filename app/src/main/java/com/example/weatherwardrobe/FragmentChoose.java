package com.example.weatherwardrobe;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

public class FragmentChoose extends Fragment {

    private Spinner typesDropdown;
    private Spinner colourDropdown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_random, container, false);

        // get the spinner from the xml.
        typesDropdown = view.findViewById(R.id.choose_types_spinner);
        colourDropdown = view.findViewById(R.id.choose_colour_spinner);

        //create a list of items for the spinner.
        String[] colours = new String[]{"white", "black", "grey", "red", "orange", "yellow", "green", "blue", "purple"};
        String[] types = new String[]{"coat", "tshirt", "longsleeve", "sweater", "pants", "shorts"};

        //create an adapter to describe how items are displayed. There's multiple variations but this is the basic one.
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, types);
        ArrayAdapter<String> colourAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, colours);

        //set the spinners adapter to the previously created one.
        typesDropdown.setAdapter(typesAdapter);
        colourDropdown.setAdapter(colourAdapter);

        return view;
    }
}
