package com.example.firebaseauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText edUserEmail, edUserPassword;
    TextView tvSignUp;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Firebase things :)
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        /*Link the .xml file with the .java file*/
        btnLogin = (Button) findViewById(R.id.btnLogin);
        edUserEmail = (EditText)findViewById(R.id.userEmail);
        edUserPassword = (EditText)findViewById(R.id.userPassword);
        tvSignUp = (TextView)findViewById(R.id.startRegistrationActivity);
        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            userLogin();
        }
        if(v == tvSignUp){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    private void userLogin() {

        String email = edUserEmail.getText().toString().trim();
        String password = edUserPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Email cannot be empty.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"Password cannot be empty.",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Getting started...");
        progressDialog.show();

        //Firebase stuff
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


                        }

                    }
                });
    }
}
