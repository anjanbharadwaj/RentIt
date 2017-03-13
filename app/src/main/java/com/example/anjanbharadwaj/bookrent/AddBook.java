package com.example.anjanbharadwaj.bookrent;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AddBook extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    //ArrayList<Map> bitmapArrayList = new ArrayList<>();
    Bitmap mImageBitmap;
    File photoFile;

    EditText bookname;
    EditText price;
    Button btn;
    /*
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> list = new ArrayList<>();
    */

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Books");


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        //listview = (ListView)findViewById(R.id.listView);
        //arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        //imageAdapter = new ImageAdapter(getApplicationContext());
        //listview.setAdapter(arrayAdapter);
        bookname = (EditText)findViewById(R.id.bookname);
        price = (EditText)findViewById(R.id.price);

        btn = (Button) findViewById(R.id.button);
        /*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pic = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(getApplicationContext(), Popup.class);
                intent.putExtra("Picture", pic);
                startActivity(intent);
            }
        });
        */
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                    /*
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.i("HomeActivity", "IOException");
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                    */
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                // startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                //  }
                //}

                //firebase code
                Map<String, Object> map1 = new HashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map1.put(bookname.getText().toString(), "");
                map2.put("Price",price.getText().toString());
                root.updateChildren(map1);
                root.child(bookname.getText().toString()).updateChildren(map2);
                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while(i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());

                }
                //list.clear();
                //list.addAll(set);
                //imageAdapter.notifyDataSetChanged();
                //arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            StorageReference ref = storageRef.child("Books").child(bookname.getText().toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data1 = baos.toByteArray();

            UploadTask uploadTask = ref.putBytes(data1);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    //arrayAdapter.notifyDataSetChanged();
                }
            });

        }
    }



}






