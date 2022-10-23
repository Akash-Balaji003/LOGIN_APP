package com.example.dsa;

import static com.example.dsa.R.id.Phone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Spinner spinner, spinner2;
    private String DOB;
    private String course;
    private String semester;
    private TextView dob_input;
    private EditText email_input, password_input, Name_input, Phone_input, Confirm_password;
    private Button register;

    ArrayList<String> arrayList_course, arrayList_btech, arrayList_mtech, arrayList_mbbs, arrayList_babl, arrayList_ba;
    ArrayAdapter<String> arrayAdapter_course, ArrayAdapter_semester;

    Pattern digitCasePatten = Pattern.compile("[0-9 ]");
    Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
    Pattern lowerCasePatten = Pattern.compile("[a-z ]");
    Pattern specialCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        dob_input = (TextView) findViewById(R.id.dob);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        email_input = (EditText) findViewById(R.id.Email);
        password_input = (EditText) findViewById(R.id.Password);
        register = (Button) findViewById(R.id.Register);
        Name_input = (EditText) findViewById(R.id.Name);
        Phone_input = (EditText) findViewById(R.id.Phone);
        Confirm_password = (EditText) findViewById(R.id.Confirm_password);

        arrayList_course = new ArrayList<>();
        arrayList_course.add("B.Tech");
        arrayList_course.add("M.Tech");
        arrayList_course.add("MBBS");
        arrayList_course.add("BABL");
        arrayList_course.add("BA");

        arrayAdapter_course = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_course);
        spinner2.setAdapter(arrayAdapter_course);

        arrayList_btech = new ArrayList<>();
        arrayList_btech.add("1");
        arrayList_btech.add("2");
        arrayList_btech.add("3");
        arrayList_btech.add("4");
        arrayList_btech.add("5");
        arrayList_btech.add("6");
        arrayList_btech.add("7");
        arrayList_btech.add("8");

        arrayList_mtech = new ArrayList<>();
        arrayList_mtech.add("1");
        arrayList_mtech.add("2");
        arrayList_mtech.add("3");
        arrayList_mtech.add("4");
        arrayList_mtech.add("5");
        arrayList_mtech.add("6");
        arrayList_mtech.add("7");
        arrayList_mtech.add("8");
        arrayList_mtech.add("9");
        arrayList_mtech.add("10");

        arrayList_babl = new ArrayList<>();
        arrayList_babl.add("1");
        arrayList_babl.add("2");
        arrayList_babl.add("3");
        arrayList_babl.add("4");
        arrayList_babl.add("5");
        arrayList_babl.add("6");
        arrayList_babl.add("7");
        arrayList_babl.add("8");
        arrayList_babl.add("9");
        arrayList_babl.add("10");

        arrayList_mbbs = new ArrayList<>();
        arrayList_mbbs.add("1");
        arrayList_mbbs.add("2");
        arrayList_mbbs.add("3");
        arrayList_mbbs.add("4");
        arrayList_mbbs.add("5");
        arrayList_mbbs.add("6");
        arrayList_mbbs.add("7");
        arrayList_mbbs.add("8");
        arrayList_mbbs.add("9");

        arrayList_ba = new ArrayList<>();
        arrayList_ba.add("1");
        arrayList_ba.add("2");
        arrayList_ba.add("3");
        arrayList_ba.add("4");
        arrayList_ba.add("5");
        arrayList_ba.add("6");

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    course = adapterView.getItemAtPosition(i).toString();
                    ArrayAdapter_semester = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_btech);
                }
                if(i==1){
                    course = adapterView.getItemAtPosition(i).toString();
                    ArrayAdapter_semester = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_mtech);
                }
                if(i==2){
                    course = adapterView.getItemAtPosition(i).toString();
                    ArrayAdapter_semester = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_mbbs);
                }
                if(i==3){
                    course = adapterView.getItemAtPosition(i).toString();
                    ArrayAdapter_semester = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_babl);
                }
                if(i==4){
                    course = adapterView.getItemAtPosition(i).toString();
                    ArrayAdapter_semester = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList_ba);
                }
                spinner.setAdapter(ArrayAdapter_semester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeredUser();
            }
        });

        dob_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                DOB = day + "/" + month + "/" + year;
                dob_input.setText(DOB);
            }
        };
    }
    private void registeredUser() {

        String email = email_input.getText().toString().trim();
        String semester = spinner.getSelectedItem().toString().trim();
        String password = password_input.getText().toString().trim();
        String confirm_password = Confirm_password.getText().toString().trim();
        String name = Name_input.getText().toString().trim();
        String phone = Phone_input.getText().toString().trim();

        if (name.isEmpty()) {
            Name_input.setError("Name is required");
            Name_input.requestFocus();
            return;
        }
        if (DOB.isEmpty()) {
            dob_input.setError("Enter your Date of Birth");
            return;
        }
        if (phone.isEmpty()) {
            Phone_input.setError("Phone number is required");
            Phone_input.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            email_input.setError("Email is required");
            email_input.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_input.setError("Please enter a valid Email Id");
            email_input.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            password_input.setError("Password is required");
            password_input.requestFocus();
            return;
        }
        if (password.length() < 6) {
            password_input.setError("Minimum password length should be 6 characters");
            password_input.requestFocus();
            return;
        }
        if (!digitCasePatten.matcher(password).find()) {
            password_input.setError("Password must have atleast one digit character");
            password_input.requestFocus();
            return;
        }
        if (!UpperCasePatten.matcher(password).find()) {
            password_input.setError("Password must have atleast one uppercase character");
            password_input.requestFocus();
            return;
        }
        if (!lowerCasePatten.matcher(password).find()) {
            password_input.setError("Password must have atleast one lowercase character");
            password_input.requestFocus();
            return;
        }
        if (!specialCharPatten.matcher(password).find()) {
            password_input.setError("Password must have atleast one special character");
            password_input.requestFocus();
            return;
        }
        if (confirm_password.isEmpty()) {
            Confirm_password.setError("Password is required");
            Confirm_password.requestFocus();
            return;
        }
        if (!confirm_password.equals(password)){
            Confirm_password.setError("Passwords does not match");
            Confirm_password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(name, course, semester, DOB ,phone, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "User registered", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }
}