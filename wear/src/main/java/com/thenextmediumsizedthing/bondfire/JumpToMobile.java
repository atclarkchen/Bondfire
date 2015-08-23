package com.thenextmediumsizedthing.bondfire;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Christian on 8/2/2015.
 */
public class JumpToMobile extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private GoogleApiClient mGoogleApiClient;

    public JumpToMobile() {
        super("JumpToMobile");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("0", "jumptomobile reached");
        MainActivityWear.notificationManager.cancel(1000);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
        CapabilityApi.GetCapabilityResult capResult =
                Wearable.CapabilityApi.getCapability(
                        mGoogleApiClient, "canLaunchMobile",
                        CapabilityApi.FILTER_REACHABLE)
                        .await();
        Wearable.MessageApi.sendMessage(
                mGoogleApiClient, capResult.toString(),
                "mobile", "requestToLaunchMobileInitated".getBytes()
        );
    }

}
