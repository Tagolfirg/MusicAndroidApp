package com.diaze.musicapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.diaze.musicapp.Adapters.TrackListAdapter;

public class TrackListActivity extends AppCompatActivity {

    TrackListAdapter mAdapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);
    }
}
