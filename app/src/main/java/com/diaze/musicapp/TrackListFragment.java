package com.diaze.musicapp;

import android.Manifest.permission;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diaze.musicapp.Adapters.TrackListAdapter;
import com.diaze.musicapp.Entities.Track;
import com.diaze.musicapp.Util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrackListFragment extends Fragment implements TrackListAdapter.TrackListItemClickListener{
    private OnFragmentInteractionListener mListener;
    private TrackListAdapter mAdapter;
    private RecyclerView rv;
    private List<Track> tracks = new ArrayList<>();

    public TrackListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracklist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String projection[] = {Media._ID,Media.TITLE,Media.ARTIST,Media.ALBUM,Media.DURATION};
        requestPermission(); //request permission from user
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        rv = (RecyclerView)getView().findViewById(R.id.rv_tracklist);
        initRecyclerView();

        Bundle bundle = getArguments();

        if (bundle != null){
            if (bundle.containsKey(Constants.BUNDLE_ALBUM_ID_KEY)){
                long album_id = getArguments().getLong(Constants.BUNDLE_ALBUM_ID_KEY);
                tracks = getTracksByAlbum(album_id,contentResolver,uri,projection);
            }else if (bundle.containsKey(Constants.BUNDLE_ARTIST_ID_KEY)){
                long artist_id = getArguments().getLong(Constants.BUNDLE_ARTIST_ID_KEY);
                tracks = getTracksByArtist(artist_id,contentResolver,uri,projection);
            }
        }else{
            tracks = getAllTracks(contentResolver,uri);
        }

        sortList();

        mAdapter = new TrackListAdapter(tracks,this);
        rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        super.onViewCreated(view, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(int index) {

    }

    public void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
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
                (getContext(), permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    permission.READ_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(getActivity(),new String[]
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

    public List<Track> getTracksByAlbum(long albumId,
                                               ContentResolver contentResolver,
                                               Uri uri,
                                               String[] projection){

        String selection = Media.ALBUM_ID+"=?";
        String args[] = {Long.toString(albumId)};
        Cursor cursor = contentResolver.query(uri,
                projection,selection,args,null);

        if (cursor != null){
            cursor.moveToFirst();
            int idColumn = cursor.getColumnIndex(Media._ID);
            List<Track> albumTracks = new ArrayList<>();
            int titleColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
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
                albumTracks.add(new Track(id,title,artist,album,duration));

            }while(cursor.moveToNext());
            return albumTracks;
        }
        return null;
    }

    public List<Track> getAllTracks(ContentResolver contentResolver,
                                           Uri uri){
        List<Track> tracks = new ArrayList<>();
        Cursor cursor = contentResolver.query(uri,
                null,null,null,null);

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
        return null;
    }

    public List<Track> getTracksByArtist(long artistId,
                                                ContentResolver contentResolver,
                                                Uri uri,
                                                String[] projection){
        String selection = Media.ARTIST_ID+"=?";
        String args[] = {Long.toString(artistId)};
        Cursor cursor = contentResolver.query(uri,
                projection,selection,args,null);

        if (cursor != null){
            cursor.moveToFirst();
            int idColumn = cursor.getColumnIndex(Media._ID);
            List<Track> artistTracks = new ArrayList<>();
            int titleColumn = cursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
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
                artistTracks.add(new Track(id,title,artist,album,duration));

            }while(cursor.moveToNext());
            return artistTracks;
        }
        return null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
