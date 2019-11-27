package com.example.weatherwardrobe;

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

public class FragmentChooseBottom extends Fragment {
    String ACTIVITY_NAME = "FragmentChooseBottom";

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
        colours = new String[]{getResources().getString(R.string.white), getResources().getString(R.string.black), getResources().getString(R.string.grey), getResources().getString(R.string.red), getResources().getString(R.string.orange), getResources().getString(R.string.yellow), getResources().getString(R.string.green), getResources().getString(R.string.blue), getResources().getString(R.string.purple)};
        types = new String[]{getResources().getString(R.string.pants), getResources().getString(R.string.shorts)};

        //create an adapter to describe how items are displayed. There's multiple variations but this is the basic one.
        typesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, types);
        colourAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, colours);

        //set the spinners adapter to the previously created one.
        typesDropdown.setAdapter(typesAdapter);
        colourDropdown.setAdapter(colourAdapter);

        nextBtn.setText(getResources().getString(R.string.get_outfit));
        chooseTitleText.setText(getResources().getString(R.string.get_outfit_bottom));


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomType = typesDropdown.getSelectedItem().toString();
                bottomColour = colourDropdown.getSelectedItem().toString();

                Log.i(ACTIVITY_NAME, topColour + " " + topType);
                Log.i(ACTIVITY_NAME, bottomColour + " " +  bottomType);

                Bundle clothingItems = getArguments();
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

        return view;
    }
}