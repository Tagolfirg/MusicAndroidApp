package com.diaze.musicapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.diaze.musicapp.Daos.PlaylistDAO;
import com.diaze.musicapp.Daos.TrackDAO;
import com.diaze.musicapp.Entities.Playlist;
import com.diaze.musicapp.Entities.Track;

@Database(entities = {Playlist.class, Track.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "MusicAppDB";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if (sInstance == null){
            synchronized(LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract PlaylistDAO playlistDAO();

    public abstract TrackDAO trackDAO();

}
