package com.diaze.musicapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PlaylistAdapter {

    class PlaylistViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        public PlaylistViewHolder(View itemView) {
            super(itemView);

        }
    }
}
