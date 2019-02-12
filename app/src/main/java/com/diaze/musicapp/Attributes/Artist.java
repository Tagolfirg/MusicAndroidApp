package com.diaze.musicapp.Attributes;

public class Artist {
    private long id;
    private String artistName;

    public Artist(long id, String artistName){
        this.id = id;
        this.artistName = artistName;
    }

    public long getId(){
        return id;
    }

    public String getArtistName(){
        return artistName;
    }

}
