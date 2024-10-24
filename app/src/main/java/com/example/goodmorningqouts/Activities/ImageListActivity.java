package com.example.goodmorningqouts.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;


import com.bt.good.morning.qoutes.wishes.images.app.R;
import com.example.goodmorningqouts.Adpaters.ImageListAdapter;
import com.example.goodmorningqouts.Utils.itemlist;

import java.util.ArrayList;

public class ImageListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String category;
    private ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        recyclerView = findViewById(R.id.recycler_view);
        backbtn=findViewById(R.id.backbtn);

        // Get the category from the intent
        category = getIntent().getStringExtra("category");

        // Get image list based on the category
        ArrayList<itemlist> images = getImagesForCategory(category);

        // Set up the RecyclerView with the adapter
        ImageListAdapter adapter = new ImageListAdapter(this, images);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // Function to return image list based on the category
    private ArrayList<itemlist> getImagesForCategory(String category) {
        ArrayList<itemlist> images = new ArrayList<>();

        switch (category) {
            case "morning":
                images.add(new itemlist(R.drawable.morning1));
                images.add(new itemlist(R.drawable.morning2));
                images.add(new itemlist(R.drawable.morning3));
                images.add(new itemlist(R.drawable.morning4));
                images.add(new itemlist(R.drawable.morning5));
                images.add(new itemlist(R.drawable.morning7));
                images.add(new itemlist(R.drawable.morning6));
                images.add(new itemlist(R.drawable.morning8));
                images.add(new itemlist(R.drawable.morning9));
                images.add(new itemlist(R.drawable.morning10));
                images.add(new itemlist(R.drawable.morning11));
                images.add(new itemlist(R.drawable.morning12));
                break;
            case "afternoon":
                images.add(new itemlist(R.drawable.afternoun1));
                images.add(new itemlist(R.drawable.afternoun2));
                images.add(new itemlist(R.drawable.afternoun3));
                images.add(new itemlist(R.drawable.afternoun4));
                images.add(new itemlist(R.drawable.afternoun5));
                images.add(new itemlist(R.drawable.afternoun7));
                images.add(new itemlist(R.drawable.afternoun8));
                images.add(new itemlist(R.drawable.afternoun9));
                images.add(new itemlist(R.drawable.afternoun10));


                break;
            case "evening":
                images.add(new itemlist(R.drawable.evening1));
                images.add(new itemlist(R.drawable.evening2));
                images.add(new itemlist(R.drawable.evening3));
                images.add(new itemlist(R.drawable.evening4));
                images.add(new itemlist(R.drawable.evening6));


                break;
            case "night":
                images.add(new itemlist(R.drawable.night1));
                images.add(new itemlist(R.drawable.night2));
                images.add(new itemlist(R.drawable.night3));
                images.add(new itemlist(R.drawable.night4));
                images.add(new itemlist(R.drawable.night5));
                images.add(new itemlist(R.drawable.night6));

                break;
            case "card1":
                images.add(new itemlist(R.drawable.night1));
                images.add(new itemlist(R.drawable.night2));
                images.add(new itemlist(R.drawable.evening1));
                images.add(new itemlist(R.drawable.evening2));
                images.add(new itemlist(R.drawable.afternoun1));
                images.add(new itemlist(R.drawable.afternoun2));
                images.add(new itemlist(R.drawable.afternoun3));
                images.add(new itemlist(R.drawable.morning1));
                images.add(new itemlist(R.drawable.morning2));
                images.add(new itemlist(R.drawable.morning3));

                break;

            case "card2":
                images.add(new itemlist(R.drawable.morning4));
                images.add(new itemlist(R.drawable.morning5));
                images.add(new itemlist(R.drawable.morning6));
                images.add(new itemlist(R.drawable.afternoun5));
                images.add(new itemlist(R.drawable.afternoun7));
                images.add(new itemlist(R.drawable.evening4));
                images.add(new itemlist(R.drawable.night3));
                images.add(new itemlist(R.drawable.night4));
                break;

            case "card3":
                images.add(new itemlist(R.drawable.morning11));
                images.add(new itemlist(R.drawable.morning12));

                images.add(new itemlist(R.drawable.afternoun7));
                images.add(new itemlist(R.drawable.afternoun8));
                images.add(new itemlist(R.drawable.afternoun9));
                images.add(new itemlist(R.drawable.afternoun10));

                break;
        }

        return images;
    }
}
