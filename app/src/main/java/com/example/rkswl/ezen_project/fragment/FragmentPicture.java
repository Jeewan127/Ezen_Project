package com.example.rkswl.ezen_project.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rkswl.ezen_project.DbManger;
import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.StroyImageFIle;
import com.example.rkswl.ezen_project.adapter.PictureAdapter;
import com.example.rkswl.ezen_project.adapter.PlusImageAdapter;
import com.example.rkswl.ezen_project.retrofit.RetrofitService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPicture extends Fragment {

    @BindView(R.id.story_picture_recyclerview) RecyclerView list;

    PictureAdapter pictureAdapter;
    ArrayList<StroyImageFIle> item;

    DbManger dbManger;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        ButterKnife.bind(this, view);






        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbManger = new DbManger(getContext(),"addwin_user.db",null,1);
        Call<ArrayList<StroyImageFIle>> items  = RetrofitService.getInstance().getRetrofitRequest().get_story(dbManger.get_id());
        items.enqueue(new Callback<ArrayList<StroyImageFIle>>() {
            @Override
            public void onResponse(Call<ArrayList<StroyImageFIle>> call, Response<ArrayList<StroyImageFIle>> response) {
                if(response.isSuccessful()){
                    item = response.body();
                    if(item.size() != 0) {
                        Log.d("ksj", "아이템에 사이즈 : " + item.size());
                        Log.d("ksj", "테스트용 img : " + item.get(0).getFile_path());
                        list.setLayoutManager(new GridLayoutManager(getContext(), 4));
                        pictureAdapter = new PictureAdapter(item, getContext());
                        list.setAdapter(pictureAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StroyImageFIle>> call, Throwable t) {

            }
        });
    }

    public void remove(){
        Log.d("ksj","새로고침 예고시");
    }
}
