package com.gabrielmadeira.seupedido;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Signup extends AppCompatActivity {

    public void signinActivity(View view){
        Intent intent = new Intent(this,Signin.class);

        startActivity(intent);
    }
    public void signup(View view){
        Log.d("AppInfo","Signup tapped");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Intent intent = getIntent();
    }
}
