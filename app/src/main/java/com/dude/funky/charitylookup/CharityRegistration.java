package com.dude.funky.charitylookup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class CharityRegistration extends AppCompatActivity {

    //Initialising widgets
    ScrollView scrollView;
    TextView charityName;
    TextView otherName;
    TextView password;
    TextView passwordWarning;
    TextView confirmPassword;
    TextView confirmPwWarning;
    TextView ABN;
    TextView email;
    TextView emailWarning;
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
        scrollView = findViewById(R.id.ScrollVieww);
        charityName = findViewById(R.id.charityRegistName);
        otherName = findViewById(R.id.charityRegistOtherName);
        password = findViewById(R.id.charityRegistPw);
        passwordWarning = findViewById(R.id.pwWarning);
        confirmPassword = findViewById(R.id.charityConfirmPw);
        confirmPwWarning = findViewById(R.id.confirmPwWarning);
        ABN = findViewById(R.id.charityRegistABN);
        email = findViewById(R.id.charityRegistEmail);
        emailWarning = findViewById(R.id.charityEmailWarning);
        phoneNo = findViewById(R.id.charityRegistPhoneNo);
        charityWebsite = findViewById(R.id.charityRegistWebsite);
        dateEst = findViewById(R.id.charityDateEst);
        charityPurpose = findViewById(R.id.charityRegistPurpose);
    }

    public void charityRegisterAction(View v) {
        //Getting Firebase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //If the details satisfy all conditions, proceed with registration

        boolean strongPw = password.getText().length() >= 4;
        boolean pwMatch = String.valueOf(password.getText()).equals(String.valueOf(confirmPassword.getText()));
        boolean correctEmailFormat = Pattern.compile("^([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)$").matcher(email.getText()).matches();

        if (strongPw && pwMatch && correctEmailFormat) {
            //Making a new CharityInformation object where the new charity's info will be stored
            CharityInformation charityInfo = new CharityInformation(String.valueOf(charityName.getText()), String.valueOf(otherName.getText()),
                    String.valueOf(password.getText()), String.valueOf(ABN.getText()), EmailMethods.encodeEmail(String.valueOf(email.getText())),
                    String.valueOf(phoneNo.getText()), String.valueOf(charityWebsite.getText()), String.valueOf(dateEst.getText()),
                    String.valueOf(charityPurpose.getText()));

            //Uploading the charity info to the Firebase database
            DatabaseReference mRef = database.getReference().child("Charities").child(EmailMethods.encodeEmail(String.valueOf(email.getText())));
            mRef.setValue(charityInfo);

            //Popup telling user that registration has been successful
            Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
        }
        else if (!correctEmailFormat) {
            emailWarning.setVisibility(View.VISIBLE);
        }
        else if (!strongPw) {
            passwordWarning.setVisibility(View.VISIBLE);
        }
        else if (!pwMatch) {
            confirmPwWarning.setVisibility(View.VISIBLE);
        }
    }
}