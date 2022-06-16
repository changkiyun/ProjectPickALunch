package com.example.projectpickalunch.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.projectpickalunch.Main.MainActivity;
import com.example.projectpickalunch.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class Google_Login extends AppCompatActivity {
    private FirebaseAuth mAuth = null;
    private FirebaseDatabase mDatabase;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_login);

        ImageView ivGlide = (ImageView)findViewById(R.id.iv_glide);
        signInButton = findViewById(R.id.signInButton);
        mDatabase = FirebaseDatabase.getInstance();

        Glide.with(this)
                .load("http://goo.gl/gEgYUd")
                .override(300, 200)
                .fitCenter()
                .into(ivGlide);

        mAuth = FirebaseAuth.getInstance();

        //로그인 성공시 이후에는 로그인창을 띄우지 않음
        //if (mAuth.getCurrentUser() != null) {
        //    Intent intent = new Intent(getApplication(), MainActivity.class);
         //   startActivity(intent);
         //   finish();
        //}
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }
    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Snackbar.make(findViewById(R.id.layout_main), "Authentication Successed.", Snackbar.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            final String uid = task.getResult().getUser().getUid();
                            UserModel userModel = new UserModel();
                            userModel.uid = uid;

                            // database에 저장
                            mDatabase.getReference().child("users").child(uid).setValue(userModel);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Snackbar.make(findViewById(R.id.layout_main), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) { //update ui code here
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}


