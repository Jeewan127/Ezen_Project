package com.example.rkswl.ezen_project.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.rkswl.ezen_project.ViewHolder.PlusImageViewHolder;

/**
 * Created by rkswl on 2018-03-08.
 */

public class PlusImageAdapter extends RecyclerView.Adapter<PlusImageViewHolder> {

    Listener mListener;

    public PlusImageAdapter(Listener listener) {
        mListener = listener;
    }

    @Override
    public PlusImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PlusImageViewHolder holder = PlusImageViewHolder.inflate(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onImageClicked(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(PlusImageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public interface Listener {
        void onImageClicked(View view);
    }
}
