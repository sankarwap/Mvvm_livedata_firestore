package com.larkintuckerllc.livedata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sankar on 7/21/2017.
 */

public class signup extends Activity {
    Context context = this;

    EditText name, phone, pass;
    Button reg;
    TextView login;
    String names, phones, passs, otpNumber;
    public FirebaseFirestore db;
    String response = "0";
    List<String> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        name = (EditText) findViewById(R.id.editText1);
        phone = (EditText) findViewById(R.id.editText2);
        pass = (EditText) findViewById(R.id.editText3);
        reg = (Button) findViewById(R.id.button1);
        login = (TextView) findViewById(R.id.textView);

        db = FirebaseFirestore.getInstance();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                names = name.getText().toString().trim();
                phones = phone.getText().toString().trim();
                passs = pass.getText().toString().trim();
                if (names.length() == 0 && phones.length() == 0 && passs.length() == 0) {
                    Toast.makeText(context, "enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    getdata();
                }
            }

        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void getdata() {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name.getText().toString().trim());
        user.put("phone", phone.getText().toString().trim());
        user.put("password", pass.getText().toString().trim());
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(signup.this, "success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), dataActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(signup.this, "error", Toast.LENGTH_SHORT).show();
                        Log.w("failure", "Error adding document", e);
                    }
                });
    }

}
