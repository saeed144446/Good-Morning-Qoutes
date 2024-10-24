package com.example.goodmorningqouts.Adpaters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bt.good.morning.qoutes.wishes.images.app.R;
import com.bumptech.glide.Glide;
import com.example.goodmorningqouts.Activities.ImagePreviewActivity;
import com.example.goodmorningqouts.Utils.itemlist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<itemlist> imageList;

    public ImageListAdapter(Context context, ArrayList<itemlist> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        itemlist item = imageList.get(position);
        Glide.with(context).load(item.getImageResId()) // pass the image resource ID or URL here
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ImagePreviewActivity.class);
                intent.putExtra("Res_image_id", item.getImageResId());
                context.startActivity(intent);

            }
        });


        holder.sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage(holder.imageView);

            }
        });
        holder.whatsappbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareWhatsappImage(holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, favoritebtn, sharebtn, whatsappbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            sharebtn = itemView.findViewById(R.id.sharebtn);
            whatsappbtn = itemView.findViewById(R.id.whatsappbtn);

        }
    }

    private void shareImage(ImageView imageView) {
        // Get the drawable from the ImageView
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if (drawable == null) {
            return; // If no drawable is available, exit early
        }
        Bitmap bitmap = drawable.getBitmap();

        try {
            // Save the image to a temporary file
            File cachePath = new File(context.getExternalCacheDir(), "images");
            cachePath.mkdirs(); // Create directory if not exists
            File file = new File(cachePath, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Compress the bitmap
            stream.close();

            // Get the URI for the file using FileProvider
            Uri contentUri = FileProvider.getUriForFile(context, "com.example.goodmorningqouts.fileprovider", file);

            // Create a share intent
            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.setType("image/png");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant permission for other apps to access the file
                context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void shareWhatsappImage(ImageView imageView) {
        // Get the drawable from the ImageView
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if (drawable == null) {
            return; // If no drawable is available, exit early
        }
        Bitmap bitmap = drawable.getBitmap();

        try {
            // Save the image to a temporary file
            File cachePath = new File(context.getExternalCacheDir(), "images");
            cachePath.mkdirs(); // Create directory if not exists
            File file = new File(cachePath, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Compress the bitmap
            stream.close();

            // Get the URI for the file using FileProvider
            Uri contentUri = FileProvider.getUriForFile(context, "com.example.goodmorningqouts.fileprovider", file);

            // Create a share intent
            if (contentUri != null) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.setPackage("com.whatsapp");
                shareIntent.setType("image/png");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant permission for other apps to access the file
                context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
