package com.example.dsa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText Reset_mail;
    private Button Reset_button;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Reset_mail = (EditText) findViewById(R.id.Reset_email);
        Reset_button = (Button) findViewById(R.id.Reset_password);

        auth = FirebaseAuth.getInstance();

        Reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }

            private void resetPassword() {
                String email = Reset_mail.getText().toString().trim();

                if (email.isEmpty()) {
                    Reset_mail.setError("Email is required");
                    Reset_mail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Reset_mail.setError("Please enter a valid Email Id");
                    Reset_mail.requestFocus();
                    return;
                }

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPassword.this, "Check your E-mail to reset your password", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ResetPassword.this, "Failed to reset password try again", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

    }
}