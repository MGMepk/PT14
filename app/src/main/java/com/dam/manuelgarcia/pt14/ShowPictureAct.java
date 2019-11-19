package com.dam.manuelgarcia.pt14;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public class ShowPictureAct extends AppCompatActivity {

    private Bitmap bitmap;
    private static final int REQUEST_IMAGE_PICK = 40;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        setTitle("Mostra foto");

        imageView = findViewById(R.id.imgView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);  //ACTION_OPEN_DOCUMENT
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                Log.d("test", "onActivityResult:not ok ");

                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });


    }

    //https://developer.android.com/training/basics/intents/result

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK)

            try (InputStream stream = getContentResolver().openInputStream(data.getData());) {
                // recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                bitmap = BitmapFactory.decodeStream(stream);
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("test", e.getMessage() + e.getCause());
            }
        else if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_CANCELED) {
            Log.d("test", "onActivityResult:not ok ");
        }
    }


}
