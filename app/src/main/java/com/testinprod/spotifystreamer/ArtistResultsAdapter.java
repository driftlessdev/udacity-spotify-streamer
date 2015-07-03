package com.testinprod.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        boolean resetImage = true;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.search_result_row, parent, false);
            resetImage = false;
        }

        ImageView artistImage = (ImageView) convertView.findViewById(R.id.imageview_artist);
        if(resetImage)
        {
            artistImage.setImageResource(R.mipmap.ic_launcher);
        }
        TextView artistName = (TextView) convertView.findViewById(R.id.textview_artist);
        Artist artist = mArtistResults.get(position);
        artistName.setText(artist.name);
        if(artist.images.size() > 0)
        {

            Picasso.with(parent.getContext()).load(artist.images.get(0).url).into(artistImage);
        }


        return convertView;
    }
}