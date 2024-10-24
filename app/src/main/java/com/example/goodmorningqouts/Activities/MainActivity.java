package com.example.goodmorningqouts.Activities;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.bt.good.morning.qoutes.wishes.images.app.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView card1,card2,card3;

    private ImageView morning_btn,afternoun_btn,evening_btn,night_btn, menu_btn, premium_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.drawerlayout);
        menu_btn = findViewById(R.id.menu_button);
        premium_btn = findViewById(R.id.premium_button);

        morning_btn=findViewById(R.id.goodmorning_image);
        afternoun_btn=findViewById(R.id.goodafternoun_image);
        evening_btn=findViewById(R.id.goodevening_image);
        night_btn=findViewById(R.id.goodnight_image);
        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);
        card3=findViewById(R.id.card3);

        openDrawerLayout();

        morning_btn.setOnClickListener(v -> openImageListActivity("morning"));
        afternoun_btn.setOnClickListener(v -> openImageListActivity("afternoon"));
        evening_btn.setOnClickListener(v -> openImageListActivity("evening"));
        night_btn.setOnClickListener(v -> openImageListActivity("night"));
        card1.setOnClickListener(v -> openImageListActivity("card1"));
        card2.setOnClickListener(v -> openImageListActivity("card2"));
        card3.setOnClickListener(v -> openImageListActivity("card3"));



    }
    private void openImageListActivity(String category) {
        Intent intent = new Intent(MainActivity.this, ImageListActivity.class);
        intent.putExtra("category", category); // Pass the category
        startActivity(intent);
    }
    private void openDrawerLayout() {

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }

        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.privacy_policy) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://appvortex0101.blogspot.com/2024/10/policy-apps-vortex-studio-has-made-this.html")));

                } else if (id == R.id.share) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT,
                            "Check This App: https://play.google.com/store/apps/details?id=" + getPackageName());
                    shareIntent.setType("text/plain");
                    startActivity(shareIntent);

                } else if (id == R.id.rateus) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(("market://details?id=" + getPackageName()))));
                    } catch (ActivityNotFoundException e1) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(("http://play.google.com/store/apps/details?id=" + getPackageName()))));
                        } catch (ActivityNotFoundException e2) {
                            Toast.makeText(MainActivity.this, "You don't have any app that can open this link", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Create an AlertDialog builder
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Exit the app
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog and do nothing
                        dialog.dismiss();
                    }
                })
                .setCancelable(true) // Prevent closing the dialog by tapping outside
                .show();
    }

}