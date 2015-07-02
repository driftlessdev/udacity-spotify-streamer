package com.testinprod.spotifystreamer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistSimple;

/**
 * Created by Tim on 7/1/2015.
 */
public class ArtistSimpleParcelable extends ArtistSimple implements Parcelable {
    public static final String EXTRA_SIMPLE_ARTIST = "EXTRA_"+ArtistResultsAdapter.class.getSimpleName();

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(external_urls);
        dest.writeString(href);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(uri);
    }

    public ArtistSimpleParcelable(Artist artist){
        external_urls = artist.external_urls;
        href = artist.href;
        id = artist.id;
        name = artist.name;
        type = artist.type;
        uri = artist.uri;
    }

    private ArtistSimpleParcelable(Parcel source)
    {
        external_urls = new HashMap<String, String>();
        source.readMap(external_urls, String.class.getClassLoader());
        href = source.readString();
        id = source.readString();
        name = source.readString();
        type = source.readString();
        uri = source.readString();
    }

    public static final Parcelable.Creator<ArtistSimpleParcelable> CREATOR = new Parcelable.Creator<ArtistSimpleParcelable>() {
        @Override
        public ArtistSimpleParcelable createFromParcel(Parcel source) {
            return new ArtistSimpleParcelable(source);
        }

        @Override
        public ArtistSimpleParcelable[] newArray(int size) {
            return new ArtistSimpleParcelable[size];
        }
    };
}
