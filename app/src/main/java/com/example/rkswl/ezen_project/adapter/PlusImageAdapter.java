package com.example.rkswl.ezen_project.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.ViewHolder.PlusImageViewHolder;
import com.example.rkswl.ezen_project.plusImageFIle;

import java.util.ArrayList;

/**
 * Created by rkswl on 2018-03-08.
 */

public class PlusImageAdapter extends RecyclerView.Adapter<PlusImageViewHolder> {

    Listener mListener;
    ArrayList<plusImageFIle> plusImageFIles = new ArrayList<>();

    public void add(plusImageFIle plusImageFIle) {
        Log.d("add", "add 실행");
        plusImageFIles.add(plusImageFIle);
        notifyDataSetChanged();
    }

    // 생성자
    public PlusImageAdapter(Listener listener) {
        mListener = listener;
    }

    // 생성자
    public PlusImageAdapter(ArrayList<plusImageFIle> plusImageFIles) {
        this.plusImageFIles = plusImageFIles;
    }

    // 뷰홀더를 어떻게 생성할 것인지 선언
    // 뷰홀더 객체를 생성해서 return 해줌
    @Override
    public PlusImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        PlusImageViewHolder holder = new PlusImageViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onImageClicked(view);
            }
        });
        return new PlusImageViewHolder(view);
    }

    // listview getview를 대체
    // 넘겨 받는 데이터를 화면에 출력하는 역
    // 뷰홀더와 데이터가 결합할 때 각 줄의 데이터는 주어진 position으로 얻어온다
    @Override
    public void onBindViewHolder(PlusImageViewHolder holder, int position) {
        plusImageFIle item = plusImageFIles.get(position);
        holder.plus_photos.setTag(holder);
        holder.plus_photos.setImageURI(plusImageFIles.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return plusImageFIles.size();
    }

    public interface Listener {
        void onImageClicked(View view);
    }
}
