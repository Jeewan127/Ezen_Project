package com.example.rkswl.ezen_project.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.rkswl.ezen_project.R;
import com.example.rkswl.ezen_project.ViewPagerAdapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;

public class FragmentJoinViewPager extends AppCompatActivity {

    //join_1 에서 가져오는 함수들
    String server_id = null;
    String server_pass = null;
    String server_name =null;
    String server_date = null;
    String server_gender = null;
    String server_number = null;
    MultipartBody.Part image_file = null; //이파일명으로 추가하기!

    //join_2 에서 가져오는 함수들 (cp_info)
    String server_First_day = null;
    String server_op_number = null;

    //join_3 에서 가져오는 함수들(password)
    String server_Lock_password = null;


    ViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.join_viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_join_view_pager);
        ButterKnife.bind(this);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),this);
        viewpager.setAdapter(viewPagerAdapter);
        viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(false);
                return true;
            }
        });

    }

    public void one_move(){
        viewpager.setCurrentItem(0);
    }

    public void two_move(){
        viewpager.setCurrentItem(1);
    }

    public void three_move(){
        viewpager.setCurrentItem(2);
    }
}
