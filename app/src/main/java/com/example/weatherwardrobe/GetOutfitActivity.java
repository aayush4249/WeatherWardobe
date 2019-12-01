package com.example.weatherwardrobe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class GetOutfitActivity extends AppCompatActivity {
    String ACTIVITY_NAME = "GetOutfitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_outfit);

        getOutfitFragment();
    }

    public void getOutfitFragment() {

        FragmentGetOutfit frag = new FragmentGetOutfit();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Log.i(ACTIVITY_NAME, "random fragment");

        transaction.replace(R.id.fragmentContainer, frag);

//        transaction.add(R.id.fragmentContainer, frag);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

//
//    public void chooseFragment(View v){
//        FragmentChooseTop frag = new FragmentChooseTop();
//
//        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        transaction.add(R.id.fragmentContainer, frag);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
}








//        // set progress bars visibility
//        progressBar = findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);
//
//        // set progress bars visibility
//        progressBar = findViewById(R.id.go_progressBar);
//        progressBar.setVisibility(View.VISIBLE);
//
//        Button btn = findViewById(R.id.go_next_btn);
//
//        // get the spinner from the xml.
//        Spinner dropdown = findViewById(R.id.go_spinner);
//
//        //create a list of items for the spinner.
//        String[] items = new String[]{"white", "black", "grey", "brown", "red", "orange", "yellow", "green", "blue", "purple", "pink"};
//
//        //create an adapter to describe how items are displayed. There's multiple variations but this is the basic one.
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//
//        //set the spinners adapter to the previously created one.
//        dropdown.setAdapter(adapter);
//
//        String colour = dropdown.getSelectedItem().toString();
//
//
//
//
//
//
//
//        //create a list of items for the spinner.
//        items = new String[]{"coat", "tshirt", "longsleeve", "sweater", "pants", "shorts"};
//
//        //create an adapter to describe how items are displayed. There's multiple variations but this is the basic one.
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//
//        //set the spinners adapter to the previously created one.
//        dropdown.setAdapter(adapter);







// getOutfit and progressbar process
// re-roll outfit 33%

// type options 33%

// colour options 33%