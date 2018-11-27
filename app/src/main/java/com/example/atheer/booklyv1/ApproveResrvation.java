package com.example.atheer.booklyv1;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class ApproveResrvation extends AppCompatActivity {
    android.widget.ListView listView;
    ArrayList<String> list;
    ArrayList<Reservation> list1;
    ArrayAdapter<String> adapter;
    Reservation reservation;
    ImageView img;
    String name1;
    String status;
  //  Name org;
    String name;
    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private DatabaseReference ref2;
    private String userId;
    private FirebaseUser user;

    private DatabaseReference mDatabase;

    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_resrvation);

   //     setTitle("Approve Reservation");


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

                            startActivity(new Intent(ApproveResrvation.this,settingsorg.class));
                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(ApproveResrvation.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(ApproveResrvation.this,mynav.class));

                        } else if (id == R.id.servicesId){

                            startActivity(new Intent(ApproveResrvation.this,orgService.class));

                        } else if (id == R.id.ReservationsId) {

                            startActivity(new Intent(ApproveResrvation.this,ApproveResrvation.class));
                        }
                        else if (id == R.id.OffersId){

                            startActivity(new Intent(ApproveResrvation.this,OffersImages.class));

                        }


                        return true;
                    }
                });


        reservation=new Reservation();


        listView=(ListView)findViewById(R.id.ListView);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();




       mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();


       name="nora";
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.requesttext,R.id.requestInfo,list);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("client").child(userId).child("name").getValue(String.class);

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference itemsRef = rootRef.child("reservaiton");
                Query query=itemsRef.orderByChild("org").equalTo(name);



                query.addValueEventListener(new ValueEventListener() {


                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String str;
                            reservation = ds.getValue(Reservation.class);
                            if (!reservation.isApproved()){
                            str = reservation.getService().toString() + " \n\n ";
                            list.add(str);
                            list1.add(reservation);}
                          //  Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                        }

                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(ApproveResrvation.this, ApproveResrvation2.class);
                                intent.putExtra("reservation", (Serializable) list1.get(position));
                                startActivity(intent);



                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError){


                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

        setTitle("Reservations");



    }


    public void loaduserinfo(){

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


}
