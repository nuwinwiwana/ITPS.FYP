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

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class request extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://itps-1c7c7-default-rtdb.asia-southeast1.firebasedatabase.app/");
    String UID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(); // edited
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //edited
    ImageView profileImageView;
    EditText profileEmail;
    FirebaseAuth fAuth;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);



        final EditText policeStn = findViewById(R.id.PoliceStation);
        final EditText fullname = findViewById(R.id.fullname);
        final EditText ic = findViewById(R.id.Ic);
        final EditText citizenship = findViewById(R.id.citizenship);
        final EditText address = findViewById(R.id.Address);
        final EditText VeichleType = findViewById(R.id.Veichle);
        final EditText veichlePlate = findViewById(R.id.carPlate);
        final EditText dependent = findViewById(R.id.Dependent);
        final EditText DepatureDate = findViewById(R.id.depatureDate);
        final EditText returnDate = findViewById(R.id.returnDate);
        final EditText destinationAdd = findViewById(R.id.DestinationAddress);
        final EditText travelReas = findViewById(R.id.TravelReasons);


        final Button icBtn = findViewById(R.id.uploadIcCopy);
        final Button supportBtn = findViewById(R.id.AddSupportingDetails);
        final Button RoadtaxBtn = findViewById(R.id.uploadRoadtax);
        final Button otherBtn = findViewById(R.id.uploadOther);
        final Button confirmBtn = findViewById(R.id.ConfirmBtn);


        confirmBtn.setOnClickListener(v -> {
            if (policeStn.getText().toString().isEmpty() || fullname.getText().toString().isEmpty() || ic.getText().toString().isEmpty() ||citizenship.getText().toString().isEmpty() || address.getText().toString().isEmpty() || veichlePlate.getText().toString().isEmpty() || VeichleType.getText().toString().isEmpty() || dependent.getText().toString().isEmpty() || DepatureDate.getText().toString().isEmpty() || returnDate.getText().toString().isEmpty() || destinationAdd.getText().toString().isEmpty() || travelReas.getText().toString().isEmpty()  ) {
                Toast.makeText(request.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            final String email = profileEmail.getText().toString();
            user.updateEmail(email).addOnSuccessListener(unused -> {
                Toast.makeText(request.this, "saved", Toast.LENGTH_SHORT).show();

                databaseReference.child("request").child(user.getUid()).child("email").setValue(email);
                databaseReference.child("request").child(user.getUid()).child("fullname").setValue(fullname);
                databaseReference.child("request").child(user.getUid()).child("policeStn").setValue(policeStn);
                databaseReference.child("request").child(user.getUid()).child("ic").setValue(ic);
                databaseReference.child("request").child(user.getUid()).child("citizenship").setValue(citizenship);
                databaseReference.child("request").child(user.getUid()).child("address").setValue(address);
                databaseReference.child("request").child(user.getUid()).child("VeichleType").setValue(VeichleType);
                databaseReference.child("request").child(user.getUid()).child("veichlePlate").setValue(veichlePlate);
                databaseReference.child("request").child(user.getUid()).child("dependent").setValue(dependent);
                databaseReference.child("request").child(user.getUid()).child("DepatureDate").setValue(DepatureDate);
                databaseReference.child("request").child(user.getUid()).child("returnDate").setValue(returnDate);
                databaseReference.child("request").child(user.getUid()).child("destinationAdd").setValue(destinationAdd);
                databaseReference.child("request").child(user.getUid()).child("travelReas").setValue(travelReas);

            }).addOnFailureListener(e -> Toast.makeText(request.this, e.getMessage(), Toast.LENGTH_SHORT).show());

        });

        icBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });




    }


}