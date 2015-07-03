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

import kaaes.spotify.webapi.android.models.Track;

/**
 * Created by Tim on 7/2/2015.
 */
public class TracksAdapter extends BaseAdapter {
    ArrayList<Track> mTracks;

    public TracksAdapter(){
        super();

        mTracks = new ArrayList<>();
    }

    public void setTracks(ArrayList<Track> tracks)
    {
        mTracks = tracks;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTracks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView = inflater.inflate(R.layout.track_row,parent,false);
            resetImage = false;
        }

        // Reset image to default if the view is reused.
        ImageView albumImage = (ImageView) convertView.findViewById(R.id.imageview_track);
        if(resetImage){
            albumImage.setImageResource(R.mipmap.ic_launcher);
        }

        TextView trackName = (TextView) convertView.findViewById(R.id.textview_track);
        Track track = mTracks.get(position);
        trackName.setText(track.name);
        TextView albumName = (TextView) convertView.findViewById(R.id.textview_album);
        albumName.setText(track.album.name);
        if(track.album.images.size() > 0)
        {
            Picasso.with(parent.getContext()).load(track.album.images.get(0).url).into(albumImage);
        }
        return convertView;
    }
}
