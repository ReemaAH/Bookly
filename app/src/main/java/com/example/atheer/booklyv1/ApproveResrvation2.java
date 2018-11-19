package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApproveResrvation2 extends AppCompatActivity  implements View.OnClickListener{
    Reservation res;
    String name1;
    String status;
    Intent intent;
    TextView navUsername, navUserponts, service, date, time,coupon;
    View headerView;
    String resNum;
    Reservation temp;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_resrvation2);


        // to insert org info in the xml
        service = (TextView) findViewById(R.id.service);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        coupon= (TextView) findViewById(R.id.coupon);

        // to add events in the buttons
        findViewById(R.id.dis).setOnClickListener(this);

        Intent i = getIntent();
        temp = (Reservation) i.getSerializableExtra("reservation");

        service.setText(temp.getService());
        date.setText(temp.getDate());
        time.setText(temp.getTime());
        if (!temp.getCoupon().equals(null))
        coupon.setText(" "+temp.getCoupon());
        resNum=temp.getResNum();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.dis:
                approve();
                break;

        }
    }

    public void approve(){
        database = FirebaseDatabase.getInstance();
        ref =  database.getReference().child("reservaiton").child(resNum);
        //ref.setValue(null);

        boolean boo=true;

        // i made this "dissaproved1" to distinguish between the real disapproved and the new orgs
        // they should be in waiting status not disappeoved
        ref.child("approved").setValue(boo);


        // redirect user to the previous page
        Intent prev = new Intent(ApproveResrvation2.this, ApproveResrvation.class);
        startActivity(prev);
    }
}
