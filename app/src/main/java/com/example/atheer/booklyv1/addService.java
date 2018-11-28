package com.example.atheer.booklyv1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class addService extends AppCompatActivity implements View.OnClickListener {
    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    ImageView img;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;
    Intent intent;
    private DatabaseReference mDatabase;
    int n=0;
    // String counter;
    private DrawerLayout mDrawerLayout;

    String orgName;
    TextView listdata;
    TextView displaytxt;
    Spinner sp;
    ArrayList<String> data;
    String service = "";
    EditText serviceName,price;
    EditText MaxNo;
    String Service;
    String price2,maxNO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        setTitle("Add Service");

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.useremail);
        navUserponts = (TextView) headerView.findViewById(R.id.userpoints);
        img = (ImageView) headerView.findViewById(R.id.userimage);

        loaduserinfo();


        mDrawerLayout = findViewById(R.id.drawerId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        super.onStart();
        if(mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,loginActivity.class));
        }


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();

                        if (id == R.id.settingsId) {

                            startActivity(new Intent(addService.this,settingsorg.class));
                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(addService.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(addService.this,mynav.class));

                        } else if (id == R.id.servicesId){

                            startActivity(new Intent(addService.this,orgService.class));

                        } else if (id == R.id.ReservationsId) {

                            startActivity(new Intent(addService.this,ApproveResrvation.class));
                        }
                        else if (id == R.id.OffersId){

                            startActivity(new Intent(addService.this,OffersImages.class));

                        }


                        return true;
                    }
                });


        serviceName= findViewById(R.id.edit_text_file_name);
     //   price= findViewById(R.id.edit_text_file_price);
        MaxNo= findViewById(R.id.edit_text_file_maxNO);

        findViewById(R.id.button_add).setOnClickListener(this);



    }


    private void loaduserinfo() {

        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();
        ref =  database.getReference().child("client").child(userId);



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = "";
                String imgurl="";

                for(DataSnapshot ds: dataSnapshot.getChildren() ){
                    imgurl=dataSnapshot.child("image").getValue(String.class);
                    name = dataSnapshot.child("name").getValue(String.class);

                    Glide.with(getApplicationContext()).load(imgurl).into(img);
                    navUsername.setText(name);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        super.onStart();
        if(mAuth.getCurrentUser()==null){

            finish();
            startActivity(new Intent(this,loginActivity.class));

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_add:

             addServices();
                break;

        }
    }

    public void  addServices(){
        Service=serviceName.getText().toString().trim();
        maxNO=MaxNo.getText().toString().trim();

        if(Service.isEmpty()){
            serviceName.setError("Service name is required");
            serviceName.requestFocus();
            return;}
        else if (maxNO.isEmpty()){
            MaxNo.setError("maximum number is required");
            MaxNo.requestFocus();
            return;}
            else {


            FirebaseDatabase database =  FirebaseDatabase.getInstance();
            FirebaseUser user =  mAuth.getCurrentUser();
            String userId = user.getUid();


            Random rand = new Random();
            n = rand.nextInt(2000) + 1;
            DatabaseReference mRef2 =  database.getReference().child("services").child(n+"");
            mRef2.child("name").setValue(Service);
            DatabaseReference mRef4 =  database.getReference().child("services").child(n+"");
            mRef4.child("maxNO").setValue(maxNO);
            DatabaseReference mRef =  database.getReference().child("services").child(n+"");
            mRef.child("orgID").setValue(userId);



            mDatabase = FirebaseDatabase.getInstance().getReference();

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    FirebaseDatabase database =  FirebaseDatabase.getInstance();
                    FirebaseUser user =  mAuth.getCurrentUser();
                    String userId = user.getUid();
                    orgName = dataSnapshot.child("client").child(userId).child("name").getValue(String.class);
                    //FirebaseDatabase database2 =  FirebaseDatabase.getInstance();
                    DatabaseReference mRef5 =  database.getReference().child("services").child(n+"");
                    mRef5.child("orgName").setValue(orgName);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                }
            });

            Toast.makeText(addService.this, "added successfully", Toast.LENGTH_LONG).show();
          //  startActivity(new Intent(addService.this,mynav.class));
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;





        }
        return super.onOptionsItemSelected(item);
    }
}
