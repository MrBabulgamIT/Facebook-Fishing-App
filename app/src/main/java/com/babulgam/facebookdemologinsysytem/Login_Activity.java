package com.babulgam.facebookdemologinsysytem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {



    private EditText numberin,passwodin;
    Button loginButton;
    private TextView gotosingup,forgetpassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String firstname,surname,email,password,gender,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        numberin=findViewById(R.id.email_edittext_id);
        passwodin=findViewById(R.id.password_edittext_id);
        loginButton=findViewById(R.id.button_sign_in_id);
        gotosingup=findViewById(R.id.goto_signUP_textview_id);
        progressBar=findViewById(R.id.progress_bar);
        forgetpassword=findViewById(R.id.forgetButton_id);

        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(this);
        gotosingup.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Login");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.button_sign_in_id:

                loginUserAccount();


                break;

            case R.id.goto_signUP_textview_id:
                Intent intent=new Intent(getApplicationContext(),Signup_layout.class);
                startActivity(intent);
                Toast.makeText(this, "Please Filip this From", Toast.LENGTH_SHORT).show();

                break;
            case R.id.forgetButton_id:
                startActivity(new Intent(getApplicationContext(),Forget_password.class));
                break;

        }


    }

    private void loginUserAccount()
    {

        // show the visibility of progress bar to show loading




        email = numberin.getText().toString();
        password = passwodin.getText().toString();

        // validations for input email and password

        if (email.isEmpty()) {
            numberin.setError("Please enter Email !!");
            numberin.requestFocus();
            return;
        }
        else
        if (email.length() < 11) {
            numberin.setError("Enter a valid email Or Number ");
            numberin.requestFocus();
            return;

        }else
        if (password.isEmpty()) {
            passwodin.setError("Please enter password !!");
            passwodin.requestFocus();
            return;
        }
        else
        if (password.length() < 6) {
            passwodin.setError("Enter a valid password ");
            passwodin.requestFocus();
            return;

        }
        String key= databaseReference.push().getKey();
        UserRegistration users=new UserRegistration(firstname,surname,email,password,gender,date);
        databaseReference.child(key).setValue(users);
            progressBar.setVisibility(View.VISIBLE);



            // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {

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
                                    Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);

                                    // if sign-in is successful
                                    // intent to home activity

                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                    numberin.requestFocus();

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

    }


    private void gotoUrl(String s) {

        Uri uri=Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}