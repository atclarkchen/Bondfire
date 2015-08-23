package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    private Button chat;
    private Intent chatIntent;
    private ListView mListView;
    private ImageView startImg;
    private ImageView profile;
    private Intent profileIntent;
    private Intent startIntent;
    private TextView coverLabel;
    private EditText search;
    public static  Location locationA;
    private LocationListener locationListener;
    private LocationManager locationManager;
    public static Integer userid;
    private TextView bondfire;
    private ProgressDialog pDialog;
    private ArrayList<Integer> backgrounds;
    private String[] from = {"img", "title", "labelActiveGroups"};
    private int[] to = {R.id.img, R.id.title, R.id.labelActiveGroups};


    private JSONParser jsonParser = new JSONParser();
    ArrayList<Integer> interestCounts = new ArrayList<Integer>();
    private ArrayList<InterestCard> interestCards = new ArrayList<InterestCard>();
    private ArrayList <HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
    private String urlGroupCount = "http://cgarcia.site.nfoservers.com/phpmyadmin/get_count_all.php";
    private String[] interestStrings = { "Sightseeing", "Chilling", "Nature", "Concerts", "Sports", "Food", "Bar Hopping", "Shopping"};
    public static ArrayList<String> interests = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        interestCounts.add(0);
        interestCounts.add(0);
        interestCounts.add(0);
        interestCounts.add(0);
        interestCounts.add(0);
        interestCounts.add(0);
        interestCounts.add(0);
        interestCounts.add(0);
        backgrounds = new ArrayList<Integer>();
        backgrounds.add(R.drawable.sightseeing);
        backgrounds.add(R.drawable.chill);
        backgrounds.add(R.drawable.nature);
        backgrounds.add(R.drawable.concert);
        backgrounds.add(R.drawable.sports2);
        backgrounds.add(R.drawable.food);
        backgrounds.add(R.drawable.barhopping);
        backgrounds.add(R.drawable.shopping);
        userid = 12;

        chatIntent = new Intent(MainActivity.this,ChatActivity.class);
        setChatButton();
        profileIntent = new Intent(MainActivity.this,User_profile.class);
        setProfileButton();

        search = (EditText)findViewById(R.id.search);
        search.setEnabled(false);

        // Loading counts in Background Thread
        new LoadGroupCounts().execute();

        startImg = (ImageView)findViewById(R.id.start_adventure);
        startImg.setAlpha(0.0f);//default have this invisible
        startImg.setEnabled(false);

        //Retrieve the listView
        mListView = (ListView)findViewById(R.id.interestList);

//        coverLabel = (TextView) findViewById(R.id.selectedLabel);
//        coverLabel.setVisibility(View.INVISIBLE);

        bondfire = (TextView)findViewById(R.id.homeText);
        bondfire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interests.clear();
                startImg.setAlpha(0.0f);
                startImg.setEnabled(false);
                new LoadGroupCounts().execute();
            }
        });

        //set an event listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get back our Hashmap
//                HashMap<String, String> map = (HashMap<String, String>)mListView.getItemAtPosition(position);
                //int imgID = Integer.parseInt(map.get("img"));
                System.out.println(position);
                InterestCard i = interestCards.get(position);

                if (i.isSelected() && interests.size() <= 8) { //toggle selected and returns boolean on current (post-click) state
                    view.findViewById(R.id.img).setAlpha(0.6f);
                    //change cover to reflect selection
                    if (interests.contains(interestStrings[position]) == false) {
                        interests.add(interestStrings[position]);
                    }

                    if (interests.size() >
                            0) {//then this is first instance of selecting something. Get rid of DEFAULT "selected all" textview label
//                        coverLabel.setAlpha(0.0f);
                        startImg.setAlpha(1.0f); //show startAdventure image
                        startImg.setEnabled(true);
                    }

                } else {//it was de-selected
                    view.findViewById(R.id.img).setAlpha(1);//return to full opacity
                    for (int j = 0; j < interests.size(); j++) {
                        String tempName = interests.get(j);
                        if (tempName.equals(interestStrings[position])) {
                            interests.remove(i);
                        }
                    }
                    interests.remove(interestStrings[position]);

                    if (interests.size() == 0) {//then there are currently NO items selected. Show DEFAULT "selected all" textview label
//                        coverLabel.setAlpha(1.0f);
                        startImg.setAlpha(0.0f);//hide startAdventure image
                        startImg.setEnabled(false);
                    }
                }
                System.out.println(interests);

            }
        });
        setStartButton();
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new MyLocationListener();
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
//        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationA = new Location("point A");
        locationA.setLatitude(37.869711);
        locationA.setLongitude(-122.268133);
    }

    public void reloadGroups() {
        new LoadGroupCounts().execute();
    }

//    private class MyLocationListener implements LocationListener {
//
//        @Override
//        public void onLocationChanged(Location location) {
//            locationA = new Location("point A");
//            locationA.setLatitude(location.getLatitude());
//            locationA.setLongitude(location.getLongitude());
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

    private void setProfileButton() {
        profile = (ImageView)findViewById(R.id.addButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(profileIntent);
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

    private void setStartButton() {
        startImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Selected On Home", String.valueOf(interests));
                startIntent = new Intent();
                startIntent.setClass(getApplicationContext(), BrowseGroupsActivity.class);
                startIntent.putExtra("interests", interests);
                startActivity(startIntent);
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
    class LoadGroupCounts extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading interests. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting all counts from url
         * */
        protected String doInBackground(String... args) {
            interestCards = new ArrayList<InterestCard>();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonParser = new JSONParser();
            JSONObject json = jsonParser.makeHttpRequest(
                    urlGroupCount, "GET", params);
            for (int i = 0; i <= 7; i++) {
                InterestCard x = null;
                try {
                    x = new InterestCard(i, interestStrings[i], json.getInt(interestStrings[i]));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                interestCards.add(x);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("title", x.getTitle());
                map.put("labelActiveGroups", x.getLabelActiveGroups());
                map.put("img", Integer.toString(backgrounds.get(i)));
                listItem.add(map);
                    }
            return null;
        }

        /**
         * After loading counts, insert into cards
         * **/
        protected void onPostExecute(String file_url) {
            SimpleAdapter sa = new SimpleAdapter(getApplicationContext(), listItem, R.layout.interest_element, from, to);
            mListView.setAdapter(sa);
            listItem = new ArrayList<HashMap<String, String>>();
            pDialog.dismiss();
        }
    }
}
