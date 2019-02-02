package com.diaze.musicapp.Services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore.Audio.Media;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.MediaController.MediaPlayerControl;

import com.diaze.musicapp.Entities.Track;

import java.util.ArrayList;

public class TrackService extends Service implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

    private final IBinder trackBind = new TrackBinder();
    private MediaPlayer mp;
    private Track track;

    @Override
    public void onCreate() {
        super.onCreate();
        mp = new MediaPlayer();
        initMusicPlayer();
    }

    public void initMusicPlayer(){
        mp.setWakeMode(getApplicationContext(),PowerManager.PARTIAL_WAKE_LOCK);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return trackBind;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        mp.stop();
        mp.release();
        return false;
    }

    public void playTrack(){
        mp.reset();
        long trackId = track.getId();
        Uri uri = ContentUris.withAppendedId
                (android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,trackId);
        try{
            mp.setDataSource(getApplicationContext(),uri);
        }catch(Exception e){
            e.printStackTrace();
        }
        mp.prepareAsync();
    }

    public void setTrack(Track track){
        this.track = track;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    public class TrackBinder extends Binder {
        public TrackService getService(){
            return TrackService.this;
        }
    }

}
