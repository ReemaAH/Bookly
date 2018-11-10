package com.example.atheer.booklyv1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class SignUpORG extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsername , editTextPassword, editTextEmail,EditTextphone,EditTextpasswordCon, EditTextphoneNo , EditTextrecordNO;
    ProgressBar Newprogressbar;
    private FirebaseAuth mAuth;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("client");
    String name;
    String email;
    boolean flag;
    String password;
    String password2;
    String phoneNo;
    String recordNO;
   String Category;
    int position=0;
    cat cate;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    Spinner dropdown;
    public SignUpORG(){
        name=null;
        email=null;
        password=null;
        password2=null;
        recordNO=null;
        flag=true;
        phoneNo=null;
    }
    public SignUpORG(String name, int i) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up_org );


        dropdown = (Spinner) findViewById(R.id.spinner1);
        loadDropDown();

        editTextEmail=(EditText)findViewById(R.id.input_email);
        editTextPassword=(EditText)findViewById(R.id.input_password);
        editTextUsername=(EditText)findViewById(R.id.input_name);
        EditTextrecordNO=(EditText)findViewById(R.id.recordNO);
        EditTextpasswordCon=(EditText)findViewById(R.id.input_password2);
        EditTextphoneNo=(EditText)findViewById(R.id.Phone);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.link_login).setOnClickListener(this);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        Newprogressbar = (ProgressBar) findViewById(R.id.progressbar);




    }
    private void loadDropDown(){


        cate= new cat();
        DatabaseReference ref1 = database.getReference("category");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list);
        ref1.addValueEventListener(new ValueEventListener() {


            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String str;
                    cate = ds.getValue(cat.class);


                    str = cate.getName().toString() + " ";
                    list.add(str);

                }


                dropdown.setAdapter(adapter);

            }





            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }





            private void regsterUser(){
        name=editTextUsername.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        email=editTextEmail.getText().toString().trim();
        password2=EditTextpasswordCon.getText().toString().trim();
        recordNO=EditTextrecordNO.getText().toString().trim();
        phoneNo=EditTextphoneNo.getText().toString().trim();
        Category = dropdown.getSelectedItem().toString();

        if (phoneNo.length()<10 || phoneNo.length()>10){

            EditTextphone.setError("Phone Minimum length is 10");
            EditTextphone.requestFocus();
            return;

        }
        if(recordNO.isEmpty()){
            EditTextrecordNO.setError("Record number is required");
            EditTextrecordNO.requestFocus();
            return;}

        if (recordNO.length()!=10){

            EditTextrecordNO.setError("invalid Record Number");
            EditTextrecordNO.requestFocus();
            return;

        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Minimum length of password shoud be 6");
            editTextPassword.requestFocus();
            return;
        }

        if(password2.isEmpty()){
            editTextPassword.setError("Password confirmation required");
            editTextPassword.requestFocus();
            return;
        }

        if(name.isEmpty()){
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }

        if(password2.equals(password)==false)  {
            EditTextpasswordCon.setError("Password Not matching");
            EditTextpasswordCon.requestFocus();
            return;}


        Newprogressbar.setVisibility(View.VISIBLE);

        Task<AuthResult> authResultTask =mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                                            Newprogressbar.setVisibility(View.GONE);
                                                                                                                            if(task.isSuccessful()){

                                                                                                                                FirebaseDatabase database =  FirebaseDatabase.getInstance();
                                                                                                                                FirebaseUser user =  mAuth.getCurrentUser();
                                                                                                                                String userId = user.getUid();
                                                                                                                                DatabaseReference mRef =  database.getReference().child("client").child(userId);
                                                                                                                                mRef.child("name").setValue(name);
                                                                                                                                mRef.child("email").setValue(email);
                                                                                                                                mRef.child("recordNO").setValue(recordNO);
                                                                                                                                mRef.child("groupID").setValue(2);
                                                                                                                                mRef.child("phoneNO").setValue(phoneNo);
                                                                                                                                mRef.child("Status").setValue("Not approved");
                                                                                                                                mRef.child("cat").setValue(Category);

                                                                                                                               mRef =  database.getReference().child("orgz").child(userId);
                                                                                                                                mRef.child("name").setValue(name);
                                                                                                                                mRef.child("email").setValue(email);
                                                                                                                                mRef.child("recordNO").setValue(recordNO);
                                                                                                                                mRef.child("groupID").setValue(2);
                                                                                                                                mRef.child("phoneNO").setValue(phoneNo);
                                                                                                                                mRef.child("Status").setValue("Not approved");
                                                                                                                                mRef.child("cat").setValue(Category);

                                                                                                                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                        Toast.makeText(SignUpORG.this,"Please check your Email",Toast.LENGTH_LONG).show();
                                                                                                                                        Intent homepage = new Intent(SignUpORG.this, orgmessage.class);
                                                                                                                                        startActivity(homepage);


                                                                                                                                    }
                                                                                                                                });
                                                                                                                            }
                                                                                                                            else {
                                                                                                                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                                                                                                                    Toast.makeText(getApplicationContext(), "You already registered", Toast.LENGTH_LONG).show();

                                                                                                                                }

                                                                                                                                else {
                                                                                                                                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }

        );

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:{
                regsterUser();
                //Intent intent = new Intent(SignUpORG.this, dashboardAdmin.class);
               // intent.putExtra("org", "hi");
               // startActivity(intent);
                break;}
            case R.id.link_login:
                Intent login=new Intent(this,loginActivity.class);
                startActivity(login);
                break;

        }
    }



}


