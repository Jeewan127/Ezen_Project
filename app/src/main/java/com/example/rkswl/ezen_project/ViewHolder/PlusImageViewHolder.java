package com.example.rkswl.ezen_project.ViewHolder;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rkswl.ezen_project.R;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by rkswl on 2018-03-08.
 */
@Data
@ToString
public class PlusImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView plus_photos;

//    public static PlusImageViewHolder inflate(ViewGroup parent) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_plus_to, parent, false);
//        return new PlusImageViewHolder(view);
//    }

    public PlusImageViewHolder(View view) {
                super(view);
                plus_photos = view.findViewById(R.id.plus_photos);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

            }
        });
    }


}
