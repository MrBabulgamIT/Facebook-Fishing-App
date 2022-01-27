package com.babulgam.facebookdemologinsysytem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_layout extends AppCompatActivity  implements View.OnClickListener {

    private Button singUpbutton;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;
    private EditText firstnameET, surnameET, emailETUP,passwordETUP;
    private TextView gotologin;
    private ProgressBar progressBar;
    String gender,date, email, password,surname,firstname,key;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button datebutton;
    private TextView dateresulttextview;
    private DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay, mHour, mMinute;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        emailETUP = findViewById(R.id.email_edittext_id_singup);
        passwordETUP = findViewById(R.id.password_edittext_id_singup);
        singUpbutton = findViewById(R.id.signUp_Button_id);
        progressbar = findViewById(R.id.progress_bar_id);
        firstnameET=findViewById(R.id.firstName_edittext_id_singup);
        surnameET=findViewById(R.id.surname_edittext_id_singup);
        radioGroup=findViewById(R.id.radio_Group_id);
        gotologin=findViewById(R.id.goto_singIN_id);
        datebutton=findViewById(R.id.dateButton_id);
        dateresulttextview=findViewById(R.id.dateTextview_id);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        singUpbutton.setOnClickListener(this);
        gotologin.setOnClickListener(this);
        radioGroup.setOnClickListener(this);
        datebutton.setOnClickListener(this);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = radioGroup.findViewById(checkedId);

                switch (checkedId) {
                    case R.id.maleButton_Id:

                        gender = radioButton.getText().toString();

                        break;
                    case R.id.femaleButton_Id:

                        gender = radioButton.getText().toString();

                        break;
                    case R.id.customButton_Id:

                        gender = radioButton.getText().toString();

                        break;


                }
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signUp_Button_id:

                registerNewUser();

                break;

            case R.id.goto_singIN_id:

                Intent intent=new Intent(getApplicationContext(),Login_Activity.class);
                startActivity(intent);
                break;


            case R.id.dateButton_id:
                DatePicker datePicker=new DatePicker(this);
                int currentDay=datePicker.getDayOfMonth();
                int currentMonth=(datePicker.getMonth())+1;
                int currentYear=datePicker.getYear();

                datePickerDialog=new DatePickerDialog(this,

                        new  DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                date=""+dayOfMonth+"/"+(month+1)+"/"+year;
                                dateresulttextview.setText(date);

                            }
                        },currentYear,currentMonth,currentDay);

                datePickerDialog.show();
                break;


        }

    }

    private void registerNewUser()
    {

        surname = surnameET.getText().toString().trim();
        firstname = firstnameET.getText().toString().trim();
        email = emailETUP.getText().toString().trim();
        password = passwordETUP.getText().toString().trim();

        // Validations for input details
        if (firstname.isEmpty()) {
            firstnameET.setError("Please enter Firstname!!");
            firstnameET.requestFocus();
            return;
        }
        else
        if (surname.isEmpty()) {
            surnameET.setError("Please enter Surname!!");
            surnameET.requestFocus();
            return;
        }
        else

        if (email.isEmpty()) {
            emailETUP.setError("Please enter email!!");
            emailETUP.requestFocus();
            return;
        }
        else
        if (email.length() < 11) {

            emailETUP.setError("Please enter valid mail");
            emailETUP.requestFocus();
            return;

        }
        else
        if (password.isEmpty()) {
            passwordETUP.setError("Please enter password!!");
            passwordETUP.requestFocus();
            return;
        }
        else
        if (password.length() < 6) {
            passwordETUP.setError("Minimum length of a password should be 6");
            passwordETUP.requestFocus();
            return;

        }else


        progressbar.setVisibility(View.VISIBLE);
        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        if (task.isSuccessful()) {
                            key= databaseReference.push().getKey();
                            UserRegistration users=new UserRegistration(firstname,surname,email,password,gender,date);
                            databaseReference.child(key).setValue(users);

                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);

                            // if the user created intent to login activity
                            Intent intent =getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                            if (intent !=null)
                            {
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"Register is Successful",Toast.LENGTH_SHORT).show();


                            }else
                            {

                                gotoUrl("https://play.google.com/store/apps/details?id=com.facebook.katana&hl=en&gl=US");
                                Toast.makeText(getApplicationContext(), "Please Install This Apps", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void gotoUrl(String s) {

        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

}
