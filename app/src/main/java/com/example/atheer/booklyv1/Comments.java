package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Comments extends AppCompatActivity implements View.OnClickListener {
String name2;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference ref;
    private String userId;
    private FirebaseUser user;
    private Orgz temp;
    EditText comments;
    String comment1;
    String email = "";
    DateFormat dateFormat ;
    Date date ;
    String date1;
    users tempuser;
   // String email1;
    android.widget.ListView ListView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    commentobject com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

 // getting the object from Bookservice class
        Intent i = getIntent();
        name2 =  i.getStringExtra("org");


        comments = (EditText) findViewById(R.id.comments);
        findViewById(R.id.btn_send).setOnClickListener(this);


        com = new commentobject();
        ListView = (ListView) findViewById(R.id.ListView);
        database = FirebaseDatabase.getInstance();
//        ref =  database.getReference().child("comments").child(temp.getUid());
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.comment_layout,R.id.commentinfo,list);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference itemsRef = rootRef.child("comments");;

        // HERE
        Query query=itemsRef.orderByChild("orgID").equalTo(name2);
        query.addValueEventListener(new ValueEventListener() {


            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String str;
                    com = ds.getValue(commentobject.class);

                    if (!com.equals(null))
                        if(!(com==null))
//                        if (!com.getDate().toString().equals(null))
                    {
                        str = com.getValue().toString() + "\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + com.getDate();
                        list.add(str);
                    }
                }

                    ListView.setAdapter(adapter);



//
            }

            @Override
            public void onCancelled(DatabaseError databaseError){


            }
        });





    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_send: {

                comment1 = comments.getText().toString().trim();

                mAuth = FirebaseAuth.getInstance();
                database =  FirebaseDatabase.getInstance();
                user =  mAuth.getCurrentUser();
                userId = user.getUid();
                ref =  database.getReference().child("client").child(userId);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      String email1="";
                        tempuser= new users();
                        for(DataSnapshot ds: dataSnapshot.getChildren() ){
                            email1 = dataSnapshot.child("email").getValue(String.class);

                            tempuser.setEmail(email1);
                            // dummy data just to prevent null errors
                            tempuser.setName("Temp");
                            tempuser.setphoneNO("0556422117");
                        }


                        Intent i = getIntent();
                     String   name = i.getStringExtra("org");
//setTitle(temp.getUid());
                        Toast.makeText(Comments.this, name, Toast.LENGTH_LONG).show();
                dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                date = new Date();
                date1= dateFormat.format(date);
                Random rand = new Random();
                int n = rand.nextInt(2000) + 1;
                commentobject obj=new commentobject(date1,tempuser.getEmail(),comment1,name);
                obj.setDate(date1);
                obj.setOrgID(name);
                obj.setValue(comment1);
                obj.setWriterEmail(tempuser.getEmail());
                DatabaseReference ref = database.getReference().child("comments").child(n + "");
                ref.setValue(obj);
                DatabaseReference ref2 = database.getReference().child("comments").child(n + "");
               ref2.child("value").setValue(comment1);
                DatabaseReference ref3 = database.getReference().child("comments").child(n + "");
                ref3.child("date").setValue(date1);
                DatabaseReference ref4 = database.getReference().child("comments").child(n + "");
                ref4.child("orgID").setValue(name);
                DatabaseReference ref5 = database.getReference().child("comments").child(n + "");
                ref5.child("writerEmail").setValue(tempuser.getEmail());

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

             //   startActivity(new Intent(this,Comments.class));
// child(temp.getUid())
                break;
            }
        }

    }


}

