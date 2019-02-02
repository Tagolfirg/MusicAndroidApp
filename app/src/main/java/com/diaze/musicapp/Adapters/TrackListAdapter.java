package com.diaze.musicapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diaze.musicapp.Entities.Track;
import com.diaze.musicapp.R;

import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackListViewHolder> {

    private List<Track> tracks;
    TrackListItemClickListener mOnClickListener;

    public interface TrackListItemClickListener{
        void onListItemClick(int index);
    }

    public TrackListAdapter(List<Track> tracks, TrackListItemClickListener listener){
        this.tracks = tracks;
        mOnClickListener = listener;
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
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    class TrackListViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView artist;
        TextView duration;

        public TrackListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.trackTitle);
            artist = (TextView)itemView.findViewById(R.id.trackArtist);
            duration = (TextView)itemView.findViewById(R.id.trackDuration);
        }

        void bind(int position){
            title.setText(tracks.get(position).getTitle());
            artist.setText(tracks.get(position).getArtist());
            duration.setText(tracks.get(position).getFormattedTime());
        }
    }
}
