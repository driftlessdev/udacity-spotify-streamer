package com.testinprod.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.security.InvalidParameterException;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTenSongsActivityFragment extends Fragment {
    private static final String LOG_TAG = TopTenSongsActivityFragment.class.getSimpleName();

    public static TopTenSongsActivityFragment newInstance(ArtistSimpleParcelable artist)
    {
        TopTenSongsActivityFragment fragment = new TopTenSongsActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ArtistSimpleParcelable.EXTRA_SIMPLE_ARTIST, artist);
        fragment.setArguments(args);
        return fragment;
    }

    public TopTenSongsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_ten_songs, container, false);

        Bundle args = getArguments();
        ArtistSimpleParcelable artistSimpleParcelable = null;

        if(args != null)
        {
            artistSimpleParcelable = args.getParcelable(ArtistSimpleParcelable.EXTRA_SIMPLE_ARTIST);

        }

        if(artistSimpleParcelable != null)
        {
            Log.v(LOG_TAG, "Artist: " + artistSimpleParcelable.name + " - " + artistSimpleParcelable.id);
            Toast.makeText(getActivity(), "Artist: " + artistSimpleParcelable.name + " - " + artistSimpleParcelable.id, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.e(LOG_TAG,"Missing Artist", new InvalidParameterException("Artist is required for the activity"));
        }



        return rootView;
    }
}
