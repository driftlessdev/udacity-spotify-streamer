package com.testinprod.spotifystreamer;

import android.os.AsyncTask;

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

    @Override
    protected void onPostExecute(ArrayList<Artist> spotifySearchResults) {
        super.onPostExecute(spotifySearchResults);

        mAdapterToUpdate.setArtistPager(spotifySearchResults);
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
            return new ArrayList<>();
        }

        // TODO: Implement paging support
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotifyService = api.getService();
        ArtistsPager results = spotifyService.searchArtists(searchText);
        ArrayList<Artist> artists = new ArrayList<>(results.artists.items);
        return artists;
    }

    public SpotifyArtistSearchTask(ArtistResultsAdapter ResultsAdapter)
    {
        mAdapterToUpdate = ResultsAdapter;
    }
}
