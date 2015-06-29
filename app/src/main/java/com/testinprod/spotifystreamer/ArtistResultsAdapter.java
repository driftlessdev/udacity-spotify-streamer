package com.testinprod.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by Tim on 6/28/2015.
 */
public class ArtistResultsAdapter extends BaseAdapter {
    private static final String LOG_TAG = ArtistResultsAdapter.class.getSimpleName();
    private ArrayList<Artist> mArtistResults;

    public ArtistResultsAdapter(){
        super();

        mArtistResults = new ArrayList<>();
    }

    public void setArtistPager(ArrayList<Artist> NewPager)
    {
        // TODO: Handle zero results
        mArtistResults = NewPager;
        notifyDataSetChanged();
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
        artistName.setText(mArtistResults.get(position).name);

        return convertView;
    }
}