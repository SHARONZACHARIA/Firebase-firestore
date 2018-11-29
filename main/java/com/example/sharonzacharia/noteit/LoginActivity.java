package com.example.sharonzacharia.noteit;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {

   MaterialButton signbtn;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    private static final int RC_SIGN_IN = 123;
    static  String USER_ID="";
    static  String USER_NAME="";
    static  String USER_IMG = "";
    static String USER_EMAIL ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signbtn = findViewById(R.id.sign_in);

         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
         gsc =  GoogleSignIn.getClient(this,gso);



        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }





    public void signIn()
    {
        Toast.makeText(getApplicationContext(),"initiating....",Toast.LENGTH_LONG).show();
        Intent signInINtent = gsc.getSignInIntent();
        startActivityForResult(signInINtent,RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);


        if(googleSignInAccount!=null)
        { USER_NAME = googleSignInAccount.getDisplayName();
            USER_ID =googleSignInAccount.getEmail();
            USER_IMG = String.valueOf( googleSignInAccount.getPhotoUrl());
            USER_EMAIL = googleSignInAccount.getEmail();
            updateUi(googleSignInAccount);

            Toast.makeText(getApplicationContext(),USER_IMG,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                USER_ID = account.getEmail();
                USER_NAME = account.getDisplayName();
                USER_IMG = String.valueOf(account.getPhotoUrl());
                USER_EMAIL = account.getEmail();

                updateUi(account);




            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUi(GoogleSignInAccount a)
    {
        Intent NotesListIntent = new Intent (this,NotesListActivity.class);

        startActivity(NotesListIntent);
    }

}
