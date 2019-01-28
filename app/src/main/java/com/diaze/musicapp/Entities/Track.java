package com.diaze.musicapp.Entities;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "track")
public class Track {

    @PrimaryKey
            private int id;

    private String title;
    private String artist;
    private String album;
    private String duration;
    private String filePath;

    public Track(String title, String artist, String album, String duration){
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }

    public String getTitle(){
        return title;
    }

    public String getArtist(){
        return artist;
    }

    public String getAlbum(){
        return album;
    }

    public String getDuration(){
        return duration;
    }
}
