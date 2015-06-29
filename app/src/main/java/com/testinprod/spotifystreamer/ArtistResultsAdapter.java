package com.testinprod.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tim on 6/28/2015.
 */
public class ArtistResultsAdapter extends BaseAdapter {
    private ArrayList<String> mArtistResults;

    public ArtistResultsAdapter(ArrayList<String> Artists){
        super();

        mArtistResults = Artists;
    }
    @Override
    public int getCount() {
        return mArtistResults.size();
    }

    @Override
    public Object getItem(int position) {
        return mArtistResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.search_result_row, parent, false);
        }

        TextView artistName = (TextView) convertView.findViewById(R.id.textview_artist);
        artistName.setText(mArtistResults.get(position));

        return convertView;
    }
}