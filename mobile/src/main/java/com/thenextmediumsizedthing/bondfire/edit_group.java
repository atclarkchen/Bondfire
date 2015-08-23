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


public class edit_group extends Activity {
    private group_obj current;
    private ImageView group_image;
    private EditText group_name;
    private EditText group_plan;
    private EditText group_blurb;
    private TextView group_tags;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        setBackButton();
        //retrieving group
//        Bundle b = getIntent().getExtras();
//        int group_index = b.getInt("Own");
        current = Create_group.all_groups.get(0);

        //setting up
//        group_image = (ImageView) findViewById(R.id.group_image);
//        group_image.setImageDrawable(current.get_image());
        group_name = (EditText) findViewById(R.id.group_name);
        group_name.setText(current.get_name());
        group_plan = (EditText) findViewById(R.id.plan_text);
        group_plan.setText(current.get_plan());
        group_blurb = (EditText) findViewById(R.id.blurb_text);
        group_blurb.setText(current.get_blurb());
//        group_tags = (TextView) findViewById(R.id.tags);
//        String s = current.get_tags().toString();
//        s = s.substring(1, s.length()-1).replaceAll(",", "");
//        group_tags.setText(s);
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
        getMenuInflater().inflate(R.menu.menu_edit_group, menu);
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

    public void onClickDone(View v){
        Create_group.all_groups.remove(current);
        current.set_group(group_name.getText().toString(), current.get_members(),
                group_plan.getText().toString(), group_blurb.getText().toString(), new String[0]);
        Create_group.all_groups.add(current);
        Intent done = new Intent(this, own_existing_group.class);
        startActivity(done);
    }

}
