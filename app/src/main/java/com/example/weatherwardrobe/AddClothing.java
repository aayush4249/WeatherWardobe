package com.example.weatherwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddClothing extends AppCompatActivity {

    private Spinner typesDropdown;
    private Spinner colourDropdown;
    private ItemsDataSource dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clothing);

        dh = new ItemsDataSource(this);
        // open to write into database
        dh.open();

        // get the spinner from the xml.
        typesDropdown = findViewById(R.id.types_spinner);
        colourDropdown = findViewById(R.id.colour_spinner);

        //create a list of items for the spinner.
        String[] colours = new String[]{"white", "black", "grey", "red", "orange", "yellow", "green", "blue", "purple"};
        String[] types = new String[]{"coat", "tshirt", "longsleeve", "sweater", "pants", "shorts"};

        //create an adapter to describe how items are displayed. There's multiple variations but this is the basic one.
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        ArrayAdapter<String> colourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, colours);
        //set the spinners adapter to the previously created one.
        typesDropdown.setAdapter(typesAdapter);
        colourDropdown.setAdapter(colourAdapter);
    }

    public void onClick(View view) {

        String type = typesDropdown.getSelectedItem().toString();
        String colour = colourDropdown.getSelectedItem().toString();

        String x = "123123";
        byte[] b = x.getBytes();
        ClothingItem newItem = new ClothingItem("name", b, colour, type, 1, "desc");
        dh.createItem(newItem);

        /*Intent resultIntent = new Intent();
        resultIntent.putExtra("New Clothing Item", newItem);
        setResult(Activity.RESULT_OK,resultIntent);*/
        finish();
    }
}
