package com.app3c.application.feed;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class CreateEvent extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    private ImageView imageView;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    //StorageReference storageReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        final EditText eventName = findViewById(R.id.event_name);
        final EditText organizationName = findViewById(R.id.organization_name);
        final EditText description = findViewById(R.id.event_description);
        final EditText eventContact = findViewById(R.id.event_contact);
        final EditText eventLocation = findViewById(R.id.event_location);
        final DatePicker datepicker = findViewById(R.id.datepicker);
        /*
        CheckBox checkbox = findViewById(R.id.checkbox_image);

        Button uploadImageButton = findViewById(R.id.uploadImageBtn);
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });

        imageView = findViewById(R.id.image2);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
         */

        Button RegisterBtn = findViewById(R.id.eventRegisterBtn);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String EventName = eventName.getText().toString();
                final String OrgName = organizationName.getText().toString();
                final String desc = description.getText().toString();
                final String contact = eventContact.getText().toString();
                final String venue = eventLocation.getText().toString();
                final int day = datepicker.getDayOfMonth();
                final int month = datepicker.getMonth();
                final int year = datepicker.getYear();

                if (EventName.isEmpty() || desc.isEmpty()||contact.isEmpty()||OrgName.isEmpty()||venue.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter all the details";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    Event_Post event_post = new Event_Post(EventName,OrgName,desc,contact,venue,day,month,year);
                    /*
                    StorageReference ref = uploadImage();
                    event_post.setImageurl(ref);
                    databaseReference.child("event").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Context context = getApplicationContext();
                            CharSequence text = "Success";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            finish();
                            databaseReference.child("event").push().setValue(event_post);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                     */
                    FirebaseHelper helper = new FirebaseHelper(databaseReference);
                    helper.save(event_post,"event");
                    Context context = getApplicationContext();
                    CharSequence text = "Event Registered";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();
                }
            }
        });
    }

    /*
    public void onCheckboxClicked(View view) {
        Button uploadImageButton = findViewById(R.id.uploadImageBtn);
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        if (checked){
            uploadImageButton.setVisibility(View.VISIBLE);
        }
        else{
            uploadImageButton.setVisibility(View.GONE);
        }
    }

    // Select Image method
    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }
    // Override onActivityResult method
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                int width = 60;
                int height = 60;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                imageView.setLayoutParams(parms);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }
    private StorageReference uploadImage() {
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
                            "images/"
                                    + UUID.randomUUID().toString());

            //url = ref.getDownloadUrl().toString();

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(CreateEvent.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(CreateEvent.this,
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
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
            return ref;
        }
        else {
            return null;
        }
    }
     */
}