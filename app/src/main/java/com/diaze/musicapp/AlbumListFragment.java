package com.diaze.musicapp;

import android.Manifest.permission;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Albums;
import android.provider.MediaStore.Audio.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diaze.musicapp.Adapters.AlbumListAdapter;
import com.diaze.musicapp.Attributes.Album;
import com.diaze.musicapp.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class AlbumListFragment extends Fragment implements AlbumListAdapter.AlbumListItemClickListener{

    private OnFragmentInteractionListener mListener;
    private List<Album> albums;
    private RecyclerView rv;
    private AlbumListAdapter adapter;

    public AlbumListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albums = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albumlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requestPermission();
        rv =(RecyclerView)getView().findViewById(R.id.rv_albumlist);
        initRecyclerView();
        albums = getAlbums();
        adapter = new AlbumListAdapter(albums,this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

    public void initRecyclerView(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rv.setLayoutManager(gridLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));
    }

    public List<Album> getAlbums(){
        List<Album> albums = new ArrayList<>();
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Albums.EXTERNAL_CONTENT_URI;
        String[] projection = {Albums._ID,Albums.ALBUM,Albums.ARTIST,Albums.ALBUM_ART};
        Cursor cursor = contentResolver.query(uri,projection,null,null,Media.ALBUM + " ASC");

        if (cursor != null){
            cursor.moveToFirst();
            int albumIDColumn = cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            int albumTitleColumn = cursor.getColumnIndex(Albums.ALBUM);
            int albumArtistColumn = cursor.getColumnIndex(Albums.ARTIST);
            int albumArtColumn = cursor.getColumnIndex(Albums.ALBUM_ART);

            do{
                Album album;
                long id = cursor.getLong(albumIDColumn);
                String title = cursor.getString(albumTitleColumn);
                String artist = cursor.getString(albumArtistColumn);
                String albumArtPath = cursor.getString(albumArtColumn);

                if (albumArtPath != null && albumArtPath != ""){
                    album = new Album(id,title,artist,albumArtPath);
                }else{
                    album = new Album(id,title,artist);
                }
                albums.add(album);
            }while(cursor.moveToNext());
            return albums;
        }else{
            return null;
        }
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

    @Override
    public void onListItemClick(int index) {
        long id = albums.get(index).getId();
        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        Class fragmentClass = TrackListFragment.class;
        Bundle args = new Bundle();

        try{
            fragment = (Fragment) fragmentClass.newInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
        args.putLong(Constants.BUNDLE_ALBUM_ID_KEY,id);
        fragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.contentFrameLayout, fragment).commit();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
