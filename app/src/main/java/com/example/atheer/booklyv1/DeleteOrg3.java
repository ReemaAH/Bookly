package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteOrg3 extends AppCompatActivity  implements View.OnClickListener {
    orguser org;
    String name1;
    String status;
    Intent intent;
    TextView navUsername, navUserponts, orgphone1, orgcom1, orgname1,orgcate1, orgemail1;
    View headerView;
    orguser temp;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_org3);


        // to insert org info in the xml
        orgphone1 = (TextView) findViewById(R.id.orgphone);
        orgcom1 = (TextView) findViewById(R.id.orgcom);
        orgname1 = (TextView) findViewById(R.id.orgname);
        orgcate1= (TextView) findViewById(R.id.orgcate);
        orgemail1= (TextView) findViewById(R.id.orgemail);

        // to add events in the buttons
        findViewById(R.id.dis).setOnClickListener(this);


        Intent i = getIntent();
        temp = (orguser) i.getSerializableExtra("org");

        orgname1.setText(temp.getName());
        orgcom1.setText(temp.getRecordNO());
        orgphone1.setText(temp.getPhoneNO());
        orgcate1.setText(temp.getCat());
        orgemail1.setText(temp.getEmail());


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.dis:
                delete();
                break;

        }
    }



    private void delete(){

        // Delete user from firebse
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference().child("client").child(temp.getUid());
        //ref.setValue(null);\


        // i made this "dissaproved1" to distinguish between the real disapproved and the new orgs
        // they should be in waiting status not disappeoved
        ref.child("Status").setValue("disapproved1");


        // redirect user to the previous page
        Intent prev = new Intent(DeleteOrg3.this, ManageOrganaization.class);
        startActivity(prev);
    }



}
