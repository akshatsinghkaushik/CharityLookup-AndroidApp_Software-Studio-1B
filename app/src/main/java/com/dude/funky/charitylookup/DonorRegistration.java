package com.dude.funky.charitylookup;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class DonorRegistration extends AppCompatActivity {

    //Initialisations
    TextView username;
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView emailWarning;
    TextView password;
    TextView pwWarning;
    TextView confirmPw;
    TextView confirmPwWarning;
    TextView phoneNo;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donor_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        //Linking instances to widgets
        username = findViewById(R.id.donorRegistUsername);
        firstName = findViewById(R.id.donorRegistFirstName);
        lastName = findViewById(R.id.donorRegistLastName);
        email = findViewById(R.id.donorRegistEmail);
        emailWarning = findViewById(R.id.donorRegistEmailWarning);
        password = findViewById(R.id.donorRegistPassword);
        pwWarning = findViewById(R.id.donorRegistPwWarning);
        confirmPw = findViewById(R.id.donorConfirmPw);
        confirmPwWarning = findViewById(R.id.donorConfirmPwWarning);
        phoneNo = findViewById(R.id.donorRegistPhoneNo);
    }

    public void donorRegisterAction(View v) {
        //Getting Firebase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //If the details satisfy all conditions, proceed with registration
        boolean strongPw = password.getText().length() >= 4;
        boolean pwMatch = String.valueOf(password.getText()).equals(String.valueOf(confirmPw.getText()));
        boolean correctEmailFormat = Pattern.compile("^([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)$").matcher(email.getText()).matches();

        if (strongPw && pwMatch && correctEmailFormat) {
                //Making a new DonorInformation object where the new user's info will be stored
                DonorInformation userInfo = new DonorInformation(String.valueOf(username.getText()), String.valueOf(firstName.getText()), String.valueOf(lastName.getText()),
                        EmailMethods.encodeEmail(String.valueOf(email.getText())), String.valueOf(password.getText()), String.valueOf(phoneNo.getText()));

            //Uploading the user info to the Firebase database
            DatabaseReference mRef = database.getReference().child("Donors").child(String.valueOf(username.getText()));
            mRef.setValue(userInfo);

            //Creates new user in database authentication instance
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Sign in is successful
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                            }
                            else {
                                //Sign in unsuccessful
                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


        }
        else if (!correctEmailFormat) {
            emailWarning.setVisibility(View.VISIBLE);
        }
        else if (!strongPw) {
            pwWarning.setVisibility(View.VISIBLE);
        }
        else if (!pwMatch) {
            confirmPwWarning.setVisibility(View.VISIBLE);
        }
    }

}
