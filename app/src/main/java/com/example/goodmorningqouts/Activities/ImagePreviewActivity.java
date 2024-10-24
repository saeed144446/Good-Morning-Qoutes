package com.example.goodmorningqouts.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bt.good.morning.qoutes.wishes.images.app.R;
import com.example.goodmorningqouts.AdsManager.AppOPenASManagerSplash;
import com.example.goodmorningqouts.AdsManager.AppsController;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImagePreviewActivity extends AppCompatActivity {

    private ImageView imageView,sharebtn,whatsapp_btn,backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        imageView = findViewById(R.id.preview_image);
        sharebtn = findViewById(R.id.share_btn);
        whatsapp_btn = findViewById(R.id.whatsapp_btn);
        backbtn=findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int pos = getIntent().getIntExtra("Res_image_id", 0);
        imageView.setImageResource(pos);
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();

            }
        });
        whatsapp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsappShareImage();
            }
        });


    }

    private void whatsappShareImage() {

        // Get the drawable from the ImageView
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        try {
            // Save the image to a temporary file
            File cachePath = new File(getExternalCacheDir(), "images");
            cachePath.mkdirs(); // Create directory if not exists
            File file = new File(cachePath, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Compress the bitmap
            stream.close();

            // Get the URI for the file using FileProvider
            Uri contentUri = FileProvider.getUriForFile(this, "com.example.goodmorningqouts.fileprovider", file);

            // Create a share intent
            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.setType("image/png");
                shareIntent.setPackage("com.whatsapp");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant permission for other apps to access the file
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareImage() {
        // Get the drawable from the ImageView
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        try {
            // Save the image to a temporary file
            File cachePath = new File(getExternalCacheDir(), "images");
            cachePath.mkdirs(); // Create directory if not exists
            File file = new File(cachePath, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Compress the bitmap
            stream.close();

            // Get the URI for the file using FileProvider
            Uri contentUri = FileProvider.getUriForFile(this, "com.example.goodmorningqouts.fileprovider", file);

            // Create a share intent
            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.setType("image/png");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant permission for other apps to access the file
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}