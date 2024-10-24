package com.example.goodmorningqouts.AdsManager;


import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.bt.good.morning.qoutes.wishes.images.app.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AppsController extends Application {

    public static boolean showAdOverIntersAd = true;

    @Override
    public void onCreate() {
        super.onCreate();


        MobileAds.initialize(this, initializationStatus -> {
            // new AppOpenManager(this);
            new AppOpenASManager(this);
           // new AppOPenASManagerSplash(this);
            // Initialization code here
        });


    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Banner Ad >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static void addBannerFun(Activity activity) {
        FrameLayout frameLayout = activity.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().setHttpTimeoutMillis(5000).build();
        loadBannerAd(activity, adRequest, frameLayout);
    }

    private static void loadBannerAd(Activity activity, AdRequest adRequest, FrameLayout frameLayout) {
        AdView mAdView = new AdView(activity);
        mAdView.setAdUnitId(activity.getString(R.string.bannerad_livetesthgih)); // Replace with your real Ad ID
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("Banner", "Ad failed to load: " + loadAdError.getMessage());
                frameLayout.removeAllViews();
                mAdView.destroy();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                frameLayout.addView(mAdView);
            }
        });
        mAdView.loadAd(adRequest);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Interstitial Ad >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    public static void loadInterstitialAd(Activity activity, FullScreenContentCallback fullScreenContentCallback) {
        AdRequest adRequest = new AdRequest.Builder().setHttpTimeoutMillis(5000).build();
        InterstitialAd.load(activity, activity.getString(R.string.interstitial_adtesthigh), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        interstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
                        interstitialAd.show(activity);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.e("InterstitialAd", "Failed to load ad: " + loadAdError.getMessage());
                        Toast.makeText(activity, "No Ads Available", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public static int counterCliks = 0;

    public static void showFiveClickAds(Activity activity) {
        counterCliks++;
        if (counterCliks % 5 == 0) {
            loadAdHighFiveClicks(activity, new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    showAdOverIntersAd = true;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    showAdOverIntersAd = true;
                }
            });
            counterCliks = 0;
        }
    }

    public static void loadAdHighFiveClicks(Activity activity, FullScreenContentCallback fullScreenContentCallback) {
        AdRequest adRequest = new AdRequest.Builder().setHttpTimeoutMillis(5000).build();
        final ProgressDialog progressDialog = ProgressDialog.show(activity, "", "Loading Ad..", true);
        progressDialog.setCancelable(false);

        InterstitialAd.load(activity, activity.getString(R.string.interstitial_adtesthigh), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        progressDialog.dismiss();  // Ensure to dismiss on success
                        interstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
                        interstitialAd.show(activity);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        progressDialog.dismiss();  // Ensure to dismiss on failure
                        Log.e("InterstitialAd", "Failed to load ad: " + loadAdError.getMessage());
                        Toast.makeText(activity, "Ad failed to load. Try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}



