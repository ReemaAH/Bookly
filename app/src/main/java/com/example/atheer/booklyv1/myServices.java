package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class myServices extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "viewOrgnization";

    private DrawerLayout mDrawerLayout;

    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    private DatabaseReference ref2;
    LinearLayout myLinearLayout;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;

    ArrayList<String> list;
    private ListView ListView;
    ArrayAdapter<String> adapter;
    private ResAdapter resAdapter;
    private List<Res> reserv;
    Orgz org;
    String cat;
    Res res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_services);

        findViewById(R.id.buttonServices).setOnClickListener(this);

        setTitle("My Services");



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

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();

                        if (id == R.id.settingsId) {

                            startActivity(new Intent(myServices.this,settings.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(myServices.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(myServices.this,Mynavigation.class));

                        } else if (id == R.id.myservicesId){

                            startActivity(new Intent(myServices.this,myServices.class));

                        }


                        return true;
                    }
                });



        ListView = (ListView) findViewById(R.id.ListView);
        reserv = new ArrayList<>();
//        if(reserv.isEmpty()){
//            myLinearLayout.setVisibility(View.VISIBLE);
//        }
       // reserv.add(new Res("22/12/1417","02:00","book table",3,"Urth","33",true));

        Log.d(TAG, "onCreate: Started");


        list = new ArrayList<String>();
        res = new Res();
        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();
      ref2 =  database.getReference().child("reservaiton").child(userId);

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    res = ds.getValue(Res.class);
                    if(res.getResNum() != null)
                    reserv.add(res);

                }  resAdapter = new ResAdapter(getApplicationContext(),reserv);
                ListView.setAdapter(resAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(myServices.this,bookedService.class);
                intent.putExtra("res", reserv.get(position).getResNum());
                startActivity(intent);


            }
        });






//        org = new Orgz("Apple Pie", "4.4", "7", "Restaurant");
//        Orgz org2 = new Orgz("Urth", "4.4", "7", "Restaurant");
//        Orgz org3 = new Orgz("Five Guys", "4.4", "7","Restaurant");
//        Orgz org4 = new Orgz("Apple Pie", "4.4", "7","Restaurant");
//        Orgz org5 = new Orgz("The Cinema", "4.4", "7", "Cinema");
//        Orgz org6 = new Orgz("VOX", "4.4", "7","Cinema");
//        Orgz org7 = new Orgz("AMC", "4.4", "7","Cinema");
//
//
//

       // list.add("Urth | 2:00");

////        ArrayList<String> orgs = new ArrayList<>();
////        orgs.add("Urth");
////        orgs.add("Five Guys");
////        orgs.add("Nozomi");
////        orgs.add("Lusin");
////        orgs.add("Red Chilli");
//
//        // adapter= new OrgzAdapter(dataModels,getApplicationContext());








    }



    @Override
    public void onClick(View v) {

        switch(v.getId()){



            case R.id.buttonServices:
                startActivity(new Intent(this,Mynavigation.class));
                break;


        }}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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


        super.onStart();
        if(mAuth.getCurrentUser()==null){

            finish();
            startActivity(new Intent(this,loginActivity.class));

        }


    }




    }

