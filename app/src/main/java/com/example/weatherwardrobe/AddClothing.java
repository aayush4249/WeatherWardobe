package com.example.weatherwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddClothing extends AppCompatActivity {

    private Spinner typesDropdown;
    private Spinner colourDropdown;
    private Button addButton;
    private Button doneButton;
    private EditText descText;
    private ItemsDataSource dh;
    private static final int Image_Capture_Code = 1;
    private static final String TAG = "AddClothing";
    private byte[] image;

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

        addButton = (Button) findViewById(R.id.camera_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);
            }
        });

        doneButton = (Button) findViewById(R.id.done_outfit);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(image == null){
                    Toast.makeText(getApplicationContext(), "Please take a photo of your clothing item.", Toast.LENGTH_LONG).show();
                }
                else {
                    String type = typesDropdown.getSelectedItem().toString();
                    String colour = colourDropdown.getSelectedItem().toString();

                    descText = findViewById(R.id.descText);
                    String description = descText.getText().toString();
                    Log.d("EditText", descText.getText().toString());
                    ClothingItem newItem = new ClothingItem("name", image, colour, type, 1, description);
                    dh.createItem(newItem);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                image = stream.toByteArray();
                Log.d(TAG, "Finished adding image to item.");
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onDestroy(){
        super.onDestroy();
        dh.close();
    }
}
