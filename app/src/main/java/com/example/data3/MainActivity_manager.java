package com.example.data3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity_manager extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextcall;
    private EditText editTextadrass;
    private Button button2;

    private Button buttonJoin;
    private FirebaseDatabase database;


    //public int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextPassword = (EditText) findViewById(R.id.editText_passWord);
        editTextName = (EditText) findViewById(R.id.editText_hospital_name);
        editTextcall = (EditText) findViewById(R.id.editText_hospital_call);
        editTextadrass = (EditText) findViewById(R.id.editText_hospital_addrass);


        buttonJoin = (Button) findViewById(R.id.btn_join);
        Intent intent = getIntent() ;

        //int a_2 = intent.getIntExtra("a__1", 0) ;
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")
                        &&!editTextcall.getText().toString().equals("")&&!editTextadrass.getText().toString().equals("")) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextName.getText().toString());
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(MainActivity_manager.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createUser(String email, String password, String name) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(MainActivity_manager.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            String uid=firebaseAuth.getCurrentUser().getUid();
                            String email=editTextEmail.getText().toString();
                            String name= editTextName.getText().toString();
                            String call= editTextcall.getText().toString();
                            String address=editTextadrass.getText().toString();
                            firebase_add(uid,email,name,call,address);
                            finish();

                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(MainActivity_manager.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void firebase_add(String uid,String email,String name,String call,String address){
        database = FirebaseDatabase.getInstance();

        DatabaseReference query = database.getReference("hospital_data");
        String key2 = query.push().getKey();
        Map<String, Object> map = new HashMap<>();
        map.put("type","admin");
        map.put("hospital_uid", uid);
        map.put("hospital_email", email);
        map.put("hospital_name", name);
        map.put("hospital_call", call);
        map.put("hospital_address", address);
        query.child(key2).setValue(map);
    }
}