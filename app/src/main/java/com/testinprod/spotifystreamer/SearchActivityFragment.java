package com.testinprod.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchActivityFragment extends Fragment {
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
            SpotifySearchTask spotifySearchTask = new SpotifySearchTask(mResultsAdapter);
            spotifySearchTask.execute(mSearchText);
        }
    }
}
