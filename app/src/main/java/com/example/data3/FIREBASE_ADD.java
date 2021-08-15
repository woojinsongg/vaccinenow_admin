/*package com.example.data3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FIREBASE_ADD extends AppCompatActivity {
    private Button button;
    private EditText title,dis,booking,vaccine,biseaname,address ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        button=(Button)findViewById(R.id.button);
        title =(EditText)findViewById(R.id.title);
        vaccine=(EditText)findViewById(R.id.vaccinename);
        biseaname =(EditText)findViewById(R.id.biseaname);
        dis =(EditText)findViewById(R.id.address);
        booking =(EditText)findViewById(R.id.booking);
        address =(EditText)findViewById(R.id.address);

        button=(Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type="0";
                String vaccine=vaccine.getText().toString();
                String title=title.getText().toString();
                String booking =booking.getText().toString();
                String dis =dis.getText().toString();
                String biseaname =biseaname.getText().toString();

                    type="admin";


                FIREBASE_SEARCH firebase_search = new FIREBASE_SEARCH();
                firebase_search.firebase_add(title,booking,address,biseaname,dis,);

                Intent intent=new Intent(FIREBASE_ADD.this,FIREBASE_SEARCH.class);
                startActivity(intent);
            }
        });
    }
}*/