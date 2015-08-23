package com.thenextmediumsizedthing.bondfire;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage>{

    private TextView chatText;
    private List<ChatMessage> MessageList = new ArrayList<ChatMessage>();
    private LinearLayout layout;

    public ChatArrayAdapter(Context context, int textViewResouceID, List<ChatMessage> objects){
        super(context,textViewResouceID,objects);
    }

    public void add(ChatMessage object) {
        MessageList.add(object);
        super.add(object);
    }

    public int getCount() {
        return this.MessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.MessageList.get(index);
    }

    public View getView(int pos, View ConvertView, ViewGroup parent) {
        View v = ConvertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.chat, parent, false);
        }

        layout = (LinearLayout)v.findViewById(R.id.Message1);
        ChatMessage MessageObj = getItem(pos);
        chatText = (TextView)v.findViewById(R.id.SingleMsg);
        chatText.setText(MessageObj.message);
        chatText.setBackgroundResource(MessageObj.left ? R.drawable.greys : R.drawable.blues);
        layout.setGravity(MessageObj.left ? Gravity.START : Gravity.END);
        return v;
    }

    public Bitmap decodeToBitmap(byte[] decodeByte) {
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }
}
