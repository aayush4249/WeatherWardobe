package com.example.weatherwardrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    //SharedPreferences pref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
    //String email = pref.getString( "DefaultEmail",  "email@domain.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
    }

    public void login(View v){
       /* SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        EditText e = (EditText)findViewById(R.id.loginText);
        String email = "";
        editor.putString(e.getText().toString(),email);
        editor.commit();*/

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }


}
