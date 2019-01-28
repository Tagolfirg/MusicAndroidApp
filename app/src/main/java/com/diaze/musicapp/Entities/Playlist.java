package com.diaze.musicapp.Entities;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "playlist")

public class Playlist {

    @PrimaryKey
            private int id;

    private String title;
    private int numOfTracks;


    public Playlist(int id, String title, int numOfTracks){
        this.id = id;
        this.title = title;
        this.numOfTracks = numOfTracks;
    }

    @Ignore
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

}
