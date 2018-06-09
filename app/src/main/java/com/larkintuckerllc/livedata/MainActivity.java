package com.larkintuckerllc.livedata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    EditText phn,pass;
    Button sub;
    String phone2,pass2;
    TextView signup;
    public FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phn=(EditText)findViewById(R.id.editText1);
        pass=(EditText)findViewById(R.id.editText2);
        sub=(Button)findViewById(R.id.button1);
        signup=(TextView)findViewById(R.id.textView);

        db = FirebaseFirestore.getInstance();


        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone2=phn.getText().toString();
                pass2=pass.getText().toString();



if(phone2.length()==0&&pass2.length()==0){
    Toast.makeText(MainActivity.this, "Enter Login Details", Toast.LENGTH_SHORT).show();
}else {
    db.collection("users")
            .whereEqualTo("phone", phone2)
            .whereEqualTo("password",pass2)
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.w("fail", "Listen failed.", e);
                        return;
                    }

                    if(value.size()==0){
                        Toast.makeText(MainActivity.this, "Login invalid", Toast.LENGTH_SHORT).show();
                    }else {
                        List<String> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("name") != null) {
                                cities.add(doc.getString("name"));
                              //  Toast.makeText(MainActivity.this, "user: " + cities.get(0), Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),dataActivity.class));
                                finish();
                            }else {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
            });

}


            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),signup.class);
                startActivity(i);

            }
        });
    }
}
