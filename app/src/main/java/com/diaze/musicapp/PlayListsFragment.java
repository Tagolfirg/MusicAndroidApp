package com.diaze.musicapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.diaze.musicapp.Adapters.PlayListsAdapter;
import com.diaze.musicapp.Entities.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayListsFragment extends Fragment  implements PlayListsAdapter.PlayListItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private PlayListsAdapter mAdapter;
    private RecyclerView rv;
    List<Playlist> playLists = new ArrayList<>();
    private FloatingActionButton addPlaylist;

    private OnFragmentInteractionListener mListener;

    public PlayListsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_playlists, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView)getView().findViewById(R.id.rv_playlist);
        initRecyclerView();
        mAdapter = new PlayListsAdapter(playLists,this);
        rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        addPlaylist = getView().findViewById(R.id.addPlaylist);
        addPlaylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);

                builder.setView(input);
                builder.setMessage(R.string.add_playlist_mssg);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = input.getText().toString();
                        int randomId;
                        while (true){
                            randomId = new Random().nextInt(10000);
                            if (!checkIfIdExists(randomId))
                                break;
                        }

                        playLists.add(new Playlist(randomId,title,0));
                        mAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create();
                AlertDialog dialog = builder.show();

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public boolean checkIfIdExists(int randomId){
        for (int i=0;i<playLists.size(); i++){
            if (playLists.get(i).getId() == randomId){
                return true;
            }
        }
        return false;
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

    public void populate(){
        Playlist p1 = new Playlist(1,"p1",10);
        Playlist p2 = new Playlist(2,"p2",10);
        Playlist p3 = new Playlist(3,"p3",10);
        Playlist p4 = new Playlist(4,"p4",14);
        Playlist p5 = new Playlist(5,"p5",12);
        Playlist p6 = new Playlist(6,"p6",43);
        Playlist p7 = new Playlist(7,"p7",56);
        Playlist p8 = new Playlist(8,"p8",67);
        Playlist p9 = new Playlist(9,"p9",54);
        Playlist p10 = new Playlist(10,"p10",54);
        Playlist p11 = new Playlist(11,"p11",1);
        Playlist p12 = new Playlist(12,"p12",45);
        Playlist p13= new Playlist(13,"p13",5);
        Playlist p14 = new Playlist(14,"p14",6);
        Playlist p15 = new Playlist(15,"p15",6);
        Playlist p16 = new Playlist(16,"p16",8);
        Playlist p17 = new Playlist(17,"p17",9);
        Playlist p18 = new Playlist(18,"p18",54);
        Playlist p19 = new Playlist(19,"p19",44);
        Playlist p20 = new Playlist(20,"p20",21);
        Playlist p21 = new Playlist(21,"p21",89);
        Playlist p22 = new Playlist(22,"p22",54);

        playLists.add(p1);
        playLists.add(p2);
        playLists.add(p3);
        playLists.add(p4);
        playLists.add(p5);
        playLists.add(p6);
        playLists.add(p7);
        playLists.add(p8);
        playLists.add(p9);
        playLists.add(p10);
        playLists.add(p11);
        playLists.add(p12);
        playLists.add(p13);
        playLists.add(p14);
        playLists.add(p15);
        playLists.add(p16);
        playLists.add(p17);
        playLists.add(p18);
        playLists.add(p19);
        playLists.add(p20);
        playLists.add(p21);
        playLists.add(p22);
    }

    @Override
    public void onListItemClick(int index) {
        Playlist playlistSelected = playLists.get(index);
        int id = playlistSelected.getId();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
