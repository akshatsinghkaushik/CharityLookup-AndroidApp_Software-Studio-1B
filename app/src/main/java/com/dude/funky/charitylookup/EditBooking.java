package com.dude.funky.charitylookup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dude.funky.charitylookup.Account.LoginActivity;
import com.dude.funky.charitylookup.Account.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditBooking extends AppCompatActivity {
        private EditText inputItem, inputDate, inputTime;
        private Button btnSave, btnResetPassword;
        private ProgressBar progressBar;
        private FirebaseAuth auth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.edit_booking);

            //Get Firebase auth instance
            auth = FirebaseAuth.getInstance();

            //btnSignIn = (Button) findViewById(R.id.);
            btnSave = (Button) findViewById(R.id.save);
            inputItem = (EditText) findViewById(R.id.item);
            inputDate = (EditText) findViewById(R.id.date);
            inputTime = (EditText) findViewById(R.id.time);
            progressBar = (ProgressBar) findViewById(R.id.LoginProgressSignUp);
            //btnResetPassword = (Button) findViewById(R.id.ForgotPassSignIn);

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            /**btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
            }
            });*/

            /**btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
            });*/


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = inputItem.getText().toString().trim();
                    String name_user = inputDate.getText().toString().trim();
                    String password = inputTime.getText().toString().trim();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(name_user)) {
                        Toast.makeText(getApplicationContext(), "Enter Display NAme!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    btnSave.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    //create user
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(EditBooking.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(EditBooking.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    btnSave.setVisibility(View.VISIBLE);

                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(EditBooking.this, "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {

                                        FirebaseUser user = auth.getCurrentUser();

                                        user.sendEmailVerification();

                                        String token = auth.getCurrentUser().getUid();


                                        // Create a new user with a first and last name
                                        Map<String, Object> donor = new HashMap<>();
                                        donor.put("uid", token);
                                        donor.put("name", name_user);
                                        donor.put("email", email);

                                        // Add a new document with a generated ID
                                        db.collection("Bookings")
                                                .add(donor)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w("TAG", "Error adding document", e);
                                                    }
                                                });



                                        startActivity(new Intent(EditBooking.this, LoginActivity.class));
                                        finish();
                                    }
                                }
                            });

                }
            });

        }

        @Override
        protected void onResume() {
            super.onResume();
            progressBar.setVisibility(View.GONE);
            btnSave.setVisibility(View.VISIBLE);
        }
}