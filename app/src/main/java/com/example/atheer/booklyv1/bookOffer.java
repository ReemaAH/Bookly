package com.example.atheer.booklyv1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class bookOffer extends AppCompatActivity implements View.OnClickListener{
    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;
    Intent intent;
    int n=0;
    // String counter;
    private DrawerLayout mDrawerLayout;

    uloadOffers temp;

    private DatabaseReference mDatabase;
    TextView listdata;
    TextView displaytxt;
    TextView sp;
    ArrayList<String> data;
    String service = "";
    // ElegantNumberButton numRes;
    EditText date,SerNum;
    DatePickerDialog datePickerDialog;
    String dateval = "";
    EditText time;
    String timeval = "";
    TimePickerDialog timePickerDialog;
    int hour;
    int minute;

    int year;
    int month;
    int day;

    String service1;
    String date1;
    String time1;
    String name;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_offer);




        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.useremail);
        navUserponts = (TextView) headerView.findViewById(R.id.userpoints);
        SerNum = (EditText) headerView.findViewById(R.id.SerNum);

        findViewById(R.id.buttonBook).setOnClickListener(this);

        loaduserinfo();


        mDrawerLayout = findViewById(R.id.drawerId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

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

                            startActivity(new Intent(bookOffer.this,settings.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(bookOffer.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(bookOffer.this,Mynavigation.class));

                        } else if (id == R.id.myservicesId){

                            startActivity(new Intent(bookOffer.this,myServices.class));

                        }


                        return true;
                    }
                });





        listdata = (TextView) findViewById(R.id.textlist);

        sp = (TextView)  findViewById(R.id.service);
        displaytxt = (TextView) findViewById(R.id.coupon);
        date = (EditText) findViewById(R.id.SerDate);
        time = (EditText) findViewById(R.id.SerTime);





        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog cc = new DatePickerDialog(bookOffer.this,
                        new DatePickerDialog.OnDateSetListener() {


                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                cc.getDatePicker().setMinDate(System.currentTimeMillis()-1000);

                cc.show();
            }
        });




        time.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Calendar calander = Calendar.getInstance();
                hour = calander.get(Calendar.HOUR);
                minute = calander.get(Calendar.MINUTE);


                timePickerDialog = new TimePickerDialog(bookOffer.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

//                    ]

                        timeval = i + ":" + i1 + " ";

                        time.setText(timeval);


                    }
                },hour,minute,false);

                timePickerDialog.show();

            }

        });


        Intent i = getIntent();
        temp = (uloadOffers) i.getSerializableExtra("org");
        sp.setText(temp.getmName());
        displaytxt.setText(temp.getCoupon());

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("client").child(temp.getOrgID()).child("name").getValue(String.class);
                listdata.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

        super.onStart();
        if(mAuth.getCurrentUser()==null){

            finish();
            startActivity(new Intent(this,loginActivity.class));

        }


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
                int points = 0;


                for(DataSnapshot ds: dataSnapshot.getChildren() ){

                    name = dataSnapshot.child("name").getValue(String.class);

                    if (dataSnapshot.hasChild("totalPoint")) {
                        points = dataSnapshot.child("totalPoint").getValue(int.class);
                        navUserponts.setVisibility(View.VISIBLE);
                        navUserponts.setText(points + "");
                    }



                    navUsername.setText(name);



                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonBook:

                book();
                break;

        }
    }

    private void book() {

        date1= date.getText().toString().trim();;
        time1= time.getText().toString().trim();
      //  service1= service.toString();
        //String w =  SerNum.getText().toString().trim();
        // num = Integer.parseInt(w);
        if(date1.isEmpty()){
            date.setError("Date is required");
            date.requestFocus();
            return;}

        else if(time1.isEmpty())  {
            time.setError("Time is required");
            date.requestFocus();
            return;
        }else  if(!date1.matches("\\d{2}-\\d{2}-\\d{4}")){
            date.setError("Date should be in the following format DD-MM-YYYY");
            date.requestFocus();
            return;
        }else if(!time1.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")){
            time.setError("Time should be in the following format HH:MM");
            time.requestFocus();
            return;
        } else{

            FirebaseDatabase database =  FirebaseDatabase.getInstance();
            FirebaseUser user =  mAuth.getCurrentUser();
            String userId = user.getUid();
            Random rand = new Random();
            n = rand.nextInt(2000) + 1;
            DatabaseReference mRef =  database.getReference().child("reservaiton").child(n+"");
            mRef.child("clientID").setValue(userId);
            mRef.child("date").setValue(date1);
            mRef.child("time").setValue(time1);
            mRef.child("service").setValue(temp.getmName());
            mRef.child("num").setValue(1);
            mRef.child("orgID").setValue(temp.getOrgID());
            mRef.child("resNum").setValue(n+"");
            mRef.child("approved").setValue(false);
            mRef.child("coupon").setValue(temp.getCoupon());
            startActivity(new Intent(bookOffer.this,Booked.class));}

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("client").child(temp.getOrgID()).child("name").getValue(String.class);
                DatabaseReference mRef =  database.getReference().child("reservaiton").child(n+"");
                mRef.child("org").setValue(name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

            }


}
