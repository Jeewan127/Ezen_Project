package com.example.rkswl.ezen_project;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;

/**
 * Created by rkswl on 2018-03-08.
 */

public class HackyDrawerLayout extends DrawerLayout {

    public HackyDrawerLayout(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
