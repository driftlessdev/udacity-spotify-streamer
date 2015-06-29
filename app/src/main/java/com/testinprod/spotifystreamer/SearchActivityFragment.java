package com.testinprod.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


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

        ArrayList<String> artists = new ArrayList<>();
        for(int i = 0; i < 20; i++)
        {
            artists.add("Artist #:" + i);
        }

        ArtistResultsAdapter resultsAdapter = new ArtistResultsAdapter(artists);

        ListView resultList = (ListView) rootView.findViewById(R.id.listview_results);
        resultList.setAdapter(resultsAdapter);

        return rootView;
    }
}
