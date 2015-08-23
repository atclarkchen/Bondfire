package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class BrowseGroupsActivity extends Activity {

    private Button chat;
    private Intent chatIntent;
    private ListView mListView;
    private ImageView profile;
    private Intent profileIntent;
    private ProgressDialog pDialog;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location lastLocation;
    private Intent viewGroupIntent;

    private JSONParser jsonParser = new JSONParser();
    public final ArrayList<GroupCard> GroupCards = new ArrayList<GroupCard>();
    public final ArrayList<HashMap<String, String>> listItem = new ArrayList<>();
    private String urlGroups = "http://cgarcia.site.nfoservers.com/phpmyadmin/get_all_groupsFast.php";
    private final ArrayList<String> names = new ArrayList<String>();
    private final ArrayList<Integer> groupids = new ArrayList<Integer>();
    private final ArrayList<String> photos = new ArrayList<String>();
    private final ArrayList<Double> distances = new ArrayList<Double>();
    String[] from = {"img", "title", "labelDistance"};

    //IDs used by ListView layout
    int[] to = {R.id.img, R.id.title, R.id.labelDistance};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new MyLocationListener();
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
//        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        locationA = new Location("point A");
//        locationA.setLatitude(lastLocation.getLatitude());
//        locationA.setLongitude(lastLocation.getLongitude());
//        System.out.println(lastLocation);
        Log.d("Selected Interests", String.valueOf(MainActivity.interests));
        chatIntent = new Intent(BrowseGroupsActivity.this,ChatActivity.class);
        setChatButton();
        profileIntent = new Intent(BrowseGroupsActivity.this,User_profile.class);
        setCreateButton();


        //Retrieve the listView
        mListView = (ListView)findViewById(R.id.interestList);

        // Loading counts in Background Thread
        new LoadGroups().execute();

        //set an event listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                Log.d("Selected On Home", String.valueOf(MainActivity.interests));
                viewGroupIntent = new Intent();
                viewGroupIntent.setClass(getApplicationContext(), OtherGroup.class);
                viewGroupIntent.putExtra("groupid", groupids.get(position));
                startActivity(viewGroupIntent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new MyLocationListener();
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
//        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        MainActivity.locationA.setLatitude(37.874828);
//        MainActivity.locationA.setLongitude(-122.258539);
 }

    private void setCreateButton() {
        ImageView createGroupBtn = (ImageView)findViewById(R.id.addButton);
        createGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createGroup = new Intent(BrowseGroupsActivity.this, Create_group.class);
                startActivity(createGroup);
            }
        });
    }

    private void setChatButton() {
        chat = (Button)findViewById(R.id.chatBtn);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(chatIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * Background Async Task to Load all interest counts by making HTTP Request
     * */
    class LoadGroups extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BrowseGroupsActivity.this);
            pDialog.setMessage("Loading groups. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting all counts from url
         */
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (int i = 0; i < MainActivity.interests.size(); i++) {
                params.add(new BasicNameValuePair("interests[]", MainActivity.interests.get(i)));
            }
            params.add(new BasicNameValuePair("latitude", String.valueOf(MainActivity.locationA.getLatitude())));
            params.add(new BasicNameValuePair("longitude", String.valueOf(MainActivity.locationA.getLongitude())));
            JSONObject json = jsonParser.makeHttpRequest(
                    urlGroups, "GET", params);
            Log.d("json: ", String.valueOf(json));
            JSONArray groups = null;
            try {
                groups = json.getJSONArray("groups");
                for (int j = 0; j < groups.length(); j++) {
                    JSONObject c = groups.getJSONObject(j);
                    names.add(c.getString("name"));
                    groupids.add(c.getInt("groupid"));
                    photos.add(c.getString("photo"));
                    distances.add(c.getDouble("distance"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<String> BGS = new ArrayList<String>();
            for (int i = 0; i < groupids.size(); i++) {
                BGS.add(photos.get(i));
                GroupCards.add(new GroupCard(groupids.get(i), names.get(i), distances.get(i)));
            }
            HashMap<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < groupids.size(); i++) {
                File file = null;
                try {
                    System.out.println("loading photo");
                    URL url = new URL(photos.get(i));
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inSampleSize = 4;
                    Bitmap myBitmap = BitmapFactory.decodeStream(input, null, null);
                    file = new File(Environment.getExternalStorageDirectory(), URLUtil.guessFileName(photos.get(i), null, null));
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                        myBitmap.recycle();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(file.getAbsolutePath());
                    out.close();
                } catch (IOException e) {
                }
                map = new HashMap<>();//make a new hashmap each time..seems like everyone does this
                GroupCard ic = GroupCards.get(i);//current interest card
                map.put("title", ic.getName());
                map.put("labelDistance", ic.getLabelDistance());
                map.put("img", file.getAbsolutePath());
                listItem.add(map);

            }
            return null;
        }

        /**
         * After loading counts, insert into cards
         **/
        protected void onPostExecute(String file_url) {
            //ListAdapter listAdapter = new ListAdapter(this, listItem);
            SimpleAdapter sa = new SimpleAdapter(getApplicationContext(), listItem, R.layout.group_element, from, to);
            mListView.setAdapter(sa);
            pDialog.dismiss();

        }
    }

//    private class MyLocationListener implements LocationListener {
//
//        @Override
//        public void onLocationChanged(Location location) {
////            locationA = new Location("point A");
//            MainActivity.locationA.setLatitude(location.getLatitude());
//            MainActivity.locationA.setLongitude(location.getLongitude());
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//
//        }
//    }

}
