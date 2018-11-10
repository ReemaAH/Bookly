package com.example.atheer.booklyv1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class bookedService extends AppCompatActivity implements View.OnClickListener{


    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    LinearLayout myLinearLayout;
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

    Res org2;
    private List<Res> orgz2;


    TextView listdata;
    TextView displaytxt;

    ArrayList<String> data;
    String service = "";
    // ElegantNumberButton numRes;
    EditText date;
    DatePickerDialog datePickerDialog;
    String dateval = "";
    EditText time, SerName;
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
    int num;
    String resNum;
    String resId;
    Intent intent2;

    String org1;
    boolean approved1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_service);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.useremail);
        navUserponts = (TextView) headerView.findViewById(R.id.userpoints);
        myLinearLayout = (LinearLayout) findViewById(R.id.viewme);

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

                            startActivity(new Intent(bookedService.this,settings.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(bookedService.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(bookedService.this,Mynavigation.class));

                        } else if (id == R.id.myservicesId){

                            startActivity(new Intent(bookedService.this,myServices.class));

                        }


                        return true;
                    }
                });


        intent2 = getIntent();
        resId = intent2.getStringExtra("res").trim();
        org2 = new Res();
        orgz2 = new ArrayList<>();
        findViewById(R.id.buttonBook).setOnClickListener(this);
        findViewById(R.id.buttonCan).setOnClickListener(this);
        listdata = (TextView) findViewById(R.id.textlist);


        // numRes = (ElegantNumberButton) findViewById(R.id.numberRes);
        date = (EditText) findViewById(R.id.SerDate);
        time = (EditText) findViewById(R.id.SerTime);
        SerName = (EditText) findViewById(R.id.SerName);



        loadbooking();






//
//          numRes.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//                @Override
//                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//
//                    counter = numRes.getNumber();
//
//
//
//                }
//            });

        final Calendar calander = Calendar.getInstance();
        year = calander.get(Calendar.YEAR);
        month = calander.get(Calendar.MONTH);
        day = calander.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog cc = new DatePickerDialog(bookedService.this,
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

        //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        time.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Calendar calander = Calendar.getInstance();
                hour = calander.get(Calendar.HOUR);
                minute = calander.get(Calendar.MINUTE);


                timePickerDialog = new TimePickerDialog(bookedService.this, new TimePickerDialog.OnTimeSetListener() {
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



        super.onStart();
        if(mAuth.getCurrentUser()==null){

            finish();
            startActivity(new Intent(this,loginActivity.class));

        }




    }


    private void loadbooking(){


        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();

      ref =  FirebaseDatabase.getInstance().getReference().child("reservaiton").child(userId);



        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                org1 = "";
                approved1 = false;
                date1 ="";
                time1 ="";
                service = "";
                num = 0;



               for (DataSnapshot ds : dataSnapshot.getChildren()) {

                //   String key = ds.getKey();
                   org2 = ds.getValue(Res.class);
                 //  org2.setResNum(key);
//
//
//                        org1 = dataSnapshot.child("org").getValue(String.class);
//                      //  date1 = dataSnapshot.child("date").getValue(String.class);
//                     //    time1 = dataSnapshot.child("time").getValue(String.class);
//                        service = dataSnapshot.child("service").getValue(String.class);
//                    //    num = dataSnapshot.child("num").getValue(int.class);


//
                if(org2!=null){
                  String o = org2.getResNum().trim() + "";
                    if(o.equals(resId)){
                        listdata.setText(org2.getOrg()+"");
                      date.setText(org2.getDate()+"");
                      time.setText(org2.getTime()+"");
                     SerName.setText(org2.getService()+"");


                   }
                }
//
//
//
                   }
                }



            @Override
            public void onCancelled(DatabaseError databaseError){


            }
        });


//        Query query = ref.orderByChild("resNum").equalTo("33");
//        ValueEventListener valueEventListener = new ValueEventListener() {
//
//            String org = "";
//            boolean approved = false;
//            String date1 ="";
//            String time1 ="";
//            String service = "";
//            int num = 0;
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                    org2 = ds.getValue(Res.class);
//
//
//                    org = dataSnapshot.child("org").getValue(String.class);
//                    approved = dataSnapshot.child("approved").getValue(Boolean.class);
//                    date1 = dataSnapshot.child("date").getValue(String.class);
//                    time1 = dataSnapshot.child("time").getValue(String.class);
//                    service = dataSnapshot.child("service").getValue(String.class);
//                    num = dataSnapshot.child("num").getValue(int.class);
//
//                    listdata.setText(org2.getService()+"");
//                   date.setText(org2.getDate() +"");
//                    time.setText(org2.getTime()+"");
//                    SerName.setText(org2.getService() +"");
//                    //date.setText(date1);
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        query.addListenerForSingleValueEvent(valueEventListener);
//
//




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
                updatebook();
                break;
            case R.id.buttonCan:

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                canBook();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


                break;

        }



    }

    private void canBook() {
        startActivity(new Intent(bookedService.this,myServices.class));

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference mRef =  database.getReference().child("reservaiton").child(userId);

        mRef.child(resId).removeValue();




    }
    private void updatebook() {

          date1= date.getText().toString().trim();;
         time1= time.getText().toString().trim();
//        service1= service.toString();
//        num = 1;
        if(date1.isEmpty()){
            date.setError("Date is required");
            date.requestFocus();
            return;}

        if(time1.isEmpty())  {
            time.setError("Time is required");
            date.requestFocus();
            return;
        } if(!date1.matches("\\d{2}-\\d{2}-\\d{4}")){
            date.setError("Date should be in the following format DD-MM-YYYY");
            date.requestFocus();
            return;
        } if(!time1.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$")){
            time.setError("Time should be in the following format HH:MM");
            time.requestFocus();
            return;
        }

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference mRef =  database.getReference().child("reservaiton").child(userId).child(resId);

       mRef.child("date").setValue(date1);
       mRef.child("time").setValue(time1);


       startActivity(new Intent(bookedService.this,myServices.class));

    }



}
