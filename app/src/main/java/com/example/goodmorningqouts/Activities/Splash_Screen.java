package com.example.goodmorningqouts.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bt.good.morning.qoutes.wishes.images.app.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;

public class Splash_Screen extends AppCompatActivity {

    private static final String LOG_TAG = "Splash_Screen_1";
    private AppOpenAd appOpenAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             goToNext();
         }
     },4000);
       // initGDPRDialog(this);
    }

    private void goToNext() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void initGDPRDialog(Activity activity) {
        ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(activity)
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId("023859387A5FB4639FD5DA43F3AAE80C")
                .build();

        ConsentRequestParameters params = new ConsentRequestParameters.Builder()
                .setTagForUnderAgeOfConsent(false)
                .setConsentDebugSettings(debugSettings)
                .build();

        ConsentInformation consentInformation = UserMessagingPlatform.getConsentInformation(activity);
        consentInformation.requestConsentInfoUpdate(activity, params, () -> {
            if (consentInformation.canRequestAds()) {
                loadAppOpenAd(activity); // Load app open ad if consent is granted
            } else {
                goToNext(); // No ads can be requested, move to next activity
            }
        }, requestConsentError -> {
            Log.e(LOG_TAG, String.format("Error: %s: %s", requestConsentError.getErrorCode(), requestConsentError.getMessage()));
            goToNext(); // Error occurred, move to next activity
        });
    }

    private void loadAppOpenAd(Activity activity) {
        AdRequest adRequest = new AdRequest.Builder().build();

        AppOpenAd.load(activity, getString(R.string.appopen_adtesthigh), adRequest,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd ad) {
                        Log.d(LOG_TAG, "App Open Ad Loaded");
                        appOpenAd = ad; // Assign loaded ad to the instance variable
                        appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                goToNext(); // Move to next activity when ad is dismissed
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                Log.e(LOG_TAG, "Ad failed to show: " + adError.getMessage());
                                goToNext(); // Move to next activity if ad fails to show
                            }
                        });
                        appOpenAd.show(activity); // Show the app open ad
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.i(LOG_TAG, "App Open Ad Failed to Load: " + loadAdError.getMessage());
                        Toast.makeText(activity, "No Ads Available", Toast.LENGTH_SHORT).show();
                        goToNext(); // Move to next activity if ad fails to load
                    }
                });
    }
}
