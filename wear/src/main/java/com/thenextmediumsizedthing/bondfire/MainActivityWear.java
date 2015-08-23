package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivityWear extends Activity {

    private TextView mTextView;
    public static NotificationManager notificationManager;
    public Button browseGroupsButton;
    private int groupid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_activity_main);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("0", "wear app hidden");
        startActivity(homeIntent);
        groupid=14;
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            groupid = b.getInt("groupid");
        }
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        Intent intent = new Intent(getApplicationContext(), JumpToMobile.class);
        intent.putExtra("groupid", groupid);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent viewPendingIntent =
                PendingIntent.getService(getApplicationContext(), 0, intent, 0);
        NotificationCompat.Action jumpToMobile = new NotificationCompat.Action.Builder(R.drawable.groupiconwhite, "Group Details", viewPendingIntent).build();
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.icon)
                        .setLargeIcon(bm)
                        .setContentTitle("Group Nearby")
//                                .setContentIntent(viewPendingIntent)
                        .setContentText("Concerts" + "\n" + String.format("%.1f", 0.1) + " mi.");
        notificationBuilder.addAction(jumpToMobile);
        NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
        notificationBuilder.extend(extender);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(1000, notificationBuilder.build());
        Log.i("0", "notification sent");
    }


}