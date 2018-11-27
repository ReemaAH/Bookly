package com.example.atheer.booklyv1;

import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.TextView;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import android.support.annotation.NonNull;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthCredential;
        import com.google.firebase.auth.EmailAuthProvider;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;


        import java.util.ArrayList;
// client homepage (contains categories)


public class EditPasswordadmin extends AppCompatActivity implements View.OnClickListener{
    EditText currentpass , newpass , conEdit;



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
        setContentView(R.layout.activity_edit_passwordadmin);

        currentpass = (EditText) findViewById(R.id.currentpass);
        newpass= (EditText) findViewById(R.id.newpass);
        conEdit= (EditText) findViewById(R.id.con);

        findViewById(R.id.UpdateButton).setOnClickListener(this);

        setTitle("Chenge password");



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

                        mDrawerLayout.closeDrawers();


                        int id = menuItem.getItemId();

                        if (id == R.id.settingsId) {

                            startActivity(new Intent(EditPasswordadmin.this,settingsadmin.class));

                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(EditPasswordadmin.this,loginActivity.class);
                            startActivity(signOUT);


                        }

                        else if (id == R.id.OrgId){

                            startActivity(new Intent(EditPasswordadmin.this,approveOrgByadmin.class));}




                        else if (id == R.id.homeId){

                            startActivity(new Intent(EditPasswordadmin.this,dashboardAdmin.class));

                        } else if (id == R.id.CategoriesId){

                            startActivity(new Intent(EditPasswordadmin.this,categoryView.class));

                        }else if (id == R.id.OrgId){

                            //         startActivity(new Intent(dashboardAdmin.this,CatView.class));

                        }else if (id == R.id.Services1Id){

                            startActivity(new Intent(EditPasswordadmin.this,BrowseAdmin.class));

                        } else if (id == R.id.ReportsId){

                            startActivity(new Intent(EditPasswordadmin.this,DeleteOrg2.class));

                        }




                        return true;
                    }
                });



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.UpdateButton:{

                updatePassword();
            }

        }
    }

    private void updatePassword() {


        String current = currentpass.getText().toString().trim();
        String newp = newpass.getText().toString().trim();
        final String conpass = conEdit.getText().toString().trim();


/////////--------- checking part ---------------------////

        if (current.isEmpty()) {
            currentpass.setError("Current Password is required");
            currentpass.requestFocus();
            return;
        }



        if (newp.isEmpty()) {
            newpass.setError("new password required");
            newpass.requestFocus();
            return;
        }

        if (newp.length() < 6) {
            newpass.setError("Minimum length of password shoud be 6");
            newpass.requestFocus();
            return;
        }

        if (conpass.isEmpty()) {
            conEdit.setError("Password confirmation required");
            conEdit.requestFocus();
            return;
        }


        if (newp.equals(conpass) == false) {
            conEdit.setError("Password Not matching");
            conEdit.requestFocus();
            return;
        }

        /////////--------- checking part ---------------------////


        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email,current);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(conpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(EditPasswordadmin.this,"Something went wrong. Please try again later",Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(EditPasswordadmin.this,"Password Successfully updated ",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else {
                    Toast.makeText(EditPasswordadmin.this,"Authentication Failed",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



    private void loaduserinfo() {

        mAuth = FirebaseAuth.getInstance();
        database =  FirebaseDatabase.getInstance();
        user =  mAuth.getCurrentUser();
        if(user!=null) {
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


        } else {
            super.onStart();
            if (mAuth.getCurrentUser() == null) {
                finish();
                startActivity(new Intent(this, loginActivity.class));
            }
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


}





