package com.thenextmediumsizedthing.bondfire;
        import android.app.IntentService;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;

        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.wearable.CapabilityApi;
        import com.google.android.gms.wearable.Wearable;

/**
 * Created by Christian on 8/2/2015.
 */
public class NotifyWear extends IntentService {

    private GoogleApiClient mGoogleApiClient;

    public NotifyWear() {
        super("NotifyWear");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("0", "notifywear reached");
        Bundle b = intent.getExtras();
        int groupid = b.getInt("groupid");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();
        mGoogleApiClient.connect();
        CapabilityApi.GetCapabilityResult capResult =
                Wearable.CapabilityApi.getCapability(
                        mGoogleApiClient, "canNotifyWear",
                        CapabilityApi.FILTER_REACHABLE)
                        .await();
        Wearable.MessageApi.sendMessage(
                mGoogleApiClient, capResult.toString(),
                "wear", String.valueOf(groupid).getBytes()
        );
    }
}
