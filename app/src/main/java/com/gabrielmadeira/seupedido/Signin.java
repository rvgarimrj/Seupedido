package com.gabrielmadeira.seupedido;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Signin extends AppCompatActivity implements View.OnClickListener , View.OnKeyListener{

    String MSG = "AppInfo";
    String mEmail;
    String mPassword;
    EditText userEmail;
    EditText userPass;
    private ProgressDialog dialog;
    ImageView logo;
    ConstraintLayout constraintLayout;

    @Override
    public void onClick(View view) {
        if ((view.getId() == R.id.logo) || (view.getId() == R.id.constraint_layout)){
            closeKeyboard(true);
        }

    }

    // ***********************************************************************************************************************************************************************
    // Hide keyboard after inputing pass and email to show progressbar. ENTER KEY = Sigin after password

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN) {

            login(view);
        }

        return false;
    }




    private void closeKeyboard(boolean b) {

        View view = this.getCurrentFocus();

        if(b) {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
        else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }

    // ***********************************************************************************************************************************************************************


    public void signupActivity(View view){
        Intent intent = new Intent(this,Signup.class);

        startActivity(intent);

    }

    // ***********************************************************************************************************************************************************************
    // Button login tapped

    public void login(View view) {

        closeKeyboard(true);
        mEmail = userEmail.getText().toString().trim();
        mPassword = userPass.getText().toString();
        if (!mEmail.isEmpty() & !mPassword.isEmpty()) {
            Log.i(MSG, mEmail);
            Log.i(MSG, mPassword);
            dialog = new ProgressDialog(Signin.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog = ProgressDialog.show(Signin.this,getString(R.string.login_dialog_title),getString(R.string.login_dialog_msg), false, true);
            dialog.setIcon(R.drawable.ic_email_black_24dp);
            dialog.setCancelable(false);

//            new ProgressTask().execute();
            new Thread() {

                public void run() {

                    try {
                        ParseUser.logInInBackground(mEmail, mPassword, new LogInCallback() {
                            @Override
                            public void done(ParseUser parseUser, ParseException e) {
                                if (e == null){

                                    Toast.makeText(getApplicationContext(),getString(R.string.log_in_successfull), Toast.LENGTH_SHORT).show();
                                    Log.i(MSG,"Logged in");
                                }
                                else {

                                    switch (e.getCode()) {
                                        case ParseException.CONNECTION_FAILED: {
                                            Toast.makeText(getApplicationContext(),getString(R.string.connection_problems),Toast.LENGTH_LONG).show();
                                            break;
                                        }
                                        default: {
                                            // Something else went wrong
                                            Toast.makeText(getApplicationContext(),getString(R.string.log_in_unsuccessfull),Toast.LENGTH_LONG).show();
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }

                        });
                        SystemClock.sleep(600);
                        dialog.dismiss();
                    }catch (Exception ignored) {

                    }
                }
            }.start();
        }
        else
        {
            Toast.makeText(getApplicationContext(),getString(R.string.msg_blank_login), Toast.LENGTH_LONG).show();
        }

    }
    // ***********************************************************************************************************************************************************************



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_signin);
//        bar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent = getIntent();

        userEmail = (EditText) findViewById(R.id.useremailLogin);
        userPass = (EditText) findViewById(R.id.userpassLogin);
        //userEmail.setOnKeyListener(this);
        userPass.setOnKeyListener(this);
        logo = (ImageView) findViewById(R.id.logo);
        constraintLayout = (ConstraintLayout)findViewById(R.id.constraint_layout);
        logo.setOnClickListener(this);
        constraintLayout.setOnKeyListener(this);


    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}

