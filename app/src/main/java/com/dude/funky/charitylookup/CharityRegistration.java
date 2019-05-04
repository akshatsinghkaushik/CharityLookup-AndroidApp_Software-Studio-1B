package com.dude.funky.charitylookup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CharityRegistration extends AppCompatActivity {

    //Initialising widgets
    TextView charityName;
    TextView otherName;
    TextView password;
    TextView ABN;
    TextView email;
    TextView phoneNo;
    TextView charityWebsite;
    TextView dateEst;
    TextView charityPurpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Linking instances to widgets
        charityName = findViewById(R.id.charityRegistName);
        otherName = findViewById(R.id.charityRegistOtherName);
        password = findViewById(R.id.charityRegistPw);
        ABN = findViewById(R.id.charityRegistABN);
        email = findViewById(R.id.charityRegistEmail);
        phoneNo = findViewById(R.id.charityRegistPhoneNo);
        charityWebsite = findViewById(R.id.charityRegistWebsite);
        dateEst = findViewById(R.id.charityDateEst);
        charityPurpose = findViewById(R.id.charityRegistPurpose);
    }

    public void charityRegisterAction(View v) {
        //Getting Firebase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Making a new CharityInformation object where the new charity's info will be stored
        CharityInformation charityInfo = new CharityInformation(String.valueOf(charityName.getText()), String.valueOf(otherName.getText()),
                String.valueOf(password.getText()), String.valueOf(ABN.getText()), String.valueOf(email.getText()), String.valueOf(phoneNo.getText()),
                String.valueOf(charityWebsite.getText()), String.valueOf(dateEst.getText()), String.valueOf(charityPurpose.getText()));

        //Uploading the charity info to the Firebase database
        DatabaseReference mRef = database.getReference().child("Charities").child(String.valueOf(email.getText()));
        mRef.setValue(charityInfo);

        //Popup telling user that registration has been successful
        Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
    }
}