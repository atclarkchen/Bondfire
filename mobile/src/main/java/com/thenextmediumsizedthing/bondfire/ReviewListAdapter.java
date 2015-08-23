package com.thenextmediumsizedthing.bondfire;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReviewListAdapter extends ArrayAdapter<Person>{

    public ReviewListAdapter(Context context, int textViewResouceID, List<Person> objects){
        super(context, textViewResouceID, objects);
    }

    @Override
    public View getView(int pos, View ConvertView, ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        View v = ConvertView;
        Person personObj = getItem(pos);
        if (ConvertView == null) {
            LayoutInflater theInflater = LayoutInflater.from(getContext());
            v = theInflater.inflate(R.layout.layout_review, parent, false);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.fullName = (TextView) v.findViewById(R.id.name);
            viewHolder.icon = (ImageView) v.findViewById(R.id.icon);
            viewHolder.text = (EditText) v.findViewById(R.id.groupComments);
            viewHolder.star1 = (ToggleButton) v.findViewById(R.id.star1);
            viewHolder.star2 = (ToggleButton) v.findViewById(R.id.star2);
            viewHolder.star3 = (ToggleButton) v.findViewById(R.id.star3);
            viewHolder.star4 = (ToggleButton) v.findViewById(R.id.star4);
            viewHolder.star5 = (ToggleButton) v.findViewById(R.id.star5);
            setStar1(viewHolder);
            setStar2(viewHolder);
            setStar3(viewHolder);
            setStar4(viewHolder);
            setStar5(viewHolder);
            v.setTag(viewHolder);
            return getView(pos, v, parent);
        } else {
            mainViewHolder = (ViewHolder) v.getTag();
            mainViewHolder.fullName.setText(personObj.getfName() + " " + personObj.getlName());
            mainViewHolder.icon.setImageResource(personObj.getImageId());
        }
        return v;
    }

    public class ViewHolder {
        TextView fullName;
        ImageView icon;
        EditText text;
        ToggleButton star1;
        ToggleButton star2;
        ToggleButton star3;
        ToggleButton star4;
        ToggleButton star5;
        Boolean star1Bool = false;
    }

    private void setStar5(final ViewHolder viewHolder) {
        viewHolder.star5.setChecked(false);
        viewHolder.star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.star1.setChecked(true);
                viewHolder.star2.setChecked(true);
                viewHolder.star3.setChecked(true);
                viewHolder.star4.setChecked(true);
                viewHolder.star5.setChecked(true);
                viewHolder.star1Bool = false;
            }
        });
    }

    private void setStar4(final ViewHolder viewHolder) {
        viewHolder.star4.setChecked(false);
        viewHolder.star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.star1.setChecked(true);
                viewHolder.star2.setChecked(true);
                viewHolder.star3.setChecked(true);
                viewHolder.star4.setChecked(true);
                viewHolder.star5.setChecked(false);
                viewHolder.star1Bool = false;
            }
        });
    }

    private void setStar3(final ViewHolder viewHolder) {
        viewHolder.star3.setChecked(false);
        viewHolder.star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.star1.setChecked(true);
                viewHolder.star2.setChecked(true);
                viewHolder.star3.setChecked(true);
                viewHolder.star4.setChecked(false);
                viewHolder.star5.setChecked(false);
                viewHolder.star1Bool = false;
            }
        });
    }

    private void setStar2(final ViewHolder viewHolder) {
        viewHolder.star2.setChecked(false);
        viewHolder.star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.star1.setChecked(true);
                viewHolder.star2.setChecked(true);
                viewHolder.star3.setChecked(false);
                viewHolder.star4.setChecked(false);
                viewHolder.star5.setChecked(false);
                viewHolder.star1Bool = false;
            }
        });
    }

    private void setStar1(final ViewHolder viewHolder) {
        viewHolder.star1.setChecked(viewHolder.star1Bool);
        viewHolder.star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.star1.setChecked(!viewHolder.star1Bool);
                viewHolder.star2.setChecked(false);
                viewHolder.star3.setChecked(false);
                viewHolder.star4.setChecked(false);
                viewHolder.star5.setChecked(false);
                viewHolder.star1Bool = !viewHolder.star1Bool;
            }
        });
    }

}
