package com.diaze.musicapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.diaze.musicapp.Entities.Track;
import com.diaze.musicapp.Services.TrackService;
import com.diaze.musicapp.Services.TrackService.TrackBinder;

import java.util.ArrayList;

public class TrackActivity extends AppCompatActivity {

    private TrackService trackService;
    private Intent playIntent;
    private boolean trackBound = false;
    private Track track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playIntent == null){
            playIntent = new Intent(this,TrackService.class);
            bindService(playIntent,connection,Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackBinder binder = (TrackBinder)service;
            trackService = binder.getService();
            trackService.setTrack(track);
            trackBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            trackBound = false;
        }
    };

}
