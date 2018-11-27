package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.design.widget.NavigationView;
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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

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
import java.util.List;

public class BrowseOrgAdmin extends AppCompatActivity {

    private static final String TAG = "viewOrgnization";
    ArrayList<String> list;
    android.widget.ListView ListView;
    ArrayAdapter<String> adapter;
    Orgz org;
    private List<Orgz> orgz;
    String cat;
    private OrgzAdapter orgAdapter;

    private DatabaseReference ref2;

    TextView navUsername, navUserponts;
    NavigationView navigationView;
    View headerView;
    SearchView search;
    cat c;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;
    String str = "ok";
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_org_admin);





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

                            startActivity(new Intent(BrowseOrgAdmin.this,settingsadmin.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(BrowseOrgAdmin.this,loginActivity.class);
                            startActivity(signOUT);


                        }

                        else if (id == R.id.OrgId){

                            startActivity(new Intent(BrowseOrgAdmin.this,approveOrgByadmin.class));}




                        else if (id == R.id.homeId){

                            startActivity(new Intent(BrowseOrgAdmin.this,dashboardAdmin.class));

                        } else if (id == R.id.CategoriesId){

                            startActivity(new Intent(BrowseOrgAdmin.this,categoryView.class));

                        }else if (id == R.id.OrgId){

                            //         startActivity(new Intent(dashboardAdmin.this,CatView.class));

                        }else if (id == R.id.Services1Id){

                            startActivity(new Intent(BrowseOrgAdmin.this,BrowseAdmin.class));

                        } else if (id == R.id.ReportsId){

                            startActivity(new Intent(BrowseOrgAdmin.this,DeleteOrg2.class));

                        }

                        return true;
                    }
                });





        Intent intent = getIntent();
        cat = intent.getStringExtra("name");
        cat = cat.trim();
        setTitle(cat);
        search = findViewById(R.id.searchQ) ;

        ListView = (ListView) findViewById(R.id.ListView);

        Log.d(TAG, "onCreate: Started");

        org = new Orgz();
        orgz = new ArrayList<>();

        list = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();
        userId = user.getUid();
        ref =  database.getReference("client");






        ref2 =  FirebaseDatabase.getInstance().getReference().child("orgz");

        Query query = ref2.orderByChild("cat").equalTo(cat+" ");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    org = ds.getValue(Orgz.class);
                    if(org.getStatus().toString().equals("approved"))
                        orgz.add(org);

                } orgAdapter = new OrgzAdapter(getApplicationContext(),orgz);
                ListView.setAdapter(orgAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        query.addListenerForSingleValueEvent(valueEventListener);





        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(BrowseOrgAdmin.this, ServicesAdmin.class);
                intent.putExtra("org", (Serializable) orgz.get(position));
                startActivity(intent);


            }
        });




   /*     search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                orgAdapter.getFilter().filter(query);

                ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        // additon
                        Orgz model = (Orgz) parent.getItemAtPosition(position);
                        Intent intent = new Intent(BrowseOrgAdmin.this,ServicesAdmin.class);
                        intent.putExtra("org",(Serializable) model  );
                        startActivity(intent);


                    }
                });
                return false;
            }
        });
*/

    }

//    public void orgBasedonCat( Orgz o ){
//        String  s =   o.getCatg();
//        if(s.equals(cat)){
//            list.add(o.getName());
//        }
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
}
