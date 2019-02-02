package com.diaze.musicapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import java.util.Random;

import com.diaze.musicapp.Adapters.PlayListsAdapter;
import com.diaze.musicapp.Entities.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlayListsActivity extends AppCompatActivity implements PlayListsAdapter.PlayListItemClickListener{

    private PlayListsAdapter mAdapter;
    private RecyclerView rv;
    List<Playlist> playLists = new ArrayList<>();
    private FloatingActionButton addPlaylist;

    private static final String TAG = PlayListsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_lists);

        rv = (RecyclerView)findViewById(R.id.rv_playlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);
        rv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        populate();

        mAdapter = new PlayListsAdapter(playLists,this);
        rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        addPlaylist = findViewById(R.id.addPlaylist);

        addPlaylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayListsActivity.this);
                final EditText input = new EditText(PlayListsActivity.this);
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
    }

    public boolean checkIfIdExists(int randomId){
        for (int i=0;i<playLists.size(); i++){
            if (playLists.get(i).getId() == randomId){
                return true;
            }
        }
        return false;
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
        Intent startChildActivity = new Intent(this,TrackListActivity.class);
        startChildActivity.putExtra("id",playLists.get(index).getId());
        startChildActivity.putExtra("title",playLists.get(index).getTitle());
        startActivity(startChildActivity);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
