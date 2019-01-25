package com.diaze.musicapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diaze.musicapp.TrackListAdapter.TrackListViewHolder;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListViewHolder> {

    private int numberOfItems;

    public TrackListAdapter(int numberOfItems){
        this.numberOfItems = numberOfItems;
    }

    @NonNull
    @Override
    public TrackListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.track_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(layoutIdForListItems,parent,shouldAttachToParent);
        TrackListViewHolder viewHolder = new TrackListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TrackListViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView artist;

        public TrackListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.trackTitle);
            artist = (TextView)itemView.findViewById(R.id.trackArtist);
        }

        void bind(String title, String artist){
            this.title.setText(title);
            this.artist.setText(artist);
        }
    }
}
