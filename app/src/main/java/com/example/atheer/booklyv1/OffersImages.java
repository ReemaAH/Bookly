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
import android.widget.AdapterView;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OffersImages extends AppCompatActivity implements ImageAdapter.OnItemClickListener{
TextView navUsername, navUserponts;
NavigationView navigationView;
View headerView;

private RecyclerView mRecyclerView;
private ImageAdapter mAdapter;

private ValueEventListener mDBListener;
private FirebaseStorage mStorge;
private DatabaseReference mDatabaseRef;
private List<uloadOffers> mUpload;
FloatingActionButton mFloatingActionButton;
 //

private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;

    private DrawerLayout mDrawerLayout;
    ImageView imagev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_images);

        setTitle("Offers");

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

                            startActivity(new Intent(OffersImages.this,settingsorg.class));
                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(OffersImages.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(OffersImages.this,mynav.class));

                        } else if (id == R.id.servicesId){

                            startActivity(new Intent(OffersImages.this,orgServices.class));

                        } else if (id == R.id.ReservationsId){

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        } else if (id == R.id.ReportsId){

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        }


                        return true;
                    }
                });

        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFloatingActionButton =findViewById(R.id.newbutton);
        mFloatingActionButton.setImageResource(R.drawable.addicon2);
        imagev=(ImageView) findViewById(R.id.newbutton);
        imagev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OffersImages.this,addOffer.class));
            }

        });
        imagev.setY(1400);
        imagev.setX(800);
      //  imagev = (ImageView)findViewById(R.id.newbutton);

        mProgressBar=findViewById(R.id.progress_circle);

        mUpload=new ArrayList<>();

        mAdapter=new ImageAdapter(OffersImages.this, mUpload);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(OffersImages.this);

        mStorge= FirebaseStorage.getInstance();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("client"); // make sure
       mDBListener= mDatabaseRef.child(userId).child("offer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUpload.clear();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    uloadOffers upload=postSnapshot.getValue(uloadOffers.class);
                    try {
                        Date strDate = sdf.parse(upload.geteDate());
                        if (new Date().after(strDate)) {
                            final String selectedKey=postSnapshot.getKey();
                            StorageReference imageRef= mStorge.getReferenceFromUrl(upload.getmImageUrl());
                            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    mDatabaseRef.child(userId).child("offer").child(selectedKey).removeValue();
                                }
                            });

                        } else {
                            upload.setmKey(postSnapshot.getKey());
                            mUpload.add(upload);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

                mAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OffersImages.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.INVISIBLE);
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
    public void onItemClick(int position) {
        Toast.makeText(this,"Normal click at position"+ position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteClick(int position) {
     uloadOffers selectedItem=mUpload.get(position);
     final String selectedKey=selectedItem.getmKey();

        StorageReference imageRef= mStorge.getReferenceFromUrl(selectedItem.getmImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(userId).child("offer").child(selectedKey).removeValue();
                Toast.makeText(OffersImages.this,"Item deleted", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onWhateverClick(int position) {
        Toast.makeText(this,"Delete click at position"+ position, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}
