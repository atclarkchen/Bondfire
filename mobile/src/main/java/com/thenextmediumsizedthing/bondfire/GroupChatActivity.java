package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class GroupChatActivity extends Activity {

    private ChatArrayAdapter adp;
    private ListView list;
    private EditText chatText;
    private Button send;
    private boolean side = false;
    public List<ChatMessage> messageList = new ArrayList<ChatMessage>();
    private Button back;
    private Button rate;
    private GroupClass group;
    private TextView groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        Bundle b = this.getIntent().getExtras();
        if (b!=null) {
            group = b.getParcelable("group");
        }
        groupName = (TextView)findViewById(R.id.groupName);
        groupName.setText(group.getName());
        send = (Button)findViewById(R.id.button);
        list = (ListView)findViewById(R.id.listview);
        adp = new ChatArrayAdapter(getApplicationContext(),R.layout.chat,messageList);
        chatText = (EditText)findViewById(R.id.input);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }

        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatMessage();
            }
        });

        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adp);
        adp.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                list.setSelection(adp.getCount() - 1);
            }
        });
        back = (Button)findViewById(R.id.backarrow);
        back.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rate = (Button)findViewById(R.id.rateBtn);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putParcelable("group", group);
                Intent startReview = new Intent(GroupChatActivity.this,ReviewActivity.class);
                startReview.putExtras(b);
                startActivity(startReview);
            }
        });
    }

    private boolean sendChatMessage() {
        adp.add(new ChatMessage(side, chatText.getText().toString()));
        chatText.setText("");

        /*side =! side;*/

        return true;
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
