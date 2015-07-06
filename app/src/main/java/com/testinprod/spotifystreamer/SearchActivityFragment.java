package com.testinprod.spotifystreamer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Timer;
import java.util.TimerTask;

import kaaes.spotify.webapi.android.models.Artist;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {
    private static final String LOG_TAG = SearchActivityFragment.class.getSimpleName();

    public static SearchActivityFragment newInstance(){
        SearchActivityFragment fragment = new SearchActivityFragment();
        return fragment;
    }

    private ArtistResultsAdapter mResultsAdapter;

    public SearchActivityFragment() {
        mResultsAdapter = new ArtistResultsAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_search, container, false);


        // Run search 500ms after the last time they edit the text

        EditText searchText = (EditText) rootView.findViewById(R.id.edittext_search);
        searchText.addTextChangedListener(new TextWatcher() {
            private Timer mTimer;
            private final long DELAY = 350;
            private String mLastSearch;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO: Is there a better way to handle this other than a timer? A Handler maybe?
                // TODO: Actually cancel the search, right now not doing anything
                if(mTimer != null)
                {
                    // Stop if scheduled
                    mTimer.cancel();
                    Log.v(LOG_TAG, "Canceled search of: " + mLastSearch);
                }
                mLastSearch = s.toString();
                mTimer = new Timer();
                mTimer.schedule(new ArtistSearchTimerTask(mLastSearch), DELAY);
                Log.v(LOG_TAG, "Initiated search with: " + mLastSearch);
            }
        });

        ListView resultList = (ListView) rootView.findViewById(R.id.listview_results);
        resultList.setAdapter(mResultsAdapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist selected = (Artist) mResultsAdapter.getItem(position);
                Intent topSongsIntent = new Intent(getActivity(),TopTenSongsActivity.class);
                ArtistSimpleParcelable artistSimpleParcelable = new ArtistSimpleParcelable(selected);
                Log.v(LOG_TAG,"Artist: " + artistSimpleParcelable.name + " - " + artistSimpleParcelable.id);
                topSongsIntent.putExtra(ArtistSimpleParcelable.EXTRA_SIMPLE_ARTIST,artistSimpleParcelable);
                startActivity(topSongsIntent);
            }
        });

        return rootView;
    }

    public class ArtistSearchTimerTask extends TimerTask{
        private String mSearchText;

        private ArtistSearchTimerTask(String SearchTest)
        {
            mSearchText = SearchTest;
        }
        @Override
        public void run() {
            Activity activity = getActivity();
            if(activity == null)
            {
                return;
            }
            SpotifyArtistSearchTask spotifyArtistSearchTask = new SpotifyArtistSearchTask(mResultsAdapter, getActivity(), activity.findViewById(R.id.soj_search));
            spotifyArtistSearchTask.execute(mSearchText);
        }
    }
}
