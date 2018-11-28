package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class displayOffer extends AppCompatActivity implements OfferAdapter.OnItemClickListener {
    TextView navUsername, navUserponts;
    NavigationView navigationView;
  //  private OnImageClickListener onImageClickListener;
    View headerView;
    ArrayList<uloadOffers> list1;
    ImageView img;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;
    Intent intent;
    private DrawerLayout mDrawerLayout;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorge;
    private DatabaseReference mDatabaseRef;
    private List<uloadOffers> mUpload;
    FloatingActionButton mFloatingActionButton;
    //
    uloadOffers org;
    private ProgressBar mProgressBar;

    private RecyclerView mRecyclerView;
    private OfferAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_offer);

        setTitle("Offers");

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        headerView = navigationView.getHeaderView(0);
        img = (ImageView) headerView.findViewById(R.id.userimage);
        navUsername = (TextView) headerView.findViewById(R.id.useremail);
        navUserponts = (TextView) headerView.findViewById(R.id.userpoints);


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

                            startActivity(new Intent(displayOffer.this,settings.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(displayOffer.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(displayOffer.this,Mynavigation.class));

                        } else if (id == R.id.myservicesId){

                            startActivity(new Intent(displayOffer.this,myServices.class));

                        } else if (id == R.id.offer){

                            startActivity(new Intent(displayOffer.this,displayOffer.class));

                        }


                        return true;
                    }
                });

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFloatingActionButton = findViewById(R.id.newbutton);


        list1 = new ArrayList<>();
        mProgressBar = findViewById(R.id.progress_circle);

        mUpload = new ArrayList<>();

        mAdapter = new OfferAdapter(displayOffer.this, mUpload);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(displayOffer.this);

        mStorge = FirebaseStorage.getInstance();
        //  mDatabaseRef= FirebaseDatabase.getInstance().getReference("orgz"); // make sure

        org = new uloadOffers();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("offer");

        ref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUpload.clear();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    uloadOffers upload = postSnapshot.getValue(uloadOffers.class);
                    try {
                        Date strDate = sdf.parse(upload.geteDate());
                        if (new Date().after(strDate)) {
                            final String selectedKey = postSnapshot.getKey();
                            StorageReference imageRef = mStorge.getReferenceFromUrl(upload.getmImageUrl());
                            imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    ref.child(selectedKey).removeValue();
                                }
                            });

                        } else {
                            upload.setmKey(postSnapshot.getKey());
                            mUpload.add(upload);
                            list1.add(upload);
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

    }

    @Override
    public void onItemClick(int position) {
        uloadOffers selectedItem = mUpload.get(position);
        final String selectedKey = selectedItem.getmKey();

        Intent intent = new Intent(displayOffer.this, bookOffer.class);
        intent.putExtra("org", (Serializable) list1.get(position));
        startActivity(intent);
    }

    @Override
    public void onOfferClick(int position) {
        uloadOffers selectedItem = mUpload.get(position);
        final String selectedKey = selectedItem.getmKey();

        Intent intent = new Intent(displayOffer.this, bookOffer.class);
        intent.putExtra("org", (Serializable) list1.get(position));
        startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mDatabaseRef.removeEventListener(mDBListener);
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
}
