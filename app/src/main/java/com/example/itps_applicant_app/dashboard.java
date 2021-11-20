package com.example.itps_applicant_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;
import java.util.Objects;


public class dashboard extends AppCompatActivity {

    TextView  fullname,more, celciusPer, humidtyPer, pressurePer;
    CardView c1, c2,c3,c4;
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




        DatabaseReference myref = Dbase.getReference("users/" + UID);

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



        DatabaseReference pres = database.getReference("Pressure/");

        pres.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                Map<String, Object> pres = (Map<String, Object>) snapshot.getValue();
                Log.d("owo","dapat" + pres.get("Hpa"));
                pressurePer.setText(pres.get("Hpa").toString());
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
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this, request.class));
            }
        });



        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this, activityHistory.class));
                startActivity(new Intent(dashboard.this, status.class));
            }
        });


    }
}