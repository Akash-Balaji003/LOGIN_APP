package com.example.dsa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.example.dsa.User;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference users;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    Pattern digitCasePatten = Pattern.compile("[0-9 ]");
    Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
    Pattern lowerCasePatten = Pattern.compile("[a-z ]");
    Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();


        Button loginButton = (Button) findViewById(R.id.button);
        TextView register = (TextView) findViewById(R.id.textView4);
        TextView passwordReset = (TextView) findViewById(R.id.textView6);

        loginButton.setOnClickListener(this);
        register.setOnClickListener(this);
        passwordReset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView4:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
                
            case R.id.button:
                loginUser();
                break;

            case R.id.textView6:
                forgotPassword();
                break;
        }
    }

    private void forgotPassword() {
        startActivity(new Intent(MainActivity.this, ResetPassword.class));
    }

    private void loginUser() {
        EditText email = (EditText)findViewById(R.id.editTextTextEmailAddress);
        EditText password = (EditText)findViewById(R.id.editTextTextPassword);
        String email_login = email.getText().toString().trim();
        String password_login = password.getText().toString().trim();

        if (email_login.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_login).matches()) {
            email.setError("Please enter a valid Email Id");
            email.requestFocus();
            return;
        }
        if (password_login.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (password_login.length() < 6) {
            password.setError("Minimum password length should be 6 characters");
            password.requestFocus();
            return;
        }
        if (!digitCasePatten.matcher(password_login).find()) {
            password.setError("Password must have atleast one digit character !!");
            return;
        }
        if (!UpperCasePatten.matcher(password_login).find()) {
            password.setError("Password must have atleast one uppercase character !!");
            return;
        }
        if (!lowerCasePatten.matcher(password_login).find()) {
            password.setError("Password must have atleast one lowercase character !!");
            return;
        }
        if (!specialCharPatten.matcher(password_login).find()) {
            password.setError("Password must have atleast one special character !!");
            return;
        }


        mAuth.signInWithEmailAndPassword(email_login, password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, HomeActivity2.class));

                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your E-mail to verify your account", Toast.LENGTH_LONG).show();

                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to login! check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}