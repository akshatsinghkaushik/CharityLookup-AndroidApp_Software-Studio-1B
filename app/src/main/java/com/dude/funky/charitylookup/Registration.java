package com.dude.funky.charitylookup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    //Initialising widgets
    TextView registrationTitle;
    TextView textName;
    TextView textEmail;
    TextView textUsername;
    TextView textPassword;
    Button confirmButton;

    String name;
    String username;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Linking instances to widgets
        registrationTitle = findViewById(R.id.textView);
        textName = findViewById(R.id.nameText);
        textUsername = findViewById(R.id.usernameText);
        textPassword = findViewById(R.id.passwordText);
        textEmail = findViewById(R.id.emailText);
        confirmButton = findViewById(R.id.confirmationButton);
    }

    public void RegistrationButtonOnClick(View v){
        Toast.makeText(getApplicationContext(),"you've Registered!", Toast.LENGTH_SHORT).show();


        //Inputting data into Firebase

        //Writing message to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");


        UserInformation userInformation = new UserInformation (String.valueOf(textEmail.getText()),String.valueOf(textName.getText()), String.valueOf(textPassword.getText()));
        registrationTitle.setText(userInformation.getName());
        DatabaseReference mRef = database.getReference().child("Donors").child(String.valueOf(textUsername.getText()));
        //mRef = ref.child("Donors");
        mRef.setValue(userInformation);

    }

}
