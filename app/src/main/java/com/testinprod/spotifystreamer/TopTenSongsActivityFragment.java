package com.testinprod.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class TopTenSongsActivityFragment extends Fragment {
    private static final String LOG_TAG = TopTenSongsActivityFragment.class.getSimpleName();

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
        else
        {

            artistSimpleParcelable = getActivity().getIntent().getParcelableExtra(ArtistSimpleParcelable.EXTRA_SIMPLE_ARTIST);
        }

        if(artistSimpleParcelable != null)
        {
            Log.v(LOG_TAG, "Artist: " + artistSimpleParcelable.name + " - " + artistSimpleParcelable.id);
            Toast.makeText(getActivity(), "Artist: " + artistSimpleParcelable.name + " - " + artistSimpleParcelable.id, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Log.e(LOG_TAG,"Missing Artist");
        }



        return rootView;
    }
}
