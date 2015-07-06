package com.testinprod.spotifystreamer;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by Tim on 6/28/2015.
 */
public class SpotifyArtistSearchTask extends AsyncTask<String, Void, ArrayList<Artist>> {
    private static final String LOG_TAG = SpotifyArtistSearchTask.class.getSimpleName();
    private ArtistResultsAdapter mAdapterToUpdate;
    private Context mContext;
    private View mShieldOfJustice;

    @Override
    protected void onPostExecute(ArrayList<Artist> spotifySearchResults) {
        super.onPostExecute(spotifySearchResults);

        if(mShieldOfJustice != null)
        {
            mShieldOfJustice.setVisibility(View.GONE);
        }
        else
        {
            Log.i(LOG_TAG, "Shield of justice missing!");
        }


        boolean searchRan = true;
        if(spotifySearchResults == null)
        {
            spotifySearchResults = new ArrayList<>();
            searchRan = false;
        }

        mAdapterToUpdate.setArtistList(spotifySearchResults);
        if(searchRan && spotifySearchResults.size() == 0)
        {
            Toast.makeText(mContext,"I regret to report that we came up with nil",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        // Ensure progressbar is visible
        if(mShieldOfJustice != null)
        {
            mShieldOfJustice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        // Ensure progressbar is visible
        if(mShieldOfJustice != null)
        {
            mShieldOfJustice.setVisibility(View.GONE);
        }
    }

    @Override
    protected ArrayList<Artist> doInBackground(String... params) {
        if(params.length != 1)
        {
            return null;
        }

        String searchText = params[0].trim();

        if(searchText.isEmpty())
        {
            return null;
        }

        publishProgress();

        try{
            Thread.sleep(3000);
        }
        catch(Exception e)
        {

        }


        // TODO: Implement paging support
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotifyService = api.getService();
        ArtistsPager results = spotifyService.searchArtists(searchText);
        if(isCancelled())
        {
            Log.v(LOG_TAG, "Search canceled after starting");
            return null;
        }
        return new ArrayList<>(results.artists.items);
    }

    public SpotifyArtistSearchTask(ArtistResultsAdapter ResultsAdapter, Context ToastContext, View ShieldOfJustice)
    {
        mAdapterToUpdate = ResultsAdapter;
        mContext = ToastContext;
        mShieldOfJustice = ShieldOfJustice;
    }
}
