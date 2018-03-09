package com.example.rkswl.ezen_project.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rkswl.ezen_project.R;

/**
 * Created by rkswl on 2018-03-08.
 */

public class PlusImageViewHolder extends RecyclerView.ViewHolder {

    public static PlusImageViewHolder inflate(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new PlusImageViewHolder(view);
    }

    public TextView mTextTitle;

    public PlusImageViewHolder(View view) {
        super(view);
        mTextTitle = view.findViewById(R.id.title);
    }

    private void bind(String title) {
        mTextTitle.setText(title);
    }
}
