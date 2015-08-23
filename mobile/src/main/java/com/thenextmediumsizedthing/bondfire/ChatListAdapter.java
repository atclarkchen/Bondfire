package com.thenextmediumsizedthing.bondfire;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends ArrayAdapter<GroupClass>{

    private TextView groupName;
    private ImageView groupIcon;

    public ChatListAdapter(Context context, int textViewResouceID, List<GroupClass> objects){
        super(context,textViewResouceID,objects);
    }

    @Override
    public View getView(int pos, View ConvertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View v = theInflater.inflate(R.layout.listobject_chat, parent, false);

        GroupClass groupObj = getItem(pos);
        groupName = (TextView)v.findViewById(R.id.groupName);
        groupName.setText(groupObj.getName());
        groupIcon = (ImageView)v.findViewById(R.id.groupIcon);
        groupIcon.setImageResource(groupObj.getImageId());
        return v;
    }

}
