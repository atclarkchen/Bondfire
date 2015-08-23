package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OtherGroup extends Activity {

    private Integer groupid;
    private ImageView photo;
    private String photoPath;
    private TextView name;
    private TextView plan;
    private TextView blurb;
    private TextView interest;
    private Button back;
    private TextView distance;
    private RatingBar ratingBar;

    JSONParser jsonParser = new JSONParser();
    JSONParser jsonParser2 = new JSONParser();
    public static NotificationManager notificationManager;
    private static String urlGetGroup = "http://cgarcia.site.nfoservers.com/phpmyadmin/get_group.php";
    private static String urlGetGroupAverage = "http://cgarcia.site.nfoservers.com/phpmyadmin/get_average_group_rating.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_group);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setBackButton();

        Bundle b = getIntent().getExtras();
        groupid = b.getInt("groupid");

        name = (TextView) findViewById(R.id.group);
        plan = (TextView) findViewById(R.id.plan_text);
        plan.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        plan.setMovementMethod(new ScrollingMovementMethod());
        blurb = (TextView) findViewById(R.id.blurb_text);
        blurb.setMovementMethod(new ScrollingMovementMethod());
        interest = (TextView) findViewById(R.id.tags);
        distance = (TextView) findViewById(R.id.distance);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        photo = (ImageView)findViewById(R.id.group_image);
        ratingBar.setStepSize((float) 0.5);

        new LoadGroup().execute();
        new LoadRatings().execute();

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.rgb(255, 99, 71), PorterDuff.Mode.SRC_ATOP);

        Button notify = (Button) findViewById(R.id.notifyButton);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OtherGroup.this);
                builder.setMessage("Contact "+name.getText())
                        .setCancelable(true)
                        .setNegativeButton("Notify Interest", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new NotifyWear().execute();
                            }
                        })
                        .setPositiveButton("Chat", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                System.out.println("chat requested");
                                Intent chatIntent = new Intent();
                                chatIntent.setClass(getApplicationContext(), ChatActivity.class);
                                chatIntent.putExtra("name", name.getText());
                                startActivity(chatIntent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    private void setBackButton() {
        back = (Button)findViewById(R.id.backarrow);
        back.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_own_existing_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickEdit(View v){
        Intent edit_button = new Intent(this, edit_group.class);
        startActivity(edit_button);
    }

    class LoadGroup extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting all counts from url
         */
        protected String doInBackground(String... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
//                            Log.d("interest: ", interestStrings[finalI]);
                        System.out.println(groupid);
                        params.add(new BasicNameValuePair("groupid", groupid.toString()));

                        JSONObject json = jsonParser.makeHttpRequest(
                                urlGetGroup, "GET", params);
//                            Log.d("json: ", String.valueOf(json));
                        Log.d("json", String.valueOf(json));
                        JSONArray groupObj = json
                                .getJSONArray("group");
                        JSONObject jsonInfo = groupObj.getJSONObject(0);
                        name.setText(jsonInfo.getString("name"));
                        plan.setText(jsonInfo.getString("plan"));
                        blurb.setText(jsonInfo.getString("blurb"));
                        interest.setText(jsonInfo.getString("interest"));
                        Location locationB = new Location("point B");
                        locationB.setLatitude(jsonInfo.getDouble("latitude"));
                        locationB.setLongitude(jsonInfo.getDouble("longitude"));
                        double dist = MainActivity.locationA.distanceTo(locationB);
                        dist = dist * 0.000621371;
                        distance.setText(String.format("%.1f", dist) + " mi.");
                        photoPath = jsonInfo.getString("photo");
                        try {
                            photo.setImageBitmap((BitmapFactory.decodeStream(new URL(jsonInfo.getString("photo")).openStream())));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }

    }

    class NotifyWear extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting all counts from url
         */
        protected String doInBackground(String... args) {
            Bitmap photoN = null;
            try {
                photoN = (BitmapFactory.decodeStream(new URL(photoPath).openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(getApplicationContext(), JumpToMobile.class);
            intent.putExtra("groupid", groupid);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent viewPendingIntent =
                    PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Action openGroup = new NotificationCompat.Action.Builder(R.drawable.groupiconwhite, "Group Details", viewPendingIntent).build();
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.icon)
                            .setLargeIcon(photoN)
                            .setContentTitle("Group Nearby")
                            .setContentText(interest.getText() + "\n" + distance.getText());
            notificationBuilder.addAction(openGroup);
            NotificationCompat.WearableExtender extender = new NotificationCompat.WearableExtender();
            notificationBuilder.extend(extender);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(100, notificationBuilder.build());
            Log.i("0", "notification sent");
            return null;
        }
    }

    class LoadRatings extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * getting all counts from url
         */
        protected String doInBackground(String... args) {
            try {
                List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                params2.add(new BasicNameValuePair("groupid", groupid.toString()));

                JSONObject json2 = jsonParser2.makeHttpRequest(
                        urlGetGroupAverage, "GET", params2);
                Log.d("json2", String.valueOf(json2));
                if (json2.getString("average") == "null") {
                    ratingBar.setRating(Float.valueOf(0));
                } else {
                    ratingBar.setRating(Float.valueOf(json2.getString("average")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}
