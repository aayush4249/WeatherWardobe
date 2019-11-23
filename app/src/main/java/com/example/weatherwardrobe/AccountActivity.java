package com.example.weatherwardrobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.security.Permission;
import java.util.List;
import java.util.Locale;

public class AccountActivity extends AppCompatActivity {
    private boolean permissionGranted = false;
    Location current;
    TextView user_name_label;
    TextView location_label;
    TextView user_name_value;
    TextView location_value;
    ImageView user_image;
    private FusedLocationProviderClient fusedLocationProviderClient;


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

                inputDialog();
            }
        });

        detectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    get_location();
                    location_value.setText("");
                    Geocoder coder = new Geocoder(AccountActivity.this, Locale.getDefault());
                    StringBuilder stringBuilder = new StringBuilder();
                    try{
                        double lat = current.getLatitude();
                        double lon = current.getLongitude();
                        List<Address> addresses = coder.getFromLocation(lat,lon,1);

                        int maxLines = addresses.get(0).getMaxAddressLineIndex();
                        for (int i=0; i<maxLines; i++) {
                            String addressStr = addresses.get(0).getAddressLine(i);
                            stringBuilder.append(addressStr);
                            stringBuilder.append(" ");
                        }

                        String finalAddress = stringBuilder.toString(); //This is the complete address.

                        location_value.setText(finalAddress); //This will display the final address.


                    }catch(IOException e){

                    }catch (NullPointerException e){

                    }
                }
                else{
                    request_location_permission();
                }
            }
        });
    }

    //Function that uses alert dialog to build a user input dialog
    public void inputDialog(){
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

    public void request_location_permission(){

        String[] request = {Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(AccountActivity.this,request,1234);

    }


    public void onRequestPermission(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1234){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                permissionGranted = true;
            }
        }
    }


    //Geo Location
    public void get_location() {


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AccountActivity.this);

        try {
            if (permissionGranted) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            current = (Location) task.getResult();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Location Service Unavailable", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });


            }
        } catch (SecurityException e) {
            Log.e("Error", "Get Device Location: Security Exception" + e.getMessage());

        }

    }
}
