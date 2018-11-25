package com.example.atheer.booklyv1;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

public class orgServices extends AppCompatActivity implements View.OnClickListener{

    ImageView img;
    ImageView imagev;
    android.widget.ListView ListView;
    //FirebaseDatabase database;
    //DatabaseReference ref;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    List<String> list2;
    services ser;
    private DatabaseReference mDatabase;
    private ListView ListView2;
    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    //TextView serviceO;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;
    private DatabaseReference mDatabaseRef;
    private DrawerLayout mDrawerLayout;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_services);
        setTitle("Services");


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

                            startActivity(new Intent(orgServices.this,settingsorg.class));
                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(orgServices.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(orgServices.this,mynav.class));

                        } else if (id == R.id.servicesId){

                            startActivity(new Intent(orgServices.this,orgServices.class));

                        } else if (id == R.id.ReservationsId){

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        } else if (id == R.id.ReportsId){

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        }


                        return true;
                    }
                });

        imagev=(ImageView) findViewById(R.id.newbutton);
        imagev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(orgServices.this,addService.class));
            }

        });
        imagev.setY(1300);
        imagev.setX(500);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();
        list2= new ArrayList<>();
        String name = mDatabase.child("orgz").child(userId).toString();
        int index=name.lastIndexOf("/");
        name=name.substring(index+1);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemsRef = rootRef.child("services");
        Query query=itemsRef.orderByChild("orgID").equalTo(name);

        ser = new services();
        ListView = (ListView) findViewById(R.id.ListView1);
        ListView2 = (ListView) findViewById(R.id.ListView1);
        database = FirebaseDatabase.getInstance();
        //ref =database.getReference().child("client");
        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();
      //  ref =  database.getReference().child("services");
        list = new ArrayList<>();
        //findViewById( R.layout.service_info,R.id.button_delete).setOnClickListener(this);

        adapter = new ArrayAdapter<String>(this, R.layout.service_info,R.id.serviceInfo,list);
     //   adapter2 = new ArrayAdapter(this, R.layout.service_info,R.id.button_delete,list2);
        query.addValueEventListener(new ValueEventListener() {



            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String str,str2;


                    ser = ds.getValue(services.class);
                    if(!ser.getName().equals(null)){
                    str = ser.getName().toString()+" \n\n "+ ser.getPrice().toString()+ " " ;
                        str2=ser.getName().toString();
                    list.add(str);
                    list2.add(str2);}
                }


            //    ListView2.setAdapter(adapter2);
                ListView.setAdapter(adapter);




            }

//


            @Override
            public void onCancelled(DatabaseError databaseError){


            }
        });

        mDatabaseRef= FirebaseDatabase.getInstance().getReference("services");


        ListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = list.get(position);
                Toast.makeText(orgServices.this,selectedItem+"Nora" , Toast.LENGTH_LONG).show();
          //      final String selectedKey = selectedItem.getKey();
           //     mDatabaseRef.child(selectedKey).removeValue();
           //     ListView2.removeViewAt(position);


            }
        });



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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;





        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onClick(View v) {

    }
}
