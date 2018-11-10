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

        import java.util.ArrayList;

public class orginforequest extends AppCompatActivity  implements View.OnClickListener{


    orguser org;
    String name1;
    String status;
    Intent intent;
    TextView navUsername, navUserponts, orgphone1, orgcom1, orgname1,orgcate1, orgemail1;
    View headerView;
    orguser temp;
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
        setContentView(R.layout.activity_orginforequest);



          // to insert org info in the xml
        orgphone1 = (TextView) findViewById(R.id.orgphone);
        orgcom1 = (TextView) findViewById(R.id.orgcom);
        orgname1 = (TextView) findViewById(R.id.orgname);
        orgcate1= (TextView) findViewById(R.id.orgcate);
        orgemail1= (TextView) findViewById(R.id.orgemail);

        // to add events in the buttons
       findViewById(R.id.app).setOnClickListener(this);
        findViewById(R.id.dis).setOnClickListener(this);


        Intent i = getIntent();
        temp = (orguser) i.getSerializableExtra("org");

        orgname1.setText(temp.getName());
        orgcom1.setText(temp.getRecordNO());
        orgphone1.setText(temp.getPhoneNO());
        orgcate1.setText(temp.getCat());
        orgemail1.setText(temp.getEmail());


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.app:
                Approve();
                break;
            case R.id.dis:
            Disapprove();
                break;

        }
    }


  private void Approve(){

         // change org status
         database = FirebaseDatabase.getInstance();
        ref =  database.getReference().child("client").child(temp.getUid());
        ref.child("Status").setValue("approved");

       // redirect user to the previous page
      Intent prev = new Intent(orginforequest.this, approveOrgByadmin.class);
      startActivity(prev);

      DatabaseReference mRef =  database.getReference().child("orgz").child(temp.getUid());

      mRef.child("Status").setValue("approved");


  }


   private void Disapprove(){

        // Delete user from firebse
       database = FirebaseDatabase.getInstance();
       ref =  database.getReference().child("client").child(temp.getUid());



       // i made this "dissaproved1" to distinguish between the real disapproved and the new orgs
       // they should be in waiting status not disappeoved
       ref.child("Status").setValue("disapproved1");


       // redirect user to the previous page
       Intent prev = new Intent(orginforequest.this, approveOrgByadmin.class);
       startActivity(prev);
   }





}
