package com.example.itps_applicant_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.Normalizer;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class request extends AppCompatActivity {

    DatabaseReference dbase;
    EditText policeStn,fullname,ic,citizenship,address,VeichleType,veichlePlate;
    EditText dependent,DepatureDate,returnDate,destinationAdd,travelReas;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        dbase = FirebaseDatabase.getInstance().getReference().child("RequestForm");

       policeStn = findViewById(R.id.PoliceStation);
       fullname = findViewById(R.id.fullname);
       ic = findViewById(R.id.Ic);
       citizenship = findViewById(R.id.citizenship);
       address = findViewById(R.id.Address);
       VeichleType = findViewById(R.id.Veichle);
       veichlePlate = findViewById(R.id.carPlate);
       dependent = findViewById(R.id.Dependent);
       DepatureDate = findViewById(R.id.depatureDate);
       returnDate = findViewById(R.id.returnDate);
       destinationAdd = findViewById(R.id.DestinationAddress);
       travelReas = findViewById(R.id.TravelReasons);


        final Button icBtn = findViewById(R.id.uploadIcCopy);
        final Button supportBtn = findViewById(R.id.AddSupportingDetails);
        final Button RoadtaxBtn = findViewById(R.id.uploadRoadtax);
        final Button otherBtn = findViewById(R.id.uploadOther);
        final Button confirmBtn = findViewById(R.id.ConfirmBtn);


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createForm();
                            }

        });


    }
    private void createForm(){

         String fullnameTxt = fullname.getText().toString();
         String icTxt = ic.getText().toString();
         String policeStnTxt = policeStn.getText().toString();
         String citizenshipTxt = citizenship.getText().toString();
         String addressTxt = address.getText().toString();
         String VeichleTypeTxt = VeichleType.getText().toString();
         String veichlePlateTxt = veichlePlate.getText().toString();
         String dependentTxt = dependent.getText().toString();
         String DepatureDateTxt = DepatureDate.getText().toString();
         String returnDateTxt = returnDate.getText().toString();
         String destinationAddTxt = destinationAdd.getText().toString();
         String travelReasTxt = travelReas.getText().toString();

         formmodel formmodel = new formmodel(travelReasTxt,destinationAddTxt,returnDateTxt,DepatureDateTxt,dependentTxt,veichlePlateTxt,VeichleTypeTxt,addressTxt,citizenshipTxt,policeStnTxt,icTxt,fullnameTxt);

         dbase.push().setValue(formmodel);
         Toast.makeText(request.this,"data inserted", Toast.LENGTH_SHORT).show();

    }




}