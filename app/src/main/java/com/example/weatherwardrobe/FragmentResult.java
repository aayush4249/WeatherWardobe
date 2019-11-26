package com.example.weatherwardrobe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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

        Bundle bundle = getArguments();

        topType = bundle.getString("topType");
        topColour = bundle.getString("topColour");
        bottomType = bundle.getString("bottomType");
        bottomColour = bundle.getString("bottomColour");

        topResult.setText(topColour + " " + topType);
        bottomResult.setText(bottomColour + " " + bottomType);

        return view;
    }
}
