package com.gabrielmadeira.seupedido;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("instagramxptohagsggas5afs")
                .clientKey("xbsbgdsgdgsasgga")
                .server("https://instagramxpto.herokuapp.com/parse/")
                .build()
        );
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);
        setContentView(R.layout.activity_main);
        ParseUser.logOut();
//        Intent intent = new Intent(this, Main2Activity.class);
//        startActivity(intent);
        if (ParseUser.getCurrentUser() == null){
            Intent intent = new Intent(this,Signin.class);

            startActivity(intent);
        }


    }
}

