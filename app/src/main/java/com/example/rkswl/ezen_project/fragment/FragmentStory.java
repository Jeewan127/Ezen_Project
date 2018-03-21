package com.example.rkswl.ezen_project.fragment;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rkswl.ezen_project.DbManger;
import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.StroyImageFIle;
import com.example.rkswl.ezen_project.activity.MainActivity;
import com.example.rkswl.ezen_project.activity.PlusActivity;
import com.example.rkswl.ezen_project.adapter.PlusImageAdapter;
import com.example.rkswl.ezen_project.adapter.StoryImageAdapter;
import com.example.rkswl.ezen_project.model.user;
import com.example.rkswl.ezen_project.retrofit.RetrofitService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentStory extends Fragment {

    @BindView(R.id.story_recyclerview) RecyclerView list;
    StoryImageAdapter storyImageAdapter;
    DbManger dbManger;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        ButterKnife.bind(this, view);
        dbManger = new DbManger(getContext(),"addwin_user.db",null,1);







//        list.setLayoutManager(new GridLayoutManager(getContext(),1));
//        storyImageAdapter = new StoryImageAdapter(item);
//        list.setAdapter(storyImageAdapter);
//        storyImageAdapter.notifyDataSetChanged();

//
//        plusImageAdapter = new PlusImageAdapter(new PlusImageAdapter.Listener() {
//            @Override
//            public void onImageClicked(View view) {
//                transition(view);  // 클릭시 코드
//            }
//        });
//
        return view;
    }





    @OnClick(R.id.story_btn_plus)
    public void plus(){

        Intent intent = new Intent(getContext(), PlusActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Call<ArrayList<StroyImageFIle>> items = RetrofitService.getInstance().getRetrofitRequest().get_story(dbManger.get_id());
        items.enqueue(new Callback<ArrayList<StroyImageFIle>>() {
            @Override
            public void onResponse(Call<ArrayList<StroyImageFIle>> call, Response<ArrayList<StroyImageFIle>> response) {
                if(response.isSuccessful()){
                    ArrayList<StroyImageFIle> item = response.body();
                    Log.d("ksj","가져온 item 들 : " +item.size());
                    String title = "초기";
                    for(int a = 0 ; a < item.size() ; a++){
                        if(title.equals(item.get(a).getTitle())){
                            item.remove(a);
                            a = a -1;
                        }else{
                            title = item.get(a).getTitle();
                        }
                    }

                    list.setLayoutManager(new GridLayoutManager(getContext(),1));
                    storyImageAdapter = new StoryImageAdapter(item , getActivity());
                    list.setAdapter(storyImageAdapter);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<StroyImageFIle>> call, Throwable t) {

            }
        });

    }
}
