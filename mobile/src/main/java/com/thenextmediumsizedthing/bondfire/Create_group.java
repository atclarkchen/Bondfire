package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class Create_group extends Activity {
    static int GET_FROM_GALLERY = 100;
    //    private ImageView group_image;
    private EditText group_name;
    private ArrayList<Object> group_members = new ArrayList<Object>();
    private EditText group_plan;
    private EditText group_blurb;
    private Button create_button;
    private ImageView image_button;
    private ImageView add_button;
    private ImageView photo;
    private Button back;
    private Uri photoPath;
    private Spinner interestSelector;
    JSONParser jsonParser = new JSONParser();
//    JSONParser jsonParser2 = new JSONParser();
    private Integer newGroupID;

    //this needs to go somewhere higher than this file
    public static List<group_obj> all_groups = new ArrayList<group_obj>();
    private String url_create_group = "http://cgarcia.site.nfoservers.com/phpmyadmin/create_group.php";
//    private String url_create_usersGroups = "http://cgarcia.site.nfoservers.com/phpmyadmin/create_usersGroups.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group2);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        photoPath = Uri.parse("android.resource://com.thenextmediumsizedthing.bondfire/" + R.drawable.sample_cover);
        setBackButton();
        interestSelector = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.interestsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestSelector.setAdapter(adapter);
//        group_image = (ImageView) findViewById(R.id.group_image);
        group_name = (EditText) findViewById(R.id.group_name);
        // need to add the user, assign picture to the first image view
        group_members.add("Clark");
        group_plan = (EditText) findViewById(R.id.plan_text);
        group_blurb = (EditText) findViewById(R.id.blurb_text);
        photo = (ImageView) findViewById(R.id.group_image);

        create_button = (Button) findViewById(R.id.button);
        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group_obj new_group = new group_obj();
                new_group.set_group(group_name.getText().toString(), group_members, group_plan.getText().toString(), group_blurb.getText().toString(), new String[0]);
                all_groups.add(new_group);
                new RetrieveFeedTask().execute();
//                Intent created = new Intent(getApplicationContext(), own_existing_group.class);
//                startActivity(created);

            }

        });

        image_button = (ImageView) findViewById(R.id.upload_button);
        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
//                final Intent launchCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(launchCamera, 10);
            }

        });

        add_button = (ImageView) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }

        });

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(photoPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", group_name.getText().toString()));
            params.add(new BasicNameValuePair("interest", interestSelector.getSelectedItem().toString()));
            params.add(new BasicNameValuePair("plan", group_plan.getText().toString()));
            params.add(new BasicNameValuePair("blurb", group_blurb.getText().toString()));
            params.add(new BasicNameValuePair("photo", encodedImage));
            params.add(new BasicNameValuePair("longitude", String.valueOf(MainActivity.locationA.getLongitude())));
            params.add(new BasicNameValuePair("latitude", String.valueOf(MainActivity.locationA.getLatitude())));

//            Log.d("1", String.valueOf(params));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_group,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt("success");
                newGroupID = json.getInt("newGroupID");

                if (success == 1) {
//                    // successfully created product
                    System.out.println(newGroupID);
                    File file = new File(Environment.getExternalStorageDirectory(), String.valueOf(newGroupID)+".jpg");
                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                        bitmap.recycle();
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
                    Intent i = new Intent(getApplicationContext(), OtherGroup.class);
                    i.putExtra("groupid", newGroupID);
                    startActivity(i);
                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String feed) {
//            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
//            params2.add(new BasicNameValuePair("userid", String.valueOf(MainActivity.userid)));
//            params2.add(new BasicNameValuePair("groupid", String.valueOf(newGroupID)));
//            Log.d("params2", String.valueOf(params2));
//            // getting JSON Object
//            // Note that create product url accepts POST method
//            JSONObject json2 = jsonParser2.makeHttpRequest(url_create_usersGroups,
//                    "POST", params2);
//            Log.d("Create id", json2.toString());
//            Intent i = new Intent(getApplicationContext(), OtherGroup.class);
//            i.putExtra("groupid", newGroupID);
//            startActivity(i);
//            // closing this screen
//            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("actResult");
//        loginButton.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            System.out.println("Photo good");
            photoPath = data.getData();
            photo.setImageURI(photoPath);
            System.out.println(photoPath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setBackButton() {
        back = (Button)findViewById(R.id.back_button);
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
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
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

}
