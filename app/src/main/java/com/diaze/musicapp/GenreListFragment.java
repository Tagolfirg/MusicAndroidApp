package com.diaze.musicapp;

import android.Manifest.permission;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Genres;
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

import com.diaze.musicapp.Adapters.GenreListAdapter;
import com.diaze.musicapp.Adapters.GenreListAdapter.GenreListItemClickListener;
import com.diaze.musicapp.Attributes.Genre;
import com.diaze.musicapp.Util.Constants;

import java.util.ArrayList;
import java.util.List;

public class GenreListFragment extends Fragment implements GenreListAdapter.GenreListItemClickListener{

    private List<Genre> genreList;
    private GenreListAdapter adapter;
    private RecyclerView rv;
    private OnFragmentInteractionListener mListener;

    public GenreListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        genreList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requestPermission();
        rv = (RecyclerView)getView().findViewById(R.id.rv_genrelist);
        initRecyclerView();

        genreList = getGenres();
        adapter = new GenreListAdapter(genreList,this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        super.onViewCreated(view, savedInstanceState);
    }

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

    public List<Genre> getGenres(){
        List<Genre> genres = new ArrayList<>();
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Genres.EXTERNAL_CONTENT_URI;
        String[] projection = {Genres._ID, Genres.NAME};
        Cursor cursor = contentResolver.query(uri,projection,null,null,null);

        if (cursor != null){
            cursor.moveToFirst();
            int genreId = cursor.getColumnIndex(Genres._ID);
            int genreName = cursor.getColumnIndex(Genres.NAME);

            do{
                Genre genre;
                long id = cursor.getLong(genreId);
                String name = cursor.getString(genreName);

                genre = new Genre(id,name);
                genres.add(genre);
            }while(cursor.moveToNext());

            return genres;
        }else{
            return null;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
