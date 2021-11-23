package com.example.enforcementapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Map;
import java.util.Objects;

public class dashboard extends AppCompatActivity {

    TextView fullname;
    CardView c1, c2,c3;
    ImageView widget;
    // DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ikaen-a3973-default-rtdb.asia-southeast1.firebasedatabase.app/");
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseDatabase databaseReference;
    FirebaseDatabase Dbase = FirebaseDatabase.getInstance();
    String UID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    // FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fullname = findViewById(R.id.textTitle);
        widget = findViewById(R.id.widget);
        c1 = findViewById(R.id.card1);
        c2 = findViewById(R.id.card2);
        c3 = findViewById(R.id.card3);
        databaseReference = FirebaseDatabase.getInstance();




        DatabaseReference myref = Dbase.getReference("admins/" + UID);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usermodel user = snapshot.getValue(usermodel.class);
                assert user != null;
                String name = user.getFullname();
                fullname.setText(name);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        widget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, setting.class));
            }
        });

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, profile.class));
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        dashboard.this
                );

                intentIntegrator.setPrompt("for flashuse volume up key");

                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();

            }
        });



        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, activityHistory.class));
                startActivity(new Intent(dashboard.this, Login.class));
            }
        });

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                resultCode,resultCode,data
        );

        if (intentResult.getContents() != null){

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    dashboard.this
            );

            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();

        }else{
            Toast.makeText(getApplicationContext()
            , "OOPS you did'nt scan anything", Toast.LENGTH_SHORT).show();
        }
    }
}