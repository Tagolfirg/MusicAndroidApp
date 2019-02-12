package com.diaze.musicapp.Attributes;

public class Playlist {
    private int id;
    private String title;
    private int numOfTracks;

    public Playlist(int id, String title, int numOfTracks){
        this.id = id;
        this.title = title;
        this.numOfTracks = numOfTracks;
    }

    public Playlist(String title, int numOfTracks){
        this.title = title;
        this.numOfTracks = numOfTracks;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public int getNumOfTracks(){
        return numOfTracks;
    }

    public com.diaze.musicapp.Entities.Playlist getPlaylistEntity(){
        return new com.diaze.musicapp.Entities.Playlist(id,title,numOfTracks);
    }

}
