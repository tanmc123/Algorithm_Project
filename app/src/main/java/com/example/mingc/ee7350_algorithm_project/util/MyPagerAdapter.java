package com.example.mingc.ee7350_algorithm_project.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mingc.ee7350_algorithm_project.model.BackBone;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.views.PageFragment;

/**
 * Created by Mingc on 4/22/2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    RandomGeometricGraph RGG;
    public MyPagerAdapter(FragmentManager fm, RandomGeometricGraph RGG) {
        super(fm);
        this.RGG = RGG;

    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstances(position, RGG);
    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "BackBone Detail";
            case 1:
                return "Degree Histogram";
            case 2:
                return "Sequential Diagram";
            case 3:
                return "Color Distribution";
        }
        return "";
    }
}


