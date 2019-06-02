package com.dude.funky.charitylookup.Account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dude.funky.charitylookup.MainActivity;
import com.dude.funky.charitylookup.R;
import com.dude.funky.charitylookup.View.Main_main;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class LoginActivity extends AppCompatActivity {

    RelativeLayout rellay1, rellay2;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(VISIBLE);
            rellay2.setVisibility(VISIBLE);
        }
    };


    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword, btn_login, charity_login;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    static final int GOOGLE_SIGN = 123;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        charity_login = findViewById(R.id.charity_login);

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 1200);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        /**if (auth.getCurrentUser() != null) {
            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }*/

        //setContentView(R.layout.activity_login);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        btn_login.setOnClickListener(v->SignInGoogle());

        charity_login.setOnClickListener(v->launchCharityLogin());



        inputEmail = (EditText) findViewById(R.id.EmailSignIn);
        inputPassword = (EditText) findViewById(R.id.PasswordSignIn);
        progressBar = (ProgressBar) findViewById(R.id.LoginProgressSignIn);
        btnSignUp = (Button) findViewById(R.id.SignUpSignIn);
        btnSignIn = (Button) findViewById(R.id.LoginButtonSignIn);
        btnResetPassword = (Button) findViewById(R.id.ForgotPassSignIn);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(VISIBLE);
                btnSignIn.setVisibility(GONE);
                btn_login.setVisibility(GONE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(GONE);
                                btn_login.setVisibility(VISIBLE);
                                btnSignIn.setVisibility(VISIBLE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError("Error");
                                        // inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, ("Failed"), Toast.LENGTH_LONG).show();//getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, Main_main.class);
                                    Log.w("TAG",email);
                                    intent.putExtra("donor_email", email );
                                    startActivity(intent);
                                    //finish();
                                }
                            }
                        });
            }
        });



    }

    void SignInGoogle() {
        progressBar.setVisibility(VISIBLE);
        btnSignIn.setVisibility(GONE);
        btn_login.setVisibility(GONE);
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        signIntent.putExtra("donor_email", auth.getCurrentUser().getEmail() );
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    void launchCharityLogin(){
        Intent intent = new Intent(LoginActivity.this, CharityLogin.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN);
        Task<GoogleSignInAccount> task = GoogleSignIn
                .getSignedInAccountFromIntent(data);

        try {

            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account!=null) firebaseAuthWithGoogle(account);
            //make request firebase

        }   catch (ApiException e){
            e.printStackTrace();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        Log.d("TAG","firebaseAuthWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("TAG","signin success");

                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.w("TAG","signin failure", task.getException());

                        Toast.makeText(this,"SignIn Failed!", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {

            //String name = user.getDisplayName();
            //String email = user.getEmail();
            //String photo = String.valueOf(user.getPhotoUrl());

            //text.setText("Info: \n");
            //text.append(name + "\n");
            //text.append(email);


            //Picasso.get().load(photo).into(image);
            btn_login.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(LoginActivity.this, Main_main.class);
            intent.putExtra("donor_email", auth.getCurrentUser().getEmail() );
            startActivity(intent);

            //btn_logout.setVisibility(View.VISIBLE);

        }   else {

            //text.setText(getString(R.string.firebase_login));
            //image.setImageResource(R.drawable.ic_firebase_logo);
            // Picasso.get().load(R.drawable.ic_firebase_logo).into(image);
            btn_login.setVisibility(VISIBLE);
            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            intent.putExtra("donor_email", auth.getCurrentUser().getEmail() );
            startActivity(intent);
            //btn_logout.setVisibility(View.INVISIBLE);


        }
    }

}
