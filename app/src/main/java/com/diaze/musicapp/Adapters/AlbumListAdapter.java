package com.diaze.musicapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diaze.musicapp.Attributes.Album;
import com.diaze.musicapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder>{

    private List<Album> albums;
    private AlbumListItemClickListener mOnClickListener;

    public interface AlbumListItemClickListener{
        void onListItemClick(int index);
    }

    public AlbumListAdapter(List<Album> albums, AlbumListItemClickListener listener){
        this.albums = albums;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public AlbumListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.album_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(layoutIdForListItems,parent,shouldAttachToParent);
        AlbumListViewHolder viewHolder = new AlbumListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListViewHolder holder, int position) {
        holder.bind(position);
    }

    public int getItemCount(){
        return albums.size();
    }

    class AlbumListViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView artist;
        ImageView art;

        public AlbumListViewHolder(View itemView) {
            super(itemView);
            artist = (TextView)itemView.findViewById(R.id.artistTitle);
            title = (TextView)itemView.findViewById(R.id.albumTitle);
            art = (ImageView)itemView.findViewById(R.id.albumImage);
        }

        void bind(int position){
            String title = albums.get(position).getTitle();
            String artist = albums.get(position).getArtist();

            this.title.setText(title);
            this.artist.setText(artist);

            if (albums.get(position).getAlbumArtPath() != null){
                Picasso.with(art.getContext())
                        .load(new File(albums.get(position).getAlbumArtPath()))
                        .error(R.drawable.ic_album_orange_96dp)
                        .resize(96,96)
                        .onlyScaleDown() //the image will only resize if greater than 96x96
                        .into(art);
            }else{
                art.setImageResource(R.drawable.ic_album_orange_96dp);
            }

        }

    }
}
