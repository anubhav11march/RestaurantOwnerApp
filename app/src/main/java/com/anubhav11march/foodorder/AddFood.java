package com.anubhav11march.foodorder;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class AddFood extends AppCompatActivity {
    private ImageButton foodImage;
    private static final int Gall_Req = 1;
    private EditText name, desc, price;
    private Uri uri;
    private StorageReference storageRefernce = null ;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        desc = (EditText) findViewById(R.id.itemDesc);
        name = (EditText) findViewById(R.id.itemName);
        price = (EditText) findViewById(R.id.itemPrice);
        storageRefernce = FirebaseStorage.getInstance().getReference();
        foodImage = (ImageButton) findViewById(R.id.foodImage);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Item");

    }

    public void ImageButtonCLicked(View view){
        Intent Gallery_Intent = new Intent(Intent.ACTION_PICK);
        Gallery_Intent.setType("image/*");
        startActivityForResult(Gallery_Intent, Gall_Req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Gall_Req &&  resultCode == RESULT_OK){
            uri = data.getData();
            foodImage.setImageURI(uri);


        }
    }

    public void addItemClicked(View view){
        final String nameFood = name.getText().toString().trim();
        final String descFood = desc.getText().toString().trim();
        final String priceFood = price.getText().toString().trim();
        if(!TextUtils.isEmpty(nameFood) && !TextUtils.isEmpty(descFood) && !TextUtils.isEmpty(priceFood)){
            StorageReference filepath = storageRefernce.child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri downloadurl = taskSnapshot.getDownloadUrl();

                    final DatabaseReference newpOst = ref.push();
                    newpOst.child("name").setValue(nameFood);
                    newpOst.child("desc").setValue(descFood);
                    newpOst.child("price").setValue(priceFood);
                    newpOst.child("image").setValue(downloadurl.toString());
                    Toast.makeText(AddFood.this, "Food Item Added", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddFood.this, MainActivity.class));
                }
            });
        }
    }
}
