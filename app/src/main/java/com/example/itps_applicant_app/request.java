package com.example.itps_applicant_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Objects;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class request extends AppCompatActivity {


    EditText policeStn,nama,id,citizenship,address,VeichleType,veichlePlate;
    EditText dependent,DepatureDate,returnDate,destinationAdd,travelReas;
    private Button icBtn,supportBtn,RoadtaxBtn,otherBtn,confirmBtn;
    private ImageView IcImg,supportImg, roadImg,otherImg;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;

    private FirebaseAuth mAuth;

    FirebaseStorage storage;
    StorageReference storageReference;


    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    formmodel formmodel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(
                Color.parseColor("#0F9D58"));
        actionBar.setBackgroundDrawable(colorDrawable);


       policeStn = findViewById(R.id.PoliceStation);
       nama = findViewById(R.id.fullname);
       id = findViewById(R.id.Ic);
       citizenship = findViewById(R.id.citizenship);
       address = findViewById(R.id.Address);
       VeichleType = findViewById(R.id.Veichle);
       veichlePlate = findViewById(R.id.carPlate);
       dependent = findViewById(R.id.Dependent);
       DepatureDate = findViewById(R.id.depatureDate);
       returnDate = findViewById(R.id.returnDate);
       destinationAdd = findViewById(R.id.DestinationAddress);
       travelReas = findViewById(R.id.TravelReasons);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("RequestForm");

        // initializing our object
        // class variable.
        formmodel = new formmodel();


        icBtn = findViewById(R.id.uploadIcCopy);
        supportBtn = findViewById(R.id.AddSupportingDetails);
        RoadtaxBtn = findViewById(R.id.uploadRoadtax);
        otherBtn = findViewById(R.id.uploadOther);
        confirmBtn = findViewById(R.id.ConfirmBtn);

        IcImg = findViewById(R.id.IcImage);
        supportImg = findViewById(R.id.SupportingDetailsImage);
        roadImg = findViewById(R.id.RoadtaxImage);
        otherImg = findViewById(R.id.OtherImage);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        icBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                SelectImage();
            }
        });

        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                SelectImage1();
            }
        });

        RoadtaxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                SelectImage2();
            }
        });



        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                SelectImage3();
            }
        });



        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();
                uploadImage1();
                uploadImage2();
                uploadImage3();




                String namaTxt = nama.getText().toString();
                String idTxt = id.getText().toString();
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

                // below line is for checking weather the
                // edittext fields are empty or not.
                if ( TextUtils.isEmpty(destinationAddTxt) && TextUtils.isEmpty(travelReasTxt) && TextUtils.isEmpty(returnDateTxt) && TextUtils.isEmpty(DepatureDateTxt) && TextUtils.isEmpty(dependentTxt) && TextUtils.isEmpty(veichlePlateTxt) && TextUtils.isEmpty(VeichleTypeTxt) && TextUtils.isEmpty(policeStnTxt) && TextUtils.isEmpty(namaTxt) && TextUtils.isEmpty(idTxt) && TextUtils.isEmpty(citizenshipTxt) && TextUtils.isEmpty(addressTxt)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(request.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(idTxt,travelReasTxt,destinationAddTxt,returnDateTxt,returnDateTxt,DepatureDateTxt,dependentTxt,veichlePlateTxt,VeichleTypeTxt,addressTxt,citizenshipTxt,policeStnTxt,namaTxt);
                }

            }

        });


    }
    private void SelectImage()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("ICimage/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }
    private void SelectImage1()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("SupportImage/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    private void SelectImage2()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("RoadImage/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    private void SelectImage3()
    {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("OtherImage/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                IcImg.setImageBitmap(bitmap);
                supportImg.setImageBitmap(bitmap);
                roadImg.setImageBitmap(bitmap);
                otherImg.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "ICimage/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(request.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(request.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

    private void uploadImage1()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "SupportImage/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(request.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(request.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
    private void uploadImage2()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "RoadImage/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(request.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(request.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
    private void uploadImage3()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "OtherImage/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(request.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(request.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }


    private void addDatatoFirebase(String travelReasTxt, String destinationAddTxt, String returnDateTxt, String DepatureDateTxt, String dependentTxt, String veichlePlateTxt, String VeichleTypeTxt, String namaTxt, String idTxt, String policeStnTxt, String citizenshipTxt, String addressTxt, String txt){
        formmodel.settravelReas(travelReasTxt);
        formmodel.setdestinationAdd(destinationAddTxt);
        formmodel.setreturnDate(returnDateTxt);
        formmodel.setDepatureDate(DepatureDateTxt);
        formmodel.setdependent(dependentTxt);
        formmodel.setveichlePlate(veichlePlateTxt);
        formmodel.setVeichleType(VeichleTypeTxt);
        formmodel.setnama(namaTxt);
        formmodel.setid(idTxt);
        formmodel.setpoliceStn(policeStnTxt);
        formmodel.setcitizenship(citizenshipTxt);
        formmodel.setaddress(addressTxt);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild(user.getUid())) {
                    Toast.makeText(request.this, "email is already registered", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("RequestForm").child(user.getUid()).child("travelReasons").setValue(travelReasTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("DestinationAddress").setValue(destinationAddTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("returnDate").setValue(returnDateTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("DepatureDate").setValue(DepatureDateTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("Dependent").setValue(dependentTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("VeichlePlate").setValue(veichlePlateTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("VeichleType").setValue(VeichleTypeTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("nama").setValue(namaTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("id").setValue(idTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("policeStation").setValue(policeStnTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("Citizenship").setValue(citizenshipTxt);
                    databaseReference.child("RequestForm").child(user.getUid()).child("Address").setValue(addressTxt);




                    Toast.makeText(request.this, "user registered succsesfully", Toast.LENGTH_SHORT).show();

                }


                // after adding this data we are showing toast message.
                Toast.makeText(request.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(request.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });



    }





}