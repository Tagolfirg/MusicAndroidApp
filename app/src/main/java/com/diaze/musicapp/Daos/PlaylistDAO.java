package com.diaze.musicapp.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.diaze.musicapp.Entities.Playlist;

import java.util.List;

@Dao
public interface PlaylistDAO {

    @Query("SELECT * FROM playlist")
    List<Playlist> loadAllTasks();

    @Insert
    void insertPlaylist(Playlist playList);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePlaylist(Playlist playlist);

    @Delete
    void deletePlaylist(Playlist playlist);
}
