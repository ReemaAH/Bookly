package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class categoryView extends AppCompatActivity implements categoryViewAdapter.OnItemClickListener{
    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    ArrayList<cat> list1;

    private RecyclerView mRecyclerView;
    private categoryViewAdapter mAdapter;

    private ValueEventListener mDBListener;
    private FirebaseStorage mStorge;
    private DatabaseReference mDatabaseRef;
    private List<cat> mUpload;
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
        setContentView(R.layout.activity_category_view);

        setTitle("Categories");

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

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId();

                        if (id == R.id.settingsId) {

                            startActivity(new Intent(categoryView.this,settingsadmin.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(categoryView.this,loginActivity.class);
                            startActivity(signOUT);


                        }

                        else if (id == R.id.OrgId){

                            startActivity(new Intent(categoryView.this,approveOrgByadmin.class));}




                        else if (id == R.id.homeId){

                            startActivity(new Intent(categoryView.this,dashboardAdmin.class));

                        } else if (id == R.id.CategoriesId){

                            startActivity(new Intent(categoryView.this,categoryView.class));

                        }else if (id == R.id.OrgId){

                            //         startActivity(new Intent(dashboardAdmin.this,CatView.class));

                        }else if (id == R.id.Services1Id){

                            startActivity(new Intent(categoryView.this,BrowseAdmin.class));

                        } else if (id == R.id.ReportsId){

                            startActivity(new Intent(categoryView.this,DeleteOrg2.class));

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
                startActivity(new Intent(categoryView.this,addCategory.class));
            }

        });
        imagev.setY(1770);
        imagev.setX(800);
        list1 = new ArrayList<>();
        mProgressBar=findViewById(R.id.progress_circle);

        mUpload=new ArrayList<>();

        mAdapter=new categoryViewAdapter(categoryView.this, mUpload);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(categoryView.this);

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
                Toast.makeText(categoryView.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
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
        //  Toast.makeText(this,"Normal click at position"+ position, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteClick(int position) {
        cat selectedItem=mUpload.get(position);
        final String selectedKey=selectedItem.getKey();

        StorageReference imageRef= mStorge.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(categoryView.this,"Category was deleted successfully", Toast.LENGTH_LONG).show();

            }
        });
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
