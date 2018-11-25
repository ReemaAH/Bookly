package com.example.atheer.booklyv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookForAFriend extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    EditText emailaddres,message;
    String Email, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_for_afriend);

        findViewById(R.id.buttonSave).setOnClickListener(this);
        findViewById(R.id.buttonCancel).setOnClickListener(this);



        intent = getIntent();
        emailaddres = (EditText) findViewById(R.id.emailaddres);
        message = (EditText) findViewById(R.id.message);








    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSave:
                Email = emailaddres.getText().toString().trim();
                name = message.getText().toString().trim();

                if(Email.isEmpty()){
                    emailaddres.setError("Email is required");
                    emailaddres.requestFocus();
                    return;}

                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    emailaddres.setError("Please Enter a valid email");
                    emailaddres.requestFocus();
                    return;
                }

                //Creating SendMail object
                SendMail sm = new SendMail(this, Email, "[Bookly] Your friend sent you a gift!", "Hello " + name + ", \n Enjoy your reservation " + intent.getStringExtra("info") + " ! \n it is on us this time :D \n \n Bookly Team");

                //Executing sendmail to send email
                sm.execute();

                Toast.makeText(getApplicationContext(), "Sent successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this,BookForAFriend.class));


                break;

            case R.id.buttonCancel:
                startActivity(new Intent(this,Mynavigation.class));
                break;

        }
    }
}
