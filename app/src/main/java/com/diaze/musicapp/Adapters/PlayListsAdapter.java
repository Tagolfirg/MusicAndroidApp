package com.diaze.musicapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.diaze.musicapp.Entities.Playlist;
import com.diaze.musicapp.R;

import java.util.List;

public class PlayListsAdapter extends RecyclerView.Adapter<PlayListsAdapter.PlaylistViewHolder> {

    private List<Playlist> playLists;
    PlayListItemClickListener mOnClickListener;

    public interface PlayListItemClickListener{
        void onListItemClick(int index);
    }

    public PlayListsAdapter(List<Playlist> playLists, PlayListItemClickListener listener){
        this.playLists = playLists;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.playlist_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(layoutIdForListItems,parent,shouldAttachToParent);
        PlaylistViewHolder viewHolder = new PlaylistViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView numOfTracks;

        public PlaylistViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.playlistTitle);
            numOfTracks = (TextView)itemView.findViewById(R.id.numTrackItems);
        }

        void bind(int position){
            Playlist playlist = playLists.get(position);
            title.setText(playlist.getTitle());
            numOfTracks.setText(playlist.getNumOfTracks()+" tracks");
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
