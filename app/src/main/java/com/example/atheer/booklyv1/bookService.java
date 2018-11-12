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

//mport com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class bookService extends AppCompatActivity implements View.OnClickListener {

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

    TextView listdata;
    TextView displaytxt;
    Spinner sp;
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
    int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);







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

                            startActivity(new Intent(bookService.this,settings.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(bookService.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(bookService.this,Mynavigation.class));

                        } else if (id == R.id.myservicesId){

                            startActivity(new Intent(bookService.this,myServices.class));

                        }


                        return true;
                    }
                });





            listdata = (TextView) findViewById(R.id.textlist);

            sp = (Spinner) findViewById(R.id.spinner1);
          // numRes = (ElegantNumberButton) findViewById(R.id.numberRes);
            date = (EditText) findViewById(R.id.SerDate);
            time = (EditText) findViewById(R.id.SerTime);



            intent = getIntent();
            listdata.setText(intent.getStringExtra("name"));
            setTitle(intent.getStringExtra("name"));





            data = new ArrayList<>();
            data.add("Book a table");
            data.add("Book a table2");



            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data); //Create the Adapter to set the data
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Set the layout resource to create the drop down views.
            sp.setAdapter(adapter); //Set the data to your spinner



            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    service=sp.getSelectedItem().toString();
                    // displaytxt.setText(service);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


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

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog cc = new DatePickerDialog(bookService.this,
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



//            date.setOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View view){
//
//
//                    datePickerDialog = new DatePickerDialog(bookService.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                            dateval = day+"-"+(month+1)+"-"+year;
//
//                            date.setText(dateval);
//                        }
//                    },year,month,day);
//
//                    datePickerDialog.show();
//
//                }
//
//            });

      //  datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            time.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    Calendar calander = Calendar.getInstance();
                    hour = calander.get(Calendar.HOUR);
                    minute = calander.get(Calendar.MINUTE);


                    timePickerDialog = new TimePickerDialog(bookService.this, new TimePickerDialog.OnTimeSetListener() {
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


//    public void dispalyResult(View view){
//        displaytxt.setText(Service);
//    }

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
        service1= service.toString();
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
        }else if(num>5){
            SerNum.setError("number of people is required");
            SerNum.requestFocus();
            return;
        } else{

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();
        Random rand = new Random();
        int  n = rand.nextInt(2000) + 1;
        DatabaseReference mRef =  database.getReference().child("reservaiton").child(n+"");
        mRef.child("clientID").setValue(userId);
        mRef.child("date").setValue(date1);
        mRef.child("time").setValue(time1);
        mRef.child("service").setValue(service1);
        mRef.child("num").setValue(1);
        mRef.child("org").setValue(intent.getStringExtra("name"));
        mRef.child("orgID").setValue(intent.getStringExtra("name"));
        mRef.child("resNum").setValue(n+"");
        mRef.child("approved").setValue(false);

        startActivity(new Intent(bookService.this,Booked.class));}

    }


}
