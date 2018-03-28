package com.lwan.rkswl.ezen_project.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lwan.rkswl.ezen_project.R;

/**
 * Created by rkswl on 2018-03-08.
 */

public class PictureImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView Stroyimg;
    public static PictureImageViewHolder inflate(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new PictureImageViewHolder(view);
    }



    public PictureImageViewHolder(View view) {
        super(view);
        Stroyimg = view.findViewById(R.id.plus_photos);
    }
}
