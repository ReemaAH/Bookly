package com.example.atheer.booklyv1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Mynavigation extends AppCompatActivity  implements CategoryAdapter.OnItemClickListener{

    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    ArrayList<Category_pic> createLists;
    ListView listView;
    Res ser;

    ///////////////// getting current date////////////////////////////
    DateFormat dateFormat ;
    Date date ;
    String date1;
    ///////////////// getting current date////////////////////////////


    ArrayList<String> list;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private DatabaseReference ref4;
    private String userId;
    private FirebaseUser user;

    private DrawerLayout mDrawerLayout;

    ArrayList<cat> list1;

    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorge;
    private DatabaseReference mDatabaseRef;
    private List<cat> mUpload;
    FloatingActionButton mFloatingActionButton;
    //

    private ProgressBar mProgressBar;

    ImageView imagev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynavigation);
        setTitle("Home");



        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.useremail);
        navUserponts = (TextView) headerView.findViewById(R.id.userpoints);

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

                            startActivity(new Intent(Mynavigation.this,settings.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(Mynavigation.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(Mynavigation.this,Mynavigation.class));

                        } else if (id == R.id.myservicesId){

                            startActivity(new Intent(Mynavigation.this,myServices.class));

                        } else if (id == R.id.offer){

                            startActivity(new Intent(Mynavigation.this,displayOffer.class));

                        }


                        return true;
                    }
                });




        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFloatingActionButton =findViewById(R.id.newbutton);
        list1 = new ArrayList<>();
        mProgressBar=findViewById(R.id.progress_circle);

        mUpload=new ArrayList<>();

        mAdapter=new CategoryAdapter(Mynavigation.this, mUpload);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(Mynavigation.this);

        mStorge= FirebaseStorage.getInstance();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("category"); // make sure
        mDBListener= mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUpload.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    cat upload=postSnapshot.getValue(cat.class);

                    upload.setKey(postSnapshot.getKey());
                    mUpload.add(upload);
                    list1.add(upload);

                }


                mAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Mynavigation.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();
        list = new ArrayList<>();

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = new Date();
        date1= dateFormat.format(date);



        ref4= database.getReference().child("reservaiton").child(userId);
        ref4.addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String clientDate;
                    String clientTime;
                    String orgname;

                    String key= ds.getKey();
                    int id = Integer.parseInt(key);

                    ser = ds.getValue(Res.class);
                    clientDate = ser.getDate();
                    ///////// if the date is today and the reservation approved a notification will be send///////
                    if(ser!=null&& clientDate!=null){
                    if( (clientDate.equals(date1)==true) && (ser.isApproved()==false) ){
                        clientTime=ser.getTime();
                        orgname=ser.getOrg();
                        showNotification(clientTime,orgname,id);



                    }}

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError){


            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification(String time, String orgname, int id) {

        String content= "Your reservation Today at " + orgname +" on: " + time+" Be ready!";
        NotificationChannel channel = new NotificationChannel("channel01", "name",
                NotificationManager.IMPORTANCE_HIGH);   // for heads-up notifications
        channel.setDescription("description");

        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        notificationManager.createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(this, "channel01")
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logohome))
                .setContentTitle("Reservation Reminder")
                .setContentText(content)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)   // heads-up
                .build();

        NotificationManagerCompat no = NotificationManagerCompat.from(this);
        no.notify(id, notification);



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
    public void onItemClick(int position) {
        cat selectedItem = mUpload.get(position);
        final String selectedKey = selectedItem.getKey();
        String tiltle=selectedItem.getName();

        Intent intent = new Intent(Mynavigation.this, BrowseServices.class);
        intent.putExtra("name", tiltle);
        startActivity(intent);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
     //   mDatabaseRef.removeEventListener(mDBListener);
    }


}