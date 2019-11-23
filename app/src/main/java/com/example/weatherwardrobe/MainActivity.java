package com.example.weatherwardrobe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView current_temp;
    TextView min_temp;
    TextView max_temp;

    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        if (id == R.id.about){
            Toast toast = Toast.makeText(getApplicationContext(), "Authors: Brian Tiner, Adam Gumieniak, Aayush Sheth, Dylan Clarry, Brian Mietkiewicz\nVersion: 1.0", Toast.LENGTH_LONG);
            toast.show();
        }
        else if(id == R.id.guide){
            Intent intent = new Intent(MainActivity.this, GuideActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current_temp = findViewById(R.id.temp);
        min_temp = findViewById(R.id.min);
        max_temp = findViewById(R.id.max);
        imageView = findViewById(R.id.forecastPicture);

        ForecastQuery f = new ForecastQuery();
        f.execute();

        // getOut
        final Button intentGetOutfit = findViewById(R.id.get_outfit_btn);
        final Button intentMyWardrobe = findViewById(R.id.my_wardrobe_button);
        final ImageButton intentAccount = findViewById(R.id.account);
        final Button intentLaundryBasket = findViewById(R.id.laundry_button);
        intentGetOutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetOutfitActivity.class);
                startActivity(intent);
            }
        });

        intentMyWardrobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyWardrobe.class);
                startActivity(intent);
            }
        });

        intentAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

        intentLaundryBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LaundryBasket.class);
                startActivity(intent);
            }
        });


    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String currentTemp;
        private String minTemp;
        private String maxTemp;
        private Bitmap picture;

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=0f39ce3557fa38dd0ffc197ecb534735&mode=xml&units=metric");

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream in = conn.getInputStream();

                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);

                    int type;
                    while ((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("temperature")) {
                                currentTemp = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemp = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";

                                if (fileExistence(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    picture = BitmapFactory.decodeStream(fis);
                                } else {
                                    String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                    picture = getImage(new URL(iconUrl));

                                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    picture.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }

                        }
                        // Go to the next XML event
                        parser.next();
                    }
                } finally {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return "";

        }

        public boolean fileExistence(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String a) {
            imageView.setImageBitmap(picture);
            current_temp.setText("Current Temp: " + currentTemp + "C\u00b0");
            min_temp.setText("Minimum Temp: " + minTemp + "C\u00b0");
            max_temp.setText("Maximum Temp: " + maxTemp + "C\u00b0");
        }
    }
}
