package com.example.goodmorningqouts.AdsManager;



import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.bt.good.morning.qoutes.wishes.images.app.R;
import com.example.goodmorningqouts.Activities.Splash_Screen;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class AppOpenASManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private final AppsController myASApplication;
    private AppOpenAd appASOpenAd = null;
    private Activity currentASActivity;
    public static boolean isShowingASAd = false;
    private static boolean isAdLoaded ;
    private long loadTime = 0;
    public static boolean showAdOverIntersAd=true;
    public static boolean showOpenAdSimple=true;
    public static ASCustomAdMobInterstitialAdListener customAdMobASInterstitialAdASListener;

    public AppOpenASManager(AppsController myASApplication) {
        this.myASApplication = myASApplication;
        this.myASApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }


    /** LifecycleObserver methods */
    //@OnLifecycleEvent(Lifecycle.Event.ON_START)

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onActivityResumed() {

                    Log.d("helloelseeee","gained");

        if (!(currentASActivity instanceof Splash_Screen)) {
            showAdIfAvailable();
        }



                    showOpenAdSimple=true;
                    Log.d("helloelseeee","replicated");

    }

    public void showAdIfAvailable() {
        Log.d("helloelseeee","fetchavailgained");
        if (!isShowingASAd && isASAdAvailable() && appASOpenAd !=null) {
            Log.d("771_AppOpenManager__", "Will show ad.");
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    AppOpenASManager.this.appASOpenAd = null;
                    isShowingASAd = false;
                    fetchASAd();
                }
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    isShowingASAd = false;
                    AppOpenASManager.this.appASOpenAd = null;
                    Log.d("771_AS_AppOpenManager__", "Failed : "+adError.getMessage());
                }
                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingASAd = true;
                    AppOpenASManager.this.appASOpenAd = null;
                }
            };
            if ( appASOpenAd !=null) {
                appASOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                appASOpenAd.show(currentASActivity);
                Log.d("helloelseeee","showavailgained");
            }
        }
        else {
            Log.d("771_AppOpenManager__", "Can not show ad.");
            fetchASAd();
        }
    }

    public void fetchASAd() {
        if (isASAdAvailable()) return;
        AdRequest request = new AdRequest.Builder().setHttpTimeoutMillis(5000).build();
        fetchAdHighASFloor(request);
    }

    public void fetchAdHighASFloor(AdRequest request) {
        AppOpenAd.load(myASApplication, myASApplication.getString(R.string.appopen_adtesthigh), request,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        isAdLoaded = false;
                        Log.e("771_fetchAdHighFloor","Failed: "+loadAdError.getMessage());
                        AppOpenASManager.this.appASOpenAd = null;
                       // fetchAMediumASFloor(request);
                    }
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        isAdLoaded = true;
                        AppOpenASManager.this.appASOpenAd = appOpenAd;
                        AppOpenASManager.this.loadTime = (new Date()).getTime();
                        Log.d("771_fetchAdHighFloor","Succeed High ad loaded");
                    }
                });

    }



    /** Utility method to check if ad was loaded more than n hours ago. */
    private boolean wasLoadTimeLessThanASNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }
    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isASAdAvailable() {
        return appASOpenAd != null && wasLoadTimeLessThanASNHoursAgo(4);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

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
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) { }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) { currentASActivity = null; }
    public interface ASCustomAdMobInterstitialAdListener
    {
        void onInterstitialAdClosed();
        void onFBInterstitialAdClosed();
    }

}
