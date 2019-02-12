package com.diaze.musicapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diaze.musicapp.Attributes.Artist;
import com.diaze.musicapp.R;

import java.util.List;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistListViewHolder>{

    private List<Artist> artistList;
    private ArtistListItemClickListener mOnClickListener;

    public interface ArtistListItemClickListener{
        void onListItemClick(int index);
    }

    public ArtistListAdapter(List<Artist> artists, ArtistListItemClickListener listener){
        this.artistList = artists;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public ArtistListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.artist_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(layoutIdForListItems,parent,shouldAttachToParent);
        ArtistListViewHolder viewHolder = new ArtistListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    class ArtistListViewHolder extends RecyclerView.ViewHolder{
        TextView artist;

        public ArtistListViewHolder(View itemView) {
            super(itemView);
            artist = (TextView) itemView.findViewById(R.id.artistTitle);
        }

        void bind(int position){
            artist.setText(artistList.get(position).getArtistName());
        }
    }
}
