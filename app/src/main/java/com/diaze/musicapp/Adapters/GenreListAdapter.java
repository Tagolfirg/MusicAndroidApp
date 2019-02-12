package com.diaze.musicapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diaze.musicapp.Attributes.Genre;
import com.diaze.musicapp.R;

import java.util.List;

public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.GenreListViewHolder>{

    private List<Genre> genreList;
    private GenreListItemClickListener mOnClickListener;

    public interface GenreListItemClickListener{
        void onListItemClick(int index);
    }

    public GenreListAdapter(List<Genre> genres, GenreListItemClickListener listener){
        this.genreList = genres;
        this.mOnClickListener = listener;
    }

    @NonNull
    @Override
    public GenreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.genre_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParent = false;

        View view = inflater.inflate(layoutIdForListItems,parent,shouldAttachToParent);
        GenreListViewHolder viewHolder = new GenreListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    class GenreListViewHolder extends RecyclerView.ViewHolder{
        TextView genre;

        public GenreListViewHolder(View itemView) {
            super(itemView);
            genre = (TextView)itemView.findViewById(R.id.genreTitle);
        }

        void bind(int position){
            genre.setText(genreList.get(position).getGenre());
        }

    }
}
