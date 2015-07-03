package com.testinprod.spotifystreamer;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by Tim on 6/28/2015.
 */
public class SpotifyTopTenTask extends AsyncTask<String, Void, ArrayList<Track>> {
    private static final String LOG_TAG = SpotifyTopTenTask.class.getSimpleName();
    private TracksAdapter mAdapterToUpdate;
    private Context mActivityContext;

    @Override
    protected void onPostExecute(ArrayList<Track> topTracks) {
        super.onPostExecute(topTracks);

        mAdapterToUpdate.setTracks(topTracks);
        if(topTracks.size() == 0)
        {
            Toast.makeText(mActivityContext, "Seems no one has ever listed to this artist before, no top tracks found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected ArrayList<Track> doInBackground(String... params) {
        if(params.length != 1)
        {
            return null;
        }

        String artistId = params[0].trim();

        if(artistId.isEmpty())
        {
            return new ArrayList<>();
        }

        HashMap<String, Object> options = new HashMap<>();
        options.put(SpotifyService.COUNTRY,"US");
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotifyService = api.getService();

        Tracks topTen = spotifyService.getArtistTopTrack(artistId, options);

        return new ArrayList<>(topTen.tracks);
    }

    public SpotifyTopTenTask(TracksAdapter ResultsAdapter, Context ApplicationContext)
    {
        mAdapterToUpdate = ResultsAdapter;
        mActivityContext = ApplicationContext;
    }
}
