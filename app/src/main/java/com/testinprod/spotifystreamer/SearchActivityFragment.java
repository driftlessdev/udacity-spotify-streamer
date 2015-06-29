package com.testinprod.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {

    public SearchActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_search, container, false);

        ArtistResultsAdapter resultsAdapter = new ArtistResultsAdapter();

        ListView resultList = (ListView) rootView.findViewById(R.id.listview_results);
        resultList.setAdapter(resultsAdapter);

        SpotifySearchTask spotifySearchTask = new SpotifySearchTask(resultsAdapter);
        spotifySearchTask.execute("front");

        return rootView;
    }
}
