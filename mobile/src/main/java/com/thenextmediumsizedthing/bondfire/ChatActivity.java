package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends Activity {

    private Button back;
    private List<GroupClass> groups;
    private ListView listView;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle b = getIntent().getExtras();
        String name = b.getString("name");
        groups = new ArrayList<GroupClass>();
        int resID = getResources().getIdentifier("jessica", "drawable", "com.thenextmediumsizedthing.bondfire");
        groups.add(new GroupClass(name,resID));
        setBackButton();
        fillListView();
        registerClickList();
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

    private void fillListView() {
        adapter = new ChatListAdapter(this,R.layout.listobject_chat,groups);
        listView = (ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    private void registerClickList() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupClass group = (GroupClass)parent.getItemAtPosition(position);
                Bundle b = new Bundle();
                b.putParcelable("group", group);
                Intent startChat = new Intent(ChatActivity.this,GroupChatActivity.class);
                startChat.putExtras(b);
                startActivity(startChat);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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
