package com.technomads.citygo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    private EditText editText_logIn_email, getEditText_logIn_password;
    private FirebaseAuth mAuth;
    private Button bStart, bSignUp, bSignIn, bOut;
    private TextView tvUserName;
    private ImageView logo;
    private LinearLayout log_reg_linear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        init();
        bStart.setOnClickListener(this::onClickStart);
        bSignUp.setOnClickListener(this::onClickSignUp);
        bSignIn.setOnClickListener(this::onClickSignIn);
        bOut.setOnClickListener(this::onClickSignOut);
    }
    private void init()
    {
        log_reg_linear = findViewById(R.id.linear_log_reg);
        logo = findViewById(R.id.logo);
        bOut = findViewById(R.id.bOut);
        bSignIn = findViewById(R.id.logIn_btn);
        bSignUp = findViewById(R.id.register_btn);
        tvUserName = findViewById(R.id.tvUserEmail);
        bStart = findViewById(R.id.bStart);
        editText_logIn_email = findViewById(R.id.editText_logIn_email);
        getEditText_logIn_password = findViewById(R.id.editText_logIn_password);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if(cUser != null)
        {
            String userName = "Signed in as "+cUser.getEmail();
            tvUserName.setText(userName);
            showSignedIn();


            Toast.makeText(this, "User not null", Toast.LENGTH_SHORT).show();
        }
        else
        {
            showSignedOut();

            Toast.makeText(this, "User null", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSignUp(View view)
    {
        if(!TextUtils.isEmpty(editText_logIn_email.getText().toString()) && !TextUtils.isEmpty(getEditText_logIn_password.getText().toString()))
        {
            mAuth.createUserWithEmailAndPassword(editText_logIn_email.getText().toString(),getEditText_logIn_password.getText().toString()).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    sendEmailVer();
                    showSignedIn();
                    Toast.makeText(getApplicationContext(), "User SignUp success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    showSignedOut();
                    Toast.makeText(getApplicationContext(), "User SignUp failed", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void onClickSignIn(View view)
    {
        if(!TextUtils.isEmpty(editText_logIn_email.getText().toString()) && !TextUtils.isEmpty(getEditText_logIn_password.getText().toString()))
        {
            mAuth.signInWithEmailAndPassword(editText_logIn_email.getText().toString(),getEditText_logIn_password.getText().toString()).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    showSignedIn();
                    Toast.makeText(getApplicationContext(), "User SignIn success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    showSignedOut();
                    Toast.makeText(getApplicationContext(), "User SignIn failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void onClickSignOut(View view)
    {
        FirebaseAuth.getInstance().signOut();
        showSignedOut();
    }

    private void showSignedOut()
    {
        log_reg_linear.setVisibility(View.VISIBLE);
        logo.setVisibility(View.VISIBLE);
        editText_logIn_email.setVisibility(View.VISIBLE);
        getEditText_logIn_password.setVisibility(View.VISIBLE);
        bSignIn.setVisibility(View.VISIBLE);
        bSignUp.setVisibility(View.VISIBLE);
        bStart.setVisibility(View.GONE);
        tvUserName.setVisibility(View.GONE);
        bOut.setVisibility(View.GONE);
    }

    private void showSignedIn()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        if(user.isEmailVerified())
        {
            String userName = "Signed in as "+user.getEmail();
            tvUserName.setText(userName);
            log_reg_linear.setVisibility(View.GONE);
            logo.setVisibility(View.GONE);
            bStart.setVisibility(View.VISIBLE);
            tvUserName.setVisibility(View.VISIBLE);
            editText_logIn_email.setVisibility(View.GONE);
            getEditText_logIn_password.setVisibility(View.GONE);
            bSignIn.setVisibility(View.GONE);
            bSignUp.setVisibility(View.GONE);
            bOut.setVisibility(View.VISIBLE);
        }
        else
            {
                Toast.makeText(getApplicationContext(), "Check your email for verification", Toast.LENGTH_SHORT).show();
            }
    }

    public void onClickStart(View view)
    {
        Intent i = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void sendEmailVer()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                Toast.makeText(getApplicationContext(), "Check your email for verification", Toast.LENGTH_SHORT).show();
            }
            else
                {
                    Toast.makeText(getApplicationContext(), "Send email failed", Toast.LENGTH_SHORT).show();
                }
        });
    }

}
