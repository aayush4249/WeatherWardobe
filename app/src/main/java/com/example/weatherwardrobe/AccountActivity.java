package com.example.weatherwardrobe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    TextView user_name_label;
    TextView location_label;
    TextView user_name_value;
    TextView location_value;
    ImageView user_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        user_name_label = findViewById(R.id.account_name);
        location_label = findViewById(R.id.location_Label);
        user_name_value = findViewById(R.id.name_value);
        location_value = findViewById(R.id.location_value);
        user_image = findViewById(R.id.user_image);
        final Button setLocation = findViewById(R.id.btn_set_location);
        final Button detectLocation = findViewById(R.id.btn_detect_location);


        //New user dialog input box to manually set the location
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater infalter = LayoutInflater.from(AccountActivity.this);
                View mview = infalter.inflate(R.layout.location_input_dialog,null);

                AlertDialog.Builder userInput = new AlertDialog.Builder(AccountActivity.this);
                userInput.setView(mview);

                final EditText location_input = (EditText) mview.findViewById(R.id.userInputDialog);

                userInput
                        .setCancelable(false)
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String loc = String.valueOf(location_input.getText());
                                location_value.setText(loc);
                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog builder = userInput.create();
                builder.show();
            }
        });




    }
}
