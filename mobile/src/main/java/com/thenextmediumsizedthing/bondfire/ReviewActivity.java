package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import static com.thenextmediumsizedthing.bondfire.R.drawable.starfull;


public class ReviewActivity extends Activity {
    
    private Button back;
    private TextView groupName;
    private Button submit;
    private ImageView groupIcon;
/*    private ToggleButton star1;
    private ToggleButton star2;
    private ToggleButton star3;
    private ToggleButton star4;
    private ToggleButton star5;
    private boolean star1Bool;*/
    private EditText groupComments;
    private GroupClass group;
    private ListAdapter adapter;
    private ListView listView;
    private ImageView stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Bundle b = this.getIntent().getExtras();
        if (b!=null) {
            group = b.getParcelable("group");
        }
        back = (Button)findViewById(R.id.backarrow);
        setBackButton();
        groupName = (TextView)findViewById(R.id.groupName);
        groupName.setText(group.getName());
        submit = (Button)findViewById(R.id.submitBtn);
        setSubmitButton();
        groupIcon = (ImageView)findViewById(R.id.groupIcon);
        groupIcon.setImageResource(group.getImageId());
        stars = (ImageView)findViewById((R.id.stars));
/*        star1Bool = false;
        star1 = (ToggleButton)findViewById(R.id.star1);
        star2 = (ToggleButton)findViewById(R.id.star2);
        star3 = (ToggleButton)findViewById(R.id.star3);
        star4 = (ToggleButton)findViewById(R.id.star4);
        star5 = (ToggleButton)findViewById(R.id.star5);
        star1.setVisibility(View.GONE);
        star2.setVisibility(View.GONE);
        star3.setVisibility(View.GONE);
        star4.setVisibility(View.GONE);
        star5.setVisibility(View.GONE);
        setStar1();
        setStar2();
        setStar3();
        setStar4();
        setStar5();*/
        groupComments = (EditText)findViewById(R.id.groupComments);
        setPersonIcons();
        fillListView();
    }

    private void setPersonIcons() {
        for (Person p : group.getMembers()){
            p.setImageId(getResID(p.getImage()));
        }
    }

    public int getResID(String s){
        return getResources().getIdentifier(s, "drawable", "com.thenextmediumsizedthing.bondfire");
    }

    private void fillListView() {
        adapter = new ReviewListAdapter(this,R.layout.listobject_chat,group.getMembers());
        listView = (ListView)findViewById(R.id.memberList);
        listView.setItemsCanFocus(true);
        listView.setAdapter(adapter);
    }

/*    private void setStar5() {
        star5.setChecked(false);
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setChecked(true);
                star2.setChecked(true);
                star3.setChecked(true);
                star4.setChecked(true);
                star5.setChecked(true);
                star1Bool = false;
            }
        });
    }

    private void setStar4() {
        star4.setChecked(false);
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setChecked(true);
                star2.setChecked(true);
                star3.setChecked(true);
                star4.setChecked(true);
                star5.setChecked(false);
                star1Bool = false;
            }
        });
    }

    private void setStar3() {
        star3.setChecked(false);
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setChecked(true);
                star2.setChecked(true);
                star3.setChecked(true);
                star4.setChecked(false);
                star5.setChecked(false);
                star1Bool = false;
            }
        });
    }

    private void setStar2() {
        star2.setChecked(false);
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setChecked(true);
                star2.setChecked(true);
                star3.setChecked(false);
                star4.setChecked(false);
                star5.setChecked(false);
                star1Bool = false;
            }
        });
    }

    private void setStar1() {
        star1.setChecked(star1Bool);
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star1.setChecked(!star1Bool);
                star2.setChecked(false);
                star3.setChecked(false);
                star4.setChecked(false);
                star5.setChecked(false);
                star1Bool = !star1Bool;
            }
        });
    }*/

    private void setSubmitButton() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setBackButton() {
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
        getMenuInflater().inflate(R.menu.menu_review, menu);
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
