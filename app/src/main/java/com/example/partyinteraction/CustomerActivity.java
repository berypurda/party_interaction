package com.example.partyinteraction;

import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firestore.bundle.NamedQuery;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {

    private FirebaseFirestore mFirestore;
    private CollectionReference mCollection;
    private Customer customer;
    private String id;

    ImageView image;
    EditText nameTxt;
    EditText gender;
    EditText description;
    EditText emailTxt;
    String name;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        name = getIntent().getExtras().getString("name");
        email = getIntent().getExtras().getString("email");

        if (name != null) {
            mFirestore = FirebaseFirestore.getInstance();
            mCollection = mFirestore.collection("Customers");

            mCollection.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    Customer temp = document.toObject(Customer.class);
                    if(name.equals(temp.getName())) {
                        customer = document.toObject(Customer.class);
                        id = document.getId();
                    }
                }
                Toast.makeText(CustomerActivity.this, id, Toast.LENGTH_LONG).show();
                image = findViewById(R.id.imageView);
                nameTxt = findViewById(R.id.nameTxt);
                gender = findViewById(R.id.genderTxt);
                description = findViewById(R.id.descTxt);
                emailTxt = findViewById(R.id.emailTxt);
                nameTxt.setText(customer.getName());
                description.setText(customer.getInfo());
                gender.setText(customer.getGender());
                Glide.with(this).load(customer.getImageresource()).into(image);
            });
        } else if(email != null) {
            mFirestore = FirebaseFirestore.getInstance();
            mCollection = mFirestore.collection("Customers");

            mCollection.orderBy("name").get().addOnSuccessListener(queryDocumentSnapshots -> {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Customer temp = document.toObject(Customer.class);
                    if (email.equals(temp.getEmail())) {
                        customer = document.toObject(Customer.class);
                        id = document.getId();
                    }
                }
                Toast.makeText(CustomerActivity.this, id, Toast.LENGTH_LONG).show();
                image = findViewById(R.id.imageView);
                nameTxt = findViewById(R.id.nameTxt);
                gender = findViewById(R.id.genderTxt);
                description = findViewById(R.id.descTxt);
                emailTxt = findViewById(R.id.emailTxt);
                nameTxt.setText(customer.getName());
                description.setText(customer.getInfo());
                gender.setText(customer.getGender());
                Glide.with(this).load(customer.getImageresource()).into(image);
            });
        }

    }

    public void updateData(View view){
        mFirestore.collection("Customers").document(id).update("gender", gender.getText().toString(),
                                                                            "imageresource", customer.getImageresource(),
                                                                            "info", description.getText().toString(),
                                                                            "name", nameTxt.getText().toString());
    }

    public void deleteData(View view){
        mFirestore.collection("Customers").document(id).delete();
    }

}
