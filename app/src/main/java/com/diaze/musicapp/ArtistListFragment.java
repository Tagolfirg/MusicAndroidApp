package com.diaze.musicapp;

import android.Manifest.permission;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Artists;
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

import com.diaze.musicapp.Adapters.ArtistListAdapter;
import com.diaze.musicapp.Attributes.Artist;
import com.diaze.musicapp.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ArtistListFragment extends Fragment implements ArtistListAdapter.ArtistListItemClickListener {

    private OnFragmentInteractionListener mListener;
    private ArtistListAdapter adapter;
    private RecyclerView rv;
    private List<Artist> artists;

    public ArtistListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artists = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requestPermission();
        rv =(RecyclerView)getView().findViewById(R.id.rv_artistlist);
        initRecyclerView();
        artists = getArtists();

        adapter = new ArtistListAdapter(artists,this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.onViewCreated(view, savedInstanceState);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.HORIZONTAL));
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

    public List<Artist> getArtists(){
        List<Artist> artists = new ArrayList<>();
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Artists.EXTERNAL_CONTENT_URI;
        String[] projection = {Artists._ID,Artists.ARTIST};
        Cursor cursor = contentResolver.query(uri,projection,null,null,Media.ARTIST + " ASC");

        if (cursor != null){
            cursor.moveToFirst();
            int artistIDColumn = cursor.getColumnIndex(Artists._ID);
            int artistTitleColumn = cursor.getColumnIndex(Artists.ARTIST);

            do{
                Artist artist;
                long id = cursor.getLong(artistIDColumn);
                String artistTitle = cursor.getString(artistTitleColumn);

                artist = new Artist(id,artistTitle);
                artists.add(artist);
            }while(cursor.moveToNext());
            return artists;
        }else{
            return null;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
