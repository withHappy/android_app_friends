package com.example.myfeed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    Button signup_btn;

    private FirebaseAuth mAuth;
    private static final String TAG = "SignupActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar ab = getSupportActionBar();
        ab.hide();


        findViewById(R.id.signup_btn).setOnClickListener(onClickListener);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


    }
    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signup_btn:
                    signUp();
                    break;
            }
        }
    };



    private void signUp() {
        String email = ((EditText) findViewById(R.id.emailedittext)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordedittext)).getText().toString();
        String passwordcheck = ((EditText) findViewById(R.id.passwordcheckedittext)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordcheck.length() > 0) {

            if (password.equals(passwordcheck)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입이 완료되었습니다");

                                    Intent intent = new Intent(getApplicationContext(),LogInActivity.class); //회원가입하면 로그인창으로 고고
                                    startActivity(intent);

                                } else {
                                    if (task.getException() != null) {//6글자 이하거나
                                        // If sign in fails, display a message to the user.
                                        startToast(task.getException().toString());
                                    }
                                }

                                // ...
                            }
                        });
            } else {
                startToast("비밀번호가 일치하지 않습니다");

            }
        }
        else{
            startToast("이메일 또는 비밀번호를 입력하세요");
        }
    }
    private void startToast(String mes){
        Toast.makeText(this, mes,Toast.LENGTH_SHORT).show();

    }
}