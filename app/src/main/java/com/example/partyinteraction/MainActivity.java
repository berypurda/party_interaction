package com.example.partyinteraction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int SECRET_KEY = 99;
    private static final String PREF_KEY = Objects.requireNonNull(MainActivity.class.getPackage()).toString();

    private FirebaseAuth mAuth;

    EditText emailET;
    EditText passwordET;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailET = findViewById(R.id.EmailTxt);
        passwordET = findViewById(R.id.passwordTxt);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

    }

    public void login(View view) {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    if (mAuth.getCurrentUser().getEmail().equals("pistike@gmail.com")){
                        startListCustomersActivity();
                    } else {
                        startCustomerActivity(mAuth.getCurrentUser().getEmail());
                    }
                    Toast.makeText(MainActivity.this, "Logged in succesfully", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
        Intent intent = new Intent(this, ListCustomersActivity.class);
        startActivity(intent);
    }

    public void startListCustomersActivity(){
        Intent intent = new Intent(this, ListCustomersActivity.class);
        startActivity(intent);
    }

    public void startCustomerActivity(String email){
        Intent intent = new Intent(this, ListCustomersActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", emailET.getText().toString());
        editor.putString("password", passwordET.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loginWithGoogle(View view) {

    }
}