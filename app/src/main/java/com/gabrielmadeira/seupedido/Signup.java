package com.gabrielmadeira.seupedido;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class Signup extends AppCompatActivity implements View.OnKeyListener{
    EditText userEmailSignup;
    EditText userPassSignup;
    EditText userPassConfirmationSignup;
    EditText userName;
    EditText userSurname;
    String mEmail;
    String mPassword;
    String mPasswordConfirmation;
    String mName;
    String mSurname;
    private ProgressDialog dialog;
    ParseUser user;

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        if (i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == keyEvent.ACTION_DOWN){
            signup(view);
        }
        return false;
    }

    // ***********************************************************************************************************************************************************************
    // Hide keyboard after inputing pass and email to show progressbar. ENTER KEY = Signup after pass confirmation


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


    public void signinActivity(View view){
        Intent intent = new Intent(this,Signin.class);

        startActivity(intent);
    }
    public void signup(View view) {
        mName = userName.getText().toString().trim();
        mSurname = userSurname.getText().toString().trim();
        mEmail = userEmailSignup.getText().toString().trim();
        mPassword = userPassSignup.getText().toString().trim();
        mPasswordConfirmation = userPassConfirmationSignup.getText().toString().trim();

        Log.d("AppInfo", mPassword);
        Log.d("AppInfo", mPasswordConfirmation);

        if (mEmail.isEmpty() || mPassword.isEmpty() || mPasswordConfirmation.isEmpty() || mName.isEmpty() || mSurname.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.signup_fields_empty), Toast.LENGTH_LONG).show();
            Log.d("AppInfo", "Signup fields empty");
        } else {
            if (!mPassword.equals(mPasswordConfirmation)) {
                Toast.makeText(getApplicationContext(), getString(R.string.password_mismatch), Toast.LENGTH_LONG).show();
            } else
            {
                Log.d("AppInfo", "Signup fields ok");
                dialog = new ProgressDialog(Signup.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog = ProgressDialog.show(Signup.this,getString(R.string.signup_dialog_msg),getString(R.string.signup_dialog_msg), false, true);
                dialog.setIcon(R.drawable.ic_email_black_24dp);
                dialog.setCancelable(false);
                user = new ParseUser();
                user.setUsername(mEmail);
                user.setEmail(mEmail);
                user.setPassword(mPassword);
                user.put("name",mName);
                user.put("surname",mSurname);
//            new ProgressTask().execute();
                new Thread() {

                    public void run() {

                        try {
                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null){

                                        Toast.makeText(getApplicationContext(),getString(R.string.sign_up_successfull), Toast.LENGTH_SHORT).show();
                                    }
                                    else {

                                        switch (e.getCode()) {
                                            case ParseException.USERNAME_TAKEN: {
                                                // report error
                                                Toast.makeText(getApplicationContext(),getString(R.string.email_already_taken),Toast.LENGTH_LONG).show();
                                                break;
                                            }
                                            case ParseException.EMAIL_TAKEN: {
                                                // report error
                                                break;
                                            }
                                            default: {
                                                // Something else went wrong
                                                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                }

                            });
                            SystemClock.sleep(600);
                            dialog.dismiss();
                        }catch (Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();

                        }
                    }
                }.start();
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Intent intent = getIntent();
        userEmailSignup = (EditText) findViewById(R.id.signup_userEmail);
        userPassSignup = (EditText) findViewById(R.id.signup_userPassword);
        userPassConfirmationSignup = (EditText) findViewById(R.id.signup_userPasswordconfirmation);
        userName = (EditText)findViewById(R.id.signup_userName);
        userSurname = (EditText)findViewById(R.id.signup_userSurname);

        userPassSignup.setOnKeyListener(this);

    }



}
