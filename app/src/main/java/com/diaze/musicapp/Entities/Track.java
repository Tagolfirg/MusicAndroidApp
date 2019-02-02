package com.diaze.musicapp.Entities;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "track")
public class Track {

    @PrimaryKey
            private long id;

    private String title;
    private String artist;
    private String album;
    private long duration;

    public Track(long id, String title, String artist, String album, long duration){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
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

    public String getAlbum(){
        return album;
    }

    public long getDuration(){
        return duration;
    }

    public long getDurationMilliseconds(){
        return duration;
    }

    public long getDurationSeconds(){
        return duration/1000;
    }

    public String getFormattedTime(){
        int m = (int) (getDurationSeconds()/60);
        int s = (int)(getDurationSeconds()%60);

            if (s < 10){
                return m+":0"+s;
            }else{
                return m+":"+s;
            }
    }

}
