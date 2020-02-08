package com.asnproject.authentificationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "LoginActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText editTextemail;
    private EditText editTextpassword;
    private Button buttonlogin;
    private Button buttonregister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Variable tadi untuk memanggil fungsi
       // mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //mengatur id sesuai dengan komponen yang dibuat
        editTextemail = (EditText)findViewById(R.id.editTextemail);
        editTextpassword = (EditText)findViewById(R.id.editTextPassword);
        buttonlogin = (Button)findViewById(R.id.buttonLogin);
        buttonregister = (Button)findViewById(R.id.buttonRegister);



        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String email = editTextemail.getText().toString();
                 String password = editTextpassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).
                        addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "signIn:Success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else {
                            Log.d(TAG, "signIn:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Gagal SignIn",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        buttonregister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = editTextemail.getText().toString();
                String password = editTextpassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Log.d(TAG, "createAccount:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                }
                                else {
                                    Log.d(TAG, "createAccount:gagal", task.getException());
                                    Toast.makeText(MainActivity.this, "Gagal buat akun", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}
