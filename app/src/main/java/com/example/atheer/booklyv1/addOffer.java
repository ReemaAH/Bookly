package com.example.atheer.booklyv1;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

public class addOffer extends AppCompatActivity {
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

    private static final int PICK_IMAGE_REQUEST = 1;

    private static final int CHOOSE_IMAGE = 101;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEditTextFileName;
    private EditText mEditTextFilepre;
    private EditText mEditTextFilesDate;
    private EditText mEditTextFileeDate;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private StorageTask mUploadTask;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    ArrayList<String> data1;
    ArrayList<String> data2;
    DatePickerDialog datePickerDialog;

    int year;
    int month;
    int day;

    int year2;
    int month2;
    int day2;

    String dateval = "";
    String dateval2 = "";

    String sDate, eDate, name,pre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        setTitle("Add Offer");

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

                            startActivity(new Intent(addOffer.this,settingsorg.class));
                        } else if (id == R.id.logoutId){

                            FirebaseAuth.getInstance().signOut();
                            finish();
                            Intent signOUT=new Intent(addOffer.this,loginActivity.class);
                            startActivity(signOUT);


                        } else if (id == R.id.homeId){

                            startActivity(new Intent(addOffer.this,mynav.class));

                        } else if (id == R.id.servicesId){

                            startActivity(new Intent(addOffer.this,orgServices.class));

                        } else if (id == R.id.ReservationsId){

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        } else if (id == R.id.ReportsId){

                            //    startActivity(new Intent(mynav.this,orgServices.class));

                        }


                        return true;
                    }
                });

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        // mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mEditTextFilepre= findViewById(R.id.edit_text_file_percentage);
        mEditTextFilesDate= findViewById(R.id.edit_text_file_sdate);
        mEditTextFileeDate= findViewById(R.id.edit_text_file_edate);


        mEditTextFilesDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Calendar calander = Calendar.getInstance();
                year = calander.get(Calendar.YEAR);
                month = calander.get(Calendar.MONTH);
                day = calander.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(addOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        dateval = day+"-"+(month+1)+"-"+year;

                        mEditTextFilesDate.setText(dateval);
                    }
                },year,month,day);

                datePickerDialog.show();

            }

        });

        mEditTextFileeDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Calendar calander = Calendar.getInstance();
                year2 = calander.get(Calendar.YEAR);
                month2 = calander.get(Calendar.MONTH);
                day2 = calander.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(addOffer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        dateval2 = day+"-"+(month+1)+"-"+year;

                        mEditTextFileeDate.setText(dateval2);
                    }
                },year2,month2,day2);

                datePickerDialog.show();

            }

        });

        mStorageRef= FirebaseStorage.getInstance().getReference("offer");
        mDatabaseRef= database.getReference().child("client").child(userId).child("offer");
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(addOffer.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    UploadFile();
                }
            }
        });


    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            mImageView.setImageURI(mImageUri);
            //  Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver CR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(CR.getType(uri));
    }

    public void UploadFile(){


        sDate=mEditTextFilesDate.getText().toString().trim();
        eDate=mEditTextFileeDate.getText().toString().trim();
        name= mEditTextFileName.getText().toString().trim();
        pre=mEditTextFilepre.getText().toString().trim();
        if(mImageUri!=null){
            StorageReference fileRefrence=mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            mUploadTask =    fileRefrence.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler=new Handler();
                            handler.postDelayed(new Runnable(){
                                @Override
                                public void run(){
                                    mProgressBar.setProgress(0);
                                }
                            },5000);
                            Toast.makeText(addOffer.this, "Upload successful", Toast.LENGTH_LONG).show();
                            uloadOffers upld=new uloadOffers(mEditTextFileName.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString(),sDate,eDate,pre);
                            String uploadId=mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upld);
                     //       mDatabaseRef.child("percentage").setValue(pre);
                     //       mDatabaseRef.child("sDate").setValue(sDate);
                     //       mDatabaseRef.child("eDate").setValue(eDate);
                      //      mDatabaseRef.child("name").setValue(name);
                            //   mDatabaseRef.child(uploadId).setValue(mEditTextFileName.getText().toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addOffer.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int)progress);
                        }
                    });
        }else{
            Toast.makeText(this, "NO file selected", Toast.LENGTH_LONG).show();
        }
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

}
