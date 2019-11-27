package com.example.weatherwardrobe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class FragmentResult extends Fragment {

    String ACTIVITY_NAME = "FragmentResult";

    private TextView topResult;
    private TextView bottomResult;

    private String topType;
    private String topColour;
    private String bottomType;
    private String bottomColour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        topResult = view.findViewById(R.id.result_top);
        bottomResult = view.findViewById(R.id.result_bottom);

        // get current temperature
        SharedPreferences preferences = getActivity().getSharedPreferences("my_prefs", MODE_PRIVATE);
        float currentTemp = Float.parseFloat(preferences.getString("currentTemp",""));

        Log.i(ACTIVITY_NAME, "temp: " + currentTemp);

        // outfit generator
        OutfitGenerator generator = new OutfitGenerator(getContext(), currentTemp);

        Bundle bundle = getArguments();
        if(bundle != null) {
            topType = bundle.getString("topType");
            topColour = bundle.getString("topColour");
            bottomType = bundle.getString("bottomType");
            bottomColour = bundle.getString("bottomColour");

            topResult.setText(topColour + " " + topType);
            bottomResult.setText(bottomColour + " " + bottomType);
        } else {
            Log.i(ACTIVITY_NAME, "bundle empty");

            // clothing items from generator
            ClothingItem outerwear = generator.selectedOutfit.outerwear;
            ClothingItem top = generator.selectedOutfit.top;
            ClothingItem bottom = generator.selectedOutfit.bottom;

            // set on result page
            Log.i(ACTIVITY_NAME, "" + outerwear);
            topResult.setText("" + top);
            bottomResult.setText("" + bottom);

        }



        return view;
    }
}
