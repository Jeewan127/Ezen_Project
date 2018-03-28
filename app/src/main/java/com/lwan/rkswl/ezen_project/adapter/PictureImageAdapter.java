package com.lwan.rkswl.ezen_project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lwan.rkswl.ezen_project.ViewHolder.PictureImageViewHolder;

/**
 * Created by rkswl on 2018-03-08.
 */

public class PictureImageAdapter extends RecyclerView.Adapter<PictureImageViewHolder> {

    Listener mListener;

    public PictureImageAdapter(Listener listener) {
        mListener = listener;
    }

    @Override
    public PictureImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PictureImageViewHolder holder = PictureImageViewHolder.inflate(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onImageClicked(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PictureImageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public interface Listener {
        void onImageClicked(View view);
    }
}