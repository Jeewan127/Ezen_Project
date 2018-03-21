package com.example.rkswl.ezen_project.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabWidget;

import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.ViewPagerAdapter.MainViewPagerAdapter;
import com.example.rkswl.ezen_project.fragment.FragmentPicture;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    MainViewPagerAdapter mainViewPagerAdapter;
    @BindView(R.id.main_viewpager)ViewPager viewpager;
    @BindView(R.id.main_view_story) View sto;
    @BindView(R.id.main_view_picture) View pic;
    @BindView(R.id.main_btn_story) Button btn_story;
    @BindView(R.id.main_btn_picture) Button btn_picture;


    public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Log.d("ksj","이건 실행이나될라나 : " + intent.getStringExtra("id"));
        id = intent.getStringExtra("id");
        mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),this);
        viewpager.setAdapter(mainViewPagerAdapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 0){
                    //스토리
                    mainViewPagerAdapter.notifyDataSetChanged();
                    sto.setVisibility(View.VISIBLE);
                    pic.setVisibility(View.GONE);
                    btn_story.setTextColor(Color.rgb(65,71,114));
                    btn_picture.setTextColor(Color.rgb(255,255,255));
                    //414772

                }else{


                    mainViewPagerAdapter.notifyDataSetChanged();
                    //사진
                    sto.setVisibility(View.GONE);
                    pic.setVisibility(View.VISIBLE);
                    btn_picture.setTextColor(Color.rgb(65,71,114));
                    btn_story.setTextColor(Color.rgb(255,255,255));

                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("ksj", "position1 : " + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //main_btn_story
//    main_btn_picture


    @Override
    protected void onResume() {
        super.onResume();
        mainViewPagerAdapter.notifyDataSetChanged();
        Log.d("ksj","처음한번밖에실행안되지않남..");
    }

    @OnClick(R.id.main_btn_story)
    public void story(){
        viewpager.setCurrentItem(0);
    }
    @OnClick(R.id.main_btn_picture)
    public void priture(){
        viewpager.setCurrentItem(1);
    }


}
