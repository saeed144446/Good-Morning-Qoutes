package com.example.goodmorningqouts.AdsManager;



import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.bt.good.morning.qoutes.wishes.images.app.R;
import com.example.goodmorningqouts.Activities.MainActivity;
import com.example.goodmorningqouts.Activities.Splash_Screen;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

import javax.annotation.Nullable;

public class AppOPenASManagerSplash implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "AppOpenASManagerSplash";

    private final AppsController myASApplication;
    private AppOpenAd appASOpenAd = null;
    private Activity currentASActivity;
    private static boolean isShowingASAd = false;
    private long loadTime = 0;
    private static boolean isAdLoaded = false;
    public static boolean showAdOverIntersAd = true;
    // public static boolean showOpenAdSimple = true;

    public AppOPenASManagerSplash(AppsController myASApplication) {
        this.myASApplication = myASApplication;
        this.myASApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (!(currentASActivity instanceof Splash_Screen)) {


        }
    }

    public void showAdIfAvailable() {
        if (!isShowingASAd && isASAdAvailable() && appASOpenAd != null) {
            Log.d(LOG_TAG, "Ad is available, showing ad.");
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d(LOG_TAG, "Ad dismissed.");
                    appASOpenAd = null;
                    isShowingASAd = false;
                    fetchASAd();
                    gotoNext();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    Log.d(LOG_TAG, "Ad failed to show: " + adError.getMessage());
                    isShowingASAd = false;
                    appASOpenAd = null;
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.d(LOG_TAG, "Ad showed.");
                    isShowingASAd = true;
                    appASOpenAd = null;
                }
            };
            if (appASOpenAd != null) {
                appASOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                appASOpenAd.show(currentASActivity);
            }
        } else {
            Log.d(LOG_TAG, "Ad is not available. Fetching ad.");
            fetchASAd();
        }
    }

    public void fetchASAd() {
        if (isASAdAvailable()) return;
        AdRequest request = new AdRequest.Builder().build();
        fetchAdHighASFloor(request);
    }

    private void fetchAdHighASFloor(AdRequest request) {
        Log.d(LOG_TAG, "Fetching high floor ad.");
        AppOpenAd.load(myASApplication, myASApplication.getString(R.string.appopen_adtesthigh), request,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.e(LOG_TAG, "Failed to load high floor ad: " + loadAdError.getMessage());
                        isAdLoaded = false;
                        appASOpenAd = null;

                    }

                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        Log.d(LOG_TAG, "High floor ad loaded.");
                        isAdLoaded = true;
                        appASOpenAd = appOpenAd;
                        loadTime = (new Date()).getTime();
                    }
                });
    }


    private void gotoNext(){
        Intent intent = new Intent(currentASActivity, MainActivity.class);
        currentASActivity.startActivity(intent);
        currentASActivity.finish();
    }
    private boolean wasLoadTimeLessThanASNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }

    public boolean isASAdAvailable() {
        return appASOpenAd != null && wasLoadTimeLessThanASNHoursAgo(4);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {}

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentASActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentASActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {}

    @Override
    public void onActivityStopped(@NonNull Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {}

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentASActivity = null;
    }
}