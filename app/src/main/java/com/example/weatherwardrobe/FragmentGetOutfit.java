package com.example.weatherwardrobe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentGetOutfit extends Fragment {

    String ACTIVITY_NAME = "fragmentGetOutfit";

    private Button randomBtn;
    private Button chooseBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_get_outfit, container, false);


        randomBtn = view.findViewById(R.id.random_btn);
        chooseBtn = view.findViewById(R.id.choose_btn);

        randomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentResult frag = new FragmentResult();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                Log.i(ACTIVITY_NAME, "fragment get outfit");

                transaction.replace(R.id.fragmentContainer, frag);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle clothingItems = new Bundle();
//                clothingItems.putString("topType", null);
//                clothingItems.putString("topColour", null);
//                clothingItems.putString("bottomType", null);
//                clothingItems.putString("bottomColour", null);

                FragmentChooseTop frag = new FragmentChooseTop();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                Log.i(ACTIVITY_NAME, "fragment get outfit");

                transaction.replace(R.id.fragmentContainer, frag);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
