package com.thenextmediumsizedthing.bondfire;

        import android.content.Intent;
        import android.support.v4.content.LocalBroadcastManager;
        import android.util.Log;

        import com.google.android.gms.wearable.MessageEvent;
        import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Christian on 8/2/2015.
 */
public class OpenMobile extends WearableListenerService {
    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals("mobile")) {
            Log.i("0", "OpenMobile reached");
            Intent startIntent = new Intent(this, OtherGroup.class);
            startIntent.putExtra("groupid", 69);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startIntent);
        }
        else {
            super.onMessageReceived(messageEvent);
        }
    }
}
