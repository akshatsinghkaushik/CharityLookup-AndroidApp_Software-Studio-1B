package com.dude.funky.charitylookup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CharityRegistration extends AppCompatActivity {

    //Initialising widgets
    TextView username;
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView password;
    TextView phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Linking instances to widgets
        username = findViewById(R.id.donorRegistUsername);
        firstName = findViewById(R.id.donorRegistFirstName);
        lastName = findViewById(R.id.donorRegistLastName);
        email = findViewById(R.id.donorRegistEmail);
        password = findViewById(R.id.donorRegistPassword);
        phoneNo = findViewById(R.id.donorRegistPhoneNo);
    }

    public void registerAction(View v) {
        //Getting Firebase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Making a new DonorInformation object where the new user's info will be stored
        DonorInformation userInfo = new DonorInformation(String.valueOf(username.getText()), String.valueOf(firstName.getText()), String.valueOf(lastName.getText()),
                String.valueOf(email.getText()), String.valueOf(password.getText()), String.valueOf(phoneNo.getText()));

        //Uploading the user info to the Firebase database
        DatabaseReference mRef = database.getReference().child("Donors1").child(String.valueOf(username.getText()));
        mRef.setValue(userInfo);

        //Popup telling user that registration has been successful
        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
    }
}