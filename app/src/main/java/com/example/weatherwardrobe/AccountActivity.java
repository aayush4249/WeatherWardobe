package com.example.weatherwardrobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class AccountActivity extends AppCompatActivity implements LocationListener {
    boolean permissionGranted = false;
    Location current;
    TextView user_name_label;
    TextView location_label;
    TextView user_name_value;
    TextView location_value;
    ImageView user_image;
    final int permissionCode = 1234;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        user_name_label = findViewById(R.id.account_name);
        location_label = findViewById(R.id.location_Label);
        user_name_value = findViewById(R.id.name_value);
        location_value = findViewById(R.id.location_Value);
        user_image = findViewById(R.id.user_image);
        final Button setLocation = findViewById(R.id.btn_set_location);
        final Button detectLocation = findViewById(R.id.btn_detect_location);

        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String city = preferences.getString("location","");
        location_value.setText(city);
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

                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    request_location_permission();
                    return;
                }
                //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, (LocationListener) AccountActivity.this);

                //Criteria criteria = new Criteria();
                //String bestProvider = locationManager.getBestProvider(criteria, true);
                Location location = getLocation();

                if (location == null) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.gps_signal_not_found), Toast.LENGTH_SHORT).show();
                }

                if (location != null) {
                    onLocationChanged(location);

                }

            }
        });

    }

    public void onLocationChanged(Location location) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Log.i("latitude", "latitude--" + latitude);
        try {
            Log.i("latitude", "inside latitude--" + latitude);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String city = addresses.get(0).getLocality();
                Log.i("City", " " + city);
                location_value.setText(city);

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public Location getLocation() {
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = manager.getProviders(true);
        Location bestLocation = null;

        for (String provider : providers) {

            @SuppressLint("MissingPermission")
            Location l = manager.getLastKnownLocation(provider);
            if (l == null){
                continue;
            }

            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()){
                bestLocation = l;
            }

        }

        if (bestLocation == null){
            return null;
        }
        return bestLocation;
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

        String[] request = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(AccountActivity.this,request,permissionCode);

    }


    public void onRequestPermission(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == permissionCode){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                permissionGranted = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String city = preferences.getString("location","");
        location_value.setText(city);

    }

    @Override
    protected  void onPause(){
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        String loc = (String) location_value.getText();
        edit.putString("location",loc );
        edit.commit();

    }

}
