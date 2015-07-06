package com.testinprod.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    private View mShieldOfJustice;

    public SearchActivityFragment() {
        mResultsAdapter = new ArtistResultsAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_search, container, false);


        mShieldOfJustice = rootView.findViewById(R.id.soj_search);


        final EditText searchText = (EditText) rootView.findViewById(R.id.edittext_search);
        searchText.addTextChangedListener(new SearchTextWatcher());

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

    public class SearchTextWatcher implements TextWatcher {

        private Runnable mRunnable;
        private Handler mHandler = new Handler();
        private SpotifyArtistSearchTask mSearchTask;
        private final long DELAY = 500;
        private String mLastSearch;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(mRunnable != null)
            {
                // Stop if scheduled
                mSearchTask.cancel(true);
                mHandler.removeCallbacks(mRunnable);
                mSearchTask = null;
                mRunnable = null;
                Log.v(LOG_TAG, "Canceled search of: " + mLastSearch);
            }
            mLastSearch = s.toString();
            mSearchTask = new SpotifyArtistSearchTask(mResultsAdapter,getActivity(), mShieldOfJustice);
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    mSearchTask.execute(mLastSearch);
                }
            };
            mHandler.postDelayed(mRunnable, DELAY);
            Log.v(LOG_TAG, "Initiated search with: " + mLastSearch);
        }
    }
}
