package com.diaze.musicapp;
import com.diaze.musicapp.util.Constants;
import android.Manifest;
import android.Manifest.permission;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import com.diaze.musicapp.Adapters.TrackListAdapter;
import com.diaze.musicapp.Entities.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrackListActivity extends AppCompatActivity implements  TrackListAdapter.TrackListItemClickListener{

    private TrackListAdapter mAdapter;
    private RecyclerView rv;
    private List<Track> tracks = new ArrayList<>();

    private static final String TAG = TrackListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);

        requestPermission(); //request permission from user

        rv = findViewById(R.id.rv_tracklist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        getTracks();
        sortList();

        mAdapter = new TrackListAdapter(tracks,this);
        rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void getTracks(){
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri,null,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
            int titleColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(Media._ID);
            int artistColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int albumColumn = cursor.getColumnIndex(Media.ALBUM);

            int durationColumn = cursor.getColumnIndex(Media.DURATION);

            do{
                long id = cursor.getLong(idColumn);
                String title = cursor.getString(titleColumn);
                String artist = cursor.getString(artistColumn);
                String album = cursor.getString(albumColumn);
                long duration = cursor.getLong(durationColumn);
                tracks.add(new Track(id,title,artist,album,duration));
            }while (cursor.moveToNext());
        }
    }

    public void sortList(){
        Collections.sort(tracks, new Comparator<Track>() {
            @Override
            public int compare(Track t1, Track t2) {
                return t1.getArtist().compareTo(t2.getArtist());
            }
        });
    }

    public void requestPermission(){
        if (ContextCompat.checkSelfPermission
                (this, permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this,new String[]
                        {permission.READ_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }

        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults){
        switch(requestCode){
            case Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                return;
            }
        }
    }

    @Override
    public void onListItemClick(int index) {

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



}
