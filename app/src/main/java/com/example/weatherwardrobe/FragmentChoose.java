package com.example.weatherwardrobe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentChoose extends Fragment {

    String ACTIVITY_NAME = "FragmentChoose";

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

        nextBtn = view.findViewById(R.id.choose_next_btn);
        chooseTitleText = view.findViewById(R.id.choose_title_text);

        // get the spinner from the xml.
        typesDropdown = view.findViewById(R.id.choose_types_spinner);
        colourDropdown = view.findViewById(R.id.choose_colour_spinner);

        //create a list of items for the spinner.
        colours = new String[]{"white", "black", "grey", "red", "orange", "yellow", "green", "blue", "purple"};
        types = new String[]{"coat", "tshirt", "longsleeve", "sweater"};

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

                //create a list of items for the spinner.
                types = new String[]{"pants", "shorts"};

                //create an adapter to describe how items are displayed. There's multiple variations but this is the basic one.
                typesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, types);
                colourAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, colours);

                //set the spinners adapter to the previously created one.
                typesDropdown.setAdapter(typesAdapter);
                colourDropdown.setAdapter(colourAdapter);

                nextBtn.setText("Get Outfit");
                chooseTitleText.setText("Bottom Preferences");

                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomType = typesDropdown.getSelectedItem().toString();
                        bottomColour = colourDropdown.getSelectedItem().toString();

                        Log.i(ACTIVITY_NAME, topColour + " " + topType);
                        Log.i(ACTIVITY_NAME, bottomColour + " " +  bottomType);

                        Bundle clothingItems = new Bundle();
                        clothingItems.putString("topType", topType);
                        clothingItems.putString("topColour", topColour);
                        clothingItems.putString("bottomType", bottomType);
                        clothingItems.putString("bottomColour", bottomColour);

                        FragmentResult frag = new FragmentResult();

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                        Log.i(ACTIVITY_NAME, "fragment get outfit");

                        frag.setArguments(clothingItems);
                        transaction.replace(R.id.fragmentContainer, frag);

                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        });


        return view;
    }
}
