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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private static final String PREF_KEY = Objects.requireNonNull(MainActivity.class.getPackage()).toString();
    private static final int SECRET_KEY = 99;


    EditText userNameEdittext;
    EditText userEmailEdittext;
    EditText userpasswordEdittext;
    EditText userpasswordconfirmEdittext;

    private FirebaseFirestore mStore;
    private CollectionReference mItems;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);
        if(secret_key != 99)
            finish();

        userNameEdittext = findViewById(R.id.usernameTxt);
        userEmailEdittext = findViewById(R.id.EmailTxt);
        userpasswordEdittext = findViewById(R.id.passwordTxt);
        userpasswordconfirmEdittext = findViewById(R.id.passwordTxt2);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");
        userEmailEdittext.setText(email);
        userpasswordEdittext.setText(password);


        mStore = FirebaseFirestore.getInstance();
        mItems = mStore.collection("Customers");
        mAuth = FirebaseAuth.getInstance();

    }

    public void register(View view) {
        String userName = userNameEdittext.getText().toString();
        String email = userEmailEdittext.getText().toString();
        String password = userpasswordEdittext.getText().toString();
        String passwordconfirm = userpasswordconfirmEdittext.getText().toString();


        if(password.equals(passwordconfirm)) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        mItems.add(new Customer(userName,email,"","",0));
                        Toast.makeText(RegisterActivity.this, "User created succesfully", Toast.LENGTH_LONG).show();
                        startListCustomersActivity(userName);
                    } else {
                        Toast.makeText(RegisterActivity.this, "User wasn't created succesfully", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    public void startListCustomersActivity(String name){
        Intent intent = new Intent(this, CustomerActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}