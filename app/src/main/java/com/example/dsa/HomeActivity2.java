package com.example.dsa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity2 extends AppCompatActivity {

    private Button logout;
    private String userID;
    private FirebaseUser user;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        logout = (Button) findViewById(R.id.button2);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView Name = (TextView) findViewById(R.id.name_display);
        final TextView dob = (TextView) findViewById(R.id.DOB_display);
        final TextView Course = (TextView) findViewById(R.id.course_display);
        final TextView sem = (TextView) findViewById(R.id.sem_display);
        final TextView number = (TextView) findViewById(R.id.phone_display);
        final TextView mail = (TextView) findViewById(R.id.email_display);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity2.this, MainActivity.class));
            }
        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String name = userProfile.name;
                    String DOB = userProfile.DOB;
                    String course = userProfile.course;
                    String Sem = userProfile.semester;
                    String Phone = userProfile.phone;
                    String Email = userProfile.email;
                    Name.setText(name);
                    dob.setText(DOB);
                    Course.setText(course);
                    sem.setText("Semester "+Sem);
                    number.setText(Phone);
                    mail.setText(Email);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity2.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}