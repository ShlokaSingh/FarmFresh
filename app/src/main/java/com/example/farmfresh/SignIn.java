package com.example.farmfresh;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.farmfresh.admin.Admin;
import com.example.farmfresh.user.menu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    String email, phone, password, choice;
    EditText etEmail, etPassword;
    Button btnSignIn;
    RadioButton rbAdmin, rbUser;
    RadioGroup rgSignIn;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignin);
        rbAdmin = findViewById(R.id.rbAdmin);
        rbUser = findViewById(R.id.rbUser);
        rgSignIn = findViewById(R.id.rgSignIn);

        rgSignIn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb) {
                    choice = rb.getText().toString();
                    Toast.makeText(com.example.farmfresh.SignIn.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //LINKING TO FIREBASE
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        final DatabaseReference userNode = database.getReference("User");
        final DatabaseReference adminNode = database.getReference("Admin");
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(com.example.farmfresh.SignIn.this);
                mDialog.setMessage("Please Wait...");
                mDialog.dismiss();

                if(choice.equals("User")){
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                userNode.child(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getValue() != null){
                                            Toast.makeText(com.example.farmfresh.SignIn.this, "Sign In successful ", Toast.LENGTH_SHORT).show();
                                            Intent AdminMenu =new Intent(getApplicationContext(), menu.class);
                                            startActivity(AdminMenu);
                                            finish();
                                        }
                                        else{
                                            firebaseAuth.signOut();
                                            Toast.makeText(com.example.farmfresh.SignIn.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                        }
                    });
                } else if(choice.equals("Admin")){
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                adminNode.child(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getValue() != null){
                                            Toast.makeText(com.example.farmfresh.SignIn.this, "Sign In successful ", Toast.LENGTH_SHORT).show();
                                            Intent AdminMenu =new Intent(getApplicationContext(), Admin.class);
                                            startActivity(AdminMenu);
                                            finish();
                                        }
                                        else{
                                            firebaseAuth.signOut();
                                            Toast.makeText(com.example.farmfresh.SignIn.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                        }
                    });
                } else{
                    Toast.makeText(com.example.farmfresh.SignIn.this, "Select your role", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
