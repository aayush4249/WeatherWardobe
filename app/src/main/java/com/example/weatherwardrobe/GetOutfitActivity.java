package com.example.weatherwardrobe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class GetOutfitActivity extends AppCompatActivity {
    String ACTIVITY_NAME = "GetOutfitActivity";

    ProgressBar progressBar;
    public boolean chooseRandom;
    private Button randomBtn;
    private Button chooseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_outfit);

        randomBtn = findViewById(R.id.random_btn);
        chooseBtn = findViewById(R.id.choose_btn);









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


    }
}
