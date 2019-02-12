package com.diaze.musicapp.Attributes;

public class Album {
    private long id;
    private String title;
    private String artist;
    private String albumArtPath = null;

    public Album(long id, String title, String artist, String albumArt){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.albumArtPath = albumArt;
    }

    public Album(long id, String title, String artist){
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public String getAlbumArtPath(){
        return albumArtPath;
    }


}
