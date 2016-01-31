package com.example.animemania.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.animemania.R;
import com.example.animemania.constant.Constants;
import com.example.animemania.utility.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> mEpisodeList;
    private static LayoutInflater inflater = null;
    public ImageLoader imageLoader; 

    public ListViewAdapter(Activity a, ArrayList<HashMap<String, String>> episodeList) {
        activity = a;
        mEpisodeList = episodeList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return mEpisodeList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(convertView == null)
            v = inflater.inflate(R.layout.list_item, null);

        TextView title = (TextView) v.findViewById(R.id.title); // title
        title.setSelected(true); // for making scrollable
        TextView artist = (TextView) v.findViewById(R.id.season); // season name
        TextView duration = (TextView) v.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView) v.findViewById(R.id.list_image); // thumb image

        HashMap<String, String> episode = new HashMap<String, String>();
        episode = mEpisodeList.get(position);

        // Setting all values in listview
        title.setText(episode.get(Constants.KEY_TITLE));
        artist.setText(episode.get(Constants.KEY_SEASON));
        duration.setText(episode.get(Constants.KEY_DURATION));
        imageLoader.DisplayImage(episode.get(Constants.KEY_THUMB_URL), thumb_image);
        
        return v;
    }
}