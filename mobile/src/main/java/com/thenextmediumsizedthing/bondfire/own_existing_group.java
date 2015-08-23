package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class own_existing_group extends Activity {

    private group_obj current;
    private ImageView group_image;
    private TextView group_name;
    private TextView group_plan;
    private TextView group_blurb;
    private TextView group_tags;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_existing_group);
        setBackButton();
//
////        Bundle b = getIntent().getExtras();
////        int group_index = b.getInt("Own");
        current = Create_group.all_groups.get(0);
        group_name = (TextView) findViewById(R.id.group);
        group_name.setText(current.get_name());
        group_plan = (TextView) findViewById(R.id.plan_text);
        group_plan.setText(current.get_plan());
        group_blurb = (TextView) findViewById(R.id.blurb_text);
        group_blurb.setText(current.get_blurb());
//        group_tags = (TextView) findViewById(R.id.tags);
//        String s = current.get_tags().toString();
//        s = s.substring(1, s.length()-1).replaceAll(",", "");
//        group_tags.setText(s);

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
//        Bundle b = new Bundle();
//        b.putInt("Own", 0);
//        edit_button.putExtras(b);
        startActivity(edit_button);
    }
}
