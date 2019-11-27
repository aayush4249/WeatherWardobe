package com.example.weatherwardrobe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentChooseTop extends Fragment {

    String ACTIVITY_NAME = "FragmentChooseTop";

    private ProgressBar progressBar;
    private Spinner typesDropdown;
    private Spinner colourDropdown;
    private Button nextBtn;
    private String[] colours;
    private String[] types;
    private ArrayAdapter<String> typesAdapter;
    private ArrayAdapter<String> colourAdapter;
    private TextView chooseTitleText;

    private String topType;
    private String topColour;
    private String bottomType;
    private String bottomColour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose, container, false);

        // set progress bars visibility
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        nextBtn = view.findViewById(R.id.choose_next_btn);
        chooseTitleText = view.findViewById(R.id.choose_title_text);

        // get the spinner from the xml.
        typesDropdown = view.findViewById(R.id.choose_types_spinner);
        colourDropdown = view.findViewById(R.id.choose_colour_spinner);

        //create a list of items for the spinner.
        colours = new String[]{getResources().getString(R.string.white), getResources().getString(R.string.black), getResources().getString(R.string.grey), getResources().getString(R.string.red), getResources().getString(R.string.orange), getResources().getString(R.string.yellow), getResources().getString(R.string.green), getResources().getString(R.string.blue), getResources().getString(R.string.purple)};
        //{"white", "black", "grey", "red", "orange", "yellow", "green", "blue", "purple"};
        types = new String[]{getResources().getString(R.string.coat), getResources().getString(R.string.tshirt), getResources().getString(R.string.longsleeve), getResources().getString(R.string.sweater)};
        //{"coat", "tshirt", "longsleeve", "sweater"};



        //create an adapter to describe how items are displayed. There's multiple variations but this is the basic one.
        typesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, types);
        colourAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, colours);

        //set the spinners adapter to the previously created one.
        typesDropdown.setAdapter(typesAdapter);
        colourDropdown.setAdapter(colourAdapter);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topType = typesDropdown.getSelectedItem().toString();
                topColour = colourDropdown.getSelectedItem().toString();

                Log.i(ACTIVITY_NAME, topColour + " " + topType);
                Log.i(ACTIVITY_NAME, bottomColour + " " +  bottomType);

                Bundle clothingItems = new Bundle();
                clothingItems.putString("topType", topType);
                clothingItems.putString("topColour", topColour);

                FragmentChooseBottom frag = new FragmentChooseBottom();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                Log.i(ACTIVITY_NAME, "fragment get outfit");

                frag.setArguments(clothingItems);
                transaction.replace(R.id.fragmentContainer, frag);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}