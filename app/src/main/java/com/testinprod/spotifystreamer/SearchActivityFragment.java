package com.testinprod.spotifystreamer;

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
            private Timer mTimer = new Timer();
            private final long DELAY = 350;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO: Is there a better way to handle this other than a timer? A Handler maybe?
                mTimer.cancel();
                mTimer = new Timer();
                mTimer.schedule(new ArtistSearchTimerTask(s.toString()), DELAY);

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
            SpotifyArtistSearchTask spotifyArtistSearchTask = new SpotifyArtistSearchTask(mResultsAdapter);
            spotifyArtistSearchTask.execute(mSearchText);
        }
    }
}
