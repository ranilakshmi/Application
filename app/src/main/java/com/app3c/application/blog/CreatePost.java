package com.app3c.application.blog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app3c.application.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class CreatePost extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://geriatric-care-66697-default-rtdb.firebaseio.com/");
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    private ImageView imageView;
    private ImageView iv_mic_title;
    private ImageView iv_mic_content;
    private TextView title;
    private TextView content;
    private static final int REQUEST_CODE_SPEECH_INPUT_TITLE = 1;
    private static final int REQUEST_CODE_SPEECH_INPUT_CONTENT = 2;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;
    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    String dateTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        //EditText postTitle = findViewById(R.id.post_title);
        //CheckBox checkbox = findViewById(R.id.checkbox_image);

        iv_mic_title = findViewById(R.id.iv_mic_title);
        iv_mic_content = findViewById(R.id.iv_mic_content);
        title = findViewById(R.id.post_title);
        content = findViewById(R.id.post_content);

        iv_mic_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    //someActivityResultLauncher.launch(intent);
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT_TITLE);
                }
                catch(ActivityNotFoundException e) {
                    String appPackageName = "com.google.android.googlequicksearchbox";

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));

                }
            }
        });

        iv_mic_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT_CONTENT);
                    //someActivityResultLauncher.launch(intent);
                }
                catch (Exception e) {
                    Toast
                            .makeText(CreatePost.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


        Button uploadImageButton = findViewById(R.id.uploadImageBtn);
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }

        });

        imageView = findViewById(R.id.imageView);
        //EditText postContent = findViewById(R.id.post_content);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Button publishPostButton = findViewById(R.id.publishBtn);
        publishPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //StorageReference ref =  uploadImage();
                //publishPost(view,ref);
                publishPost(view);
            }
        });
    }
    private String getDateAndTime(){
        Long dateValueInLong = System.currentTimeMillis();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        Log.i("datetime",dateTime);
        return dateTime;
    }
    private void publishPost(View view)
    {
        //private void publishPost(View view,StorageReference ref) {
        EditText PostTitle = findViewById(R.id.post_title);
        EditText PostContent = findViewById(R.id.post_content);
        String title = PostTitle.getText().toString();
        String content = PostContent.getText().toString();
        String date = getDateAndTime();
        BlogPost post = new BlogPost(title,content,date);
        //String imageurl = ref.toString();
        //BlogPost post = new BlogPost(title,content,imageurl);
        databaseReference.child("posts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Context context = getApplicationContext();
                CharSequence text = "Success";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                finish();
                databaseReference.child("post").push().setValue(post);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void onCheckboxClicked(View view) {
        Button uploadImageButton = findViewById(R.id.uploadImageBtn);
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

//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                        ArrayList<String> results = result.getData().getStringArrayListExtra(
//                                RecognizerIntent.EXTRA_RESULTS);
//                        content.setText(
//                                Objects.requireNonNull(results).get(0));
//                    }
//                }
//            });
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and resultCode is RESULT_OK then set image in the image view
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
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_CODE_SPEECH_INPUT_TITLE){
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                title.setText(
                        Objects.requireNonNull(result).get(0));
            }

        }
        if (requestCode == REQUEST_CODE_SPEECH_INPUT_CONTENT){
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                content.setText(
                        Objects.requireNonNull(result).get(0));
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
                                            .makeText(CreatePost.this,
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
                                    .makeText(CreatePost.this,
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
        else{
            return null;
        }
    }
}