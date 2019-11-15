package com.example.weatherwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login_activity extends AppCompatActivity {

    //SharedPreferences pref = login_activity.this.getPreferences(Context.MODE_PRIVATE);
    //String email = pref.getString( "DefaultEmail",  "email@domain.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
    }

    public void login(View v){
       /* SharedPreferences sharedPref = login_activity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        EditText e = (EditText)findViewById(R.id.loginText);
        String email = "";
        editor.putString(e.getText().toString(),email);
        editor.commit();*/

        Intent intent = new Intent(login_activity.this,MainActivity.class);
        startActivity(intent);
    }


}
