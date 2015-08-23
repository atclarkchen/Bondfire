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
//        OtherGroup.notificationManager.cancel(100);
        Bundle b = intent.getExtras();
        System.out.println(b.getInt("groupid"));
        Intent viewGroupIntent = new Intent();
        viewGroupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        viewGroupIntent.setClass(getApplicationContext(), OtherGroup.class);
        viewGroupIntent.putExtra("groupid", b.getInt("groupid"));
        Log.d("sending id", String.valueOf(b.getInt("groupid")));
        startActivity(viewGroupIntent);
    }

}
