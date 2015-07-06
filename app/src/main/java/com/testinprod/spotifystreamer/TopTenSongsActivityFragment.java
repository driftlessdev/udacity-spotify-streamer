package com.testinprod.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.security.InvalidParameterException;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTenSongsActivityFragment extends Fragment {
    private static final String LOG_TAG = TopTenSongsActivityFragment.class.getSimpleName();

    private TracksAdapter mTrackAdapter;

    public static TopTenSongsActivityFragment newInstance(ArtistSimpleParcelable artist)
    {
        TopTenSongsActivityFragment fragment = new TopTenSongsActivityFragment();
        Bundle args = new Bundle();
        args.putParcelable(ArtistSimpleParcelable.EXTRA_SIMPLE_ARTIST, artist);
        fragment.setArguments(args);
        return fragment;
    }

    public TopTenSongsActivityFragment() {
        mTrackAdapter = new TracksAdapter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_ten_songs, container, false);
        if(savedInstanceState == null)
        {
            Log.v(LOG_TAG, "No saved instance state");
        }
        else
        {
            Log.v(LOG_TAG, "Saved instance state");
        }

        ListView lvTopTen = (ListView) rootView.findViewById(R.id.listview_topsongs);
        lvTopTen.setAdapter(mTrackAdapter);

        if(savedInstanceState == null)
        {
            // First go, so load the top ten
            loadTopTen(rootView.findViewById(R.id.soj_top_ten));
        }

        return rootView;
    }

    private void loadTopTen(View ShieldOfJustice)
    {
        Bundle args = getArguments();
        ArtistSimpleParcelable artistSimpleParcelable = null;

        if(args != null)
        {
            artistSimpleParcelable = args.getParcelable(ArtistSimpleParcelable.EXTRA_SIMPLE_ARTIST);

        }

        if(artistSimpleParcelable != null)
        {
            Log.v(LOG_TAG, "Artist: " + artistSimpleParcelable.name + " - " + artistSimpleParcelable.id);
        }
        else
        {
            Log.e(LOG_TAG,"Missing Artist", new InvalidParameterException("Artist is required for the activity"));
            return;
        }

        SpotifyTopTenTask spotifyTopTenTask = new SpotifyTopTenTask(mTrackAdapter, getActivity(), ShieldOfJustice);
        spotifyTopTenTask.execute(artistSimpleParcelable.id);
    }
}
