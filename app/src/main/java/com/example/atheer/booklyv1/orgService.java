package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class orgService extends AppCompatActivity implements serviceAdapter.OnItemClickListener  {

    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    ArrayList<services> list1;

    services ser;
    private RecyclerView mRecyclerView;
    private serviceAdapter mAdapter;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorge;
    private DatabaseReference mDatabaseRef;
    private List<services> mUpload;
    FloatingActionButton mFloatingActionButton;
    //

    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private DrawerLayout mDrawerLayout;
    ImageView imagev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_service);


        setTitle("Services");

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

        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, loginActivity.class));
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

                            startActivity(new Intent(orgService.this, settingsorg.class));
                        } else if (id == R.id.logoutId) {

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT = new Intent(orgService.this, loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId) {

                            startActivity(new Intent(orgService.this, mynav.class));

                        } else if (id == R.id.servicesId) {

                            startActivity(new Intent(orgService.this, orgServices.class));

                        } else if (id == R.id.ReservationsId) {

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        } else if (id == R.id.ReportsId) {

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        }


                        return true;
                    }
                });

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFloatingActionButton = findViewById(R.id.newbutton);
        mFloatingActionButton.setImageResource(R.drawable.addicon2);
        imagev = (ImageView) findViewById(R.id.newbutton);
        imagev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(orgService.this, addService.class));
            }

        });
        imagev.setY(1500);
        imagev.setX(800);

        list1 = new ArrayList<>();
        mProgressBar = findViewById(R.id.progress_circle);

        mUpload = new ArrayList<>();

        mAdapter = new serviceAdapter(orgService.this, mUpload);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(orgService.this);

        ser = new services();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user =  mAuth.getCurrentUser();
        String userId = user.getUid();
        String name = mDatabase.child("orgz").child(userId).toString();
        int index=name.lastIndexOf("/");
        name=name.substring(index+1);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemsRef = rootRef.child("services");
        Query query=itemsRef.orderByChild("orgID").equalTo(name);

        query.addValueEventListener(new ValueEventListener() {



            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String str,str2;


                    ser = ds.getValue(services.class);
                    if(!ser.getName().equals(null)){
                        str = ser.getName().toString()+" \n\n "+ ser.getPrice().toString()+ " " ;
                        str2=ser.getName().toString();
                        ser.setKey(ds.getKey());
                        list1.add(ser);
                        mUpload.add(ser);}
                }


                mAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.INVISIBLE);




            }

//


            @Override
            public void onCancelled(DatabaseError databaseError){


            }
        });


    }


    private void loaduserinfo() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();
        ref = database.getReference().child("client").child(userId);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = "";
                int points = 0;


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
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
        if (mAuth.getCurrentUser() == null) {

            finish();
            startActivity(new Intent(this, loginActivity.class));

        }

    }

    @Override
    public void onItemClick(int position) {
        //  Toast.makeText(this,"Normal click at position"+ position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteClick(int position) {
        services selectedItem = mUpload.get(position);
        final String selectedKey = selectedItem.getKey();
        mDatabase.child("services").child(selectedKey).removeValue();
        Toast.makeText(orgService.this, "service wad deleted successfully ", Toast.LENGTH_LONG).show();
        Intent homepage = new Intent(orgService.this, orgService.class);
        startActivity(homepage);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mDatabaseRef.removeEventListener(mDBListener);
    }
}
