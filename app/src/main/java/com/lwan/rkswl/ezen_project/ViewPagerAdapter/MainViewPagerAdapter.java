package com.lwan.rkswl.ezen_project.ViewPagerAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lwan.rkswl.ezen_project.fragment.FragmentPicture;
import com.lwan.rkswl.ezen_project.fragment.FragmentStory;

/**
 * Created by rkswl on 2018-03-08.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    Context context = null;


    public MainViewPagerAdapter(FragmentManager fm , Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new FragmentStory();
        }else{
            return new FragmentPicture();
        }
    }
    @Override
    public int getCount() {
        return 2;
    }




}
