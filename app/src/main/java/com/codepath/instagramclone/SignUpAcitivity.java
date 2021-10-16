package com.codepath.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUpAcitivity extends AppCompatActivity {

    public static final String TAG = "SignUpAcitivity";

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private Button btnSignUp;
    private TextView tvUsernameCheck;
    private TextView tvPasswordCheck;
    private TextView tvEmailCheck;
    private TextView tvSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivity);
        etUsername = findViewById(R.id.etUsername_SignUp);
        etPassword = findViewById(R.id.etPassword_SignUp);
        etEmail = findViewById(R.id.etEmail_SignUp);
        btnSignUp = findViewById(R.id.btnSignUp_SignUp);
        tvUsernameCheck = findViewById(R.id.tvUsernameCheck);
        tvPasswordCheck = findViewById(R.id.tvPassworkCheck);
        tvEmailCheck = findViewById(R.id.tvEmailCheck);
        tvSuccessful = findViewById(R.id.tvSuccessful);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvUsernameCheck.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                tvPasswordCheck.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                tvEmailCheck.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String email= etEmail.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(SignUpAcitivity.this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                    tvUsernameCheck.setText("Username cannot be empty");
                    tvUsernameCheck.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.medium_red));
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(SignUpAcitivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    tvPasswordCheck.setText("Password cannot be empty");
                    tvPasswordCheck.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.medium_red));
                    return;
                }
                if (email.isEmpty()) {
                    Toast.makeText(SignUpAcitivity.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    tvEmailCheck.setText("Email cannot be empty");
                    tvEmailCheck.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.medium_red));
                    return;
                }
                createNewUser(username, password, email);
            }
        });
    }

    private void createNewUser(String username, String password, String email) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if( e != null){
                    Log.e(TAG, "Issue with Signing up", e);
                    tvUsernameCheck.setText("Account already exist for this username");
                    tvUsernameCheck.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.medium_red));
                    return;
                }
                Log.i(TAG, "New user save was successful!!");

                tvSuccessful.setText("Sign up successful! Go to Log in page ->");
                tvSuccessful.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_200));
                tvSuccessful.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goLoginActivity();
                    }
                });
            }
        });
    }

    private void goLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        ParseUser.logOut();

    }

}