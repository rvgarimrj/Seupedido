package com.gabrielmadeira.seupedido;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Signin extends AppCompatActivity {

    String MSG = "AppInfo";
    String mEmail;
    String mPassword;
    EditText userEmail;
    EditText userPass;
//    ProgressDialog progress;
//    private ProgressDialog progressDialog;

    public void signupActivity(View view){
        Intent intent = new Intent(this,Signup.class);

        startActivity(intent);

    }
    public void login(View view) {

        mEmail = userEmail.getText().toString().trim();
        mPassword = userPass.getText().toString();

        if (!mEmail.isEmpty() & !mPassword.isEmpty()) {
            Log.i(MSG, mEmail);
            Log.i(MSG, mPassword);
            ProgressDialog dialog = ProgressDialog.show(getApplicationContext(),"teste","aguarde");
//            progressDialog.show();
            ParseUser.logInInBackground(mEmail, mPassword, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null){

                        Toast.makeText(getApplicationContext(),getString(R.string.log_in_successfull), Toast.LENGTH_SHORT).show();
                        Log.i(MSG,"Logged in");
                    }
                    else {
                        Toast.makeText(getApplicationContext(),getString(R.string.log_in_unsuccessfull), Toast.LENGTH_LONG).show();
                        Log.i(MSG,"Not logged in");
                        e.printStackTrace();

                    }
                }

            });
//            progressDialog.cancel();
//            dialog.dismiss();
        }
        else
        {
            Toast.makeText(getApplicationContext(),getString(R.string.msg_blank_login), Toast.LENGTH_LONG).show();
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_signin);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Conectando...");

        Intent intent = getIntent();

        userEmail = (EditText) findViewById(R.id.useremailLogin);
        userPass = (EditText) findViewById(R.id.userpassLogin);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

