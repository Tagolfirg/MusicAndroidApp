package com.diaze.musicapp.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.diaze.musicapp.Entities.Track;

import java.util.List;

@Dao
public interface TrackDAO {
    @Query("SELECT * FROM track")
    List<Track> loadAllTracks();

}
