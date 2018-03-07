package com.example.rkswl.ezen_project.ViewPagerAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.rkswl.ezen_project.fragment.FragmentCoupleInfo;
import com.example.rkswl.ezen_project.fragment.FragmentJoin;
import com.example.rkswl.ezen_project.fragment.FragmentPassword;

/**
 * Created by rkswl on 2018-02-24.
 */

public class ViewPagerAdpter extends FragmentStatePagerAdapter {

    Context context = null;


    public ViewPagerAdpter(FragmentManager fm , Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new FragmentJoin();
        }else if(position == 1){
            return new FragmentCoupleInfo();
        }else{
            return new FragmentPassword();
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
}

