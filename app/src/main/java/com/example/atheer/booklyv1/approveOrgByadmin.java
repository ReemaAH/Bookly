package com.example.atheer.booklyv1;

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

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.io.Serializable;
        import java.util.ArrayList;

public class approveOrgByadmin extends AppCompatActivity {

    android.widget.ListView ListView;
    ArrayList<String> list;
    ArrayList<orguser> list1;
    ArrayAdapter<String> adapter;
   orguser org;
   String name1;
String status;

    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_org_byadmin);

        setTitle("Organizations requests");


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

                            startActivity(new Intent(approveOrgByadmin.this,settingsadmin.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(approveOrgByadmin.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(approveOrgByadmin.this,dashboardAdmin.class));

                        } else if (id == R.id.CategoriesId){

                            startActivity(new Intent(approveOrgByadmin.this,CatView.class));

                        }else if (id == R.id.OrgId){

                            //         startActivity(new Intent(dashboardAdmin.this,CatView.class));

                        }else if (id == R.id.Services1Id){

                            startActivity(new Intent(approveOrgByadmin.this,BrowseAdmin.class));

                        } else if (id == R.id.ReportsId){

                            //       startActivity(new Intent(dashboardAdmin.this,CatView.class));

                        }


                        return true;
                    }
                });


        org= new orguser();
        ListView = (android.widget.ListView) findViewById(R.id.ListView);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();
        ref =  database.getReference("client");
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.requesttext,R.id.requestInfo,list);
        ref.addValueEventListener(new ValueEventListener() {


            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey(); // to get the user ID 5gbccxxxxgdbehswxxgsf
                    String str;
                    org = ds.getValue(orguser.class);
                    org.setUid(key);
                    if (org.getStatus() != null) {
                        if (org.getStatus().equals("Not approved")) {
                            str = org.getName().toString() + " \n\n ";
                            list.add(str);
                            list1.add(org);
                        }
                    }
                }

                ListView.setAdapter(adapter);
                ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(approveOrgByadmin.this, orginforequest.class);
                        intent.putExtra("org", (Serializable) list1.get(position));
                        startActivity(intent);



                    }
                });
            }










            @Override
            public void onCancelled(DatabaseError databaseError){


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
}
