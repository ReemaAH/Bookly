package com.example.atheer.booklyv1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ServicesAdmin extends AppCompatActivity implements View.OnClickListener{


    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;


    TextView listdata;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;

    private DrawerLayout mDrawerLayout;

    ImageView imagev;
    android.widget.ListView ListView;
    //FirebaseDatabase database;
    //DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    services ser;


    Spinner sp;
    ArrayList<String> data;
    String service = "";
    ElegantNumberButton numRes;
    EditText date;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_admin);






        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.useremail);
        navUserponts = (TextView) headerView.findViewById(R.id.userpoints);

        loaduserinfo();

        super.onStart();
        if(mAuth.getCurrentUser()==null){

            finish();
            startActivity(new Intent(this,loginActivity.class));

        }

        mDrawerLayout = findViewById(R.id.drawerId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
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

                        // Add code here to update the UI based on t he item selected
                        // For example, swap UI fragments here  BrowseAdmin

                        int id = menuItem.getItemId();

                        if (id == R.id.settingsId) {

                                startActivity(new Intent(ServicesAdmin.this,settingsadmin.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(ServicesAdmin.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(ServicesAdmin.this,dashboardAdmin.class));

                        } else if (id == R.id.CategoriesId){

                            startActivity(new Intent(ServicesAdmin.this,CatView.class));

                        }else if (id == R.id.OrgId){

                            //         startActivity(new Intent(dashboardAdmin.this,CatView.class));

                        }else if (id == R.id.Services1Id){

                            startActivity(new Intent(ServicesAdmin.this,BrowseAdmin.class));

                        } else if (id == R.id.ReportsId){

                            //       startActivity(new Intent(dashboardAdmin.this,CatView.class));

                        }


                        return true;
                    }
                });

      /*  listdata = (TextView) findViewById(R.id.textlist);
        ser = new services();
        ListView = (ListView) findViewById(R.id.ListView);
        database = FirebaseDatabase.getInstance();
        //ref =database.getReference().child("client");
      //  mAuth = FirebaseAuth.getInstance();
     //   user =  mAuth.getCurrentUser();
     //   userId = user.getUid();
        Intent intent = getIntent();
        listdata.setText(intent.getStringExtra("name"));
        setTitle(intent.getStringExtra("name"));

        ref =  database.getReference().child("client").child(intent.getStringExtra("name")).child("services");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.service_info,R.id.serviceInfo,list);
        ref.addValueEventListener(new ValueEventListener() {



            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String str;


                    ser = ds.getValue(services.class);
                    str = ser.getName().toString()+" \n "+ ser.getPrice()+ "SR \t"+ ser.getRating()+ " " ;
                    list.add(str);}


                ListView.setAdapter(adapter);

            }




            @Override
            public void onCancelled(DatabaseError databaseError){


            }
        });




        imagev=(ImageView) findViewById(R.id.addbutton);
        // imagev.setOnClickListener(new View.OnClickListener() {
        // @Override
        // //   startActivity(new Intent(CatView.this,addCategory.class));
        // }

        // });
        imagev.setY(1300);
        imagev.setX(500);*/
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
      /*  switch (view.getId()){
            case R.id.buttonBook:
                startActivity(new Intent(this,Booked.class));
                break;

        }*/
    }
}
