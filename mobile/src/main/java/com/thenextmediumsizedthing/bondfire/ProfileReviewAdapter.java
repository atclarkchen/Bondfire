package com.thenextmediumsizedthing.bondfire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ProfileReviewAdapter extends ArrayAdapter<Review> {


    public ProfileReviewAdapter(Context context, int textViewResouceID, List<Review> objects) {
        super(context,textViewResouceID,objects);
    }

    @Override
    public View getView(int pos, View ConvertView, ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        View v = ConvertView;
        Review reviewObj = getItem(pos);
        if (ConvertView == null) {
            LayoutInflater theInflater = LayoutInflater.from(getContext());
            v = theInflater.inflate(R.layout.layout_profile_review, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) v.findViewById(R.id.text);
            viewHolder.stars = (ImageView) v.findViewById(R.id.stars);
            v.setTag(viewHolder);
            return getView(pos, v, parent);
        }
        mainViewHolder = (ViewHolder) v.getTag();
        mainViewHolder.text.setText(reviewObj.getWritten());
        System.out.println(reviewObj.getRating());
        mainViewHolder.setStars(reviewObj.getRating());
        return v;
    }

    public class ViewHolder {
        TextView text;
        ImageView stars;

        private void setStars(int numStars) {
            switch (numStars) {
                case 0:
                    stars.setImageResource(R.drawable.ratingstars);
                    break;
                case 1:
                    stars.setImageResource(R.drawable.ratingstars1);
                    break;
                case 2:
                    stars.setImageResource(R.drawable.ratingstars2);
                    break;
                case 3:
                    stars.setImageResource(R.drawable.ratingstars3);
                    break;
                case 4:
                    stars.setImageResource(R.drawable.ratingstars4);
                    break;
                case 5:
                    stars.setImageResource(R.drawable.ratingstars5);
                    break;
            }
        }
    }
}
