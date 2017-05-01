package com.example.mingc.ee7350_algorithm_project;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.mingc.ee7350_algorithm_project.model.BackBone;
import com.example.mingc.ee7350_algorithm_project.model.Disk;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Sphere;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;
import com.example.mingc.ee7350_algorithm_project.util.ColorUtil;
import com.example.mingc.ee7350_algorithm_project.util.MyPagerAdapter;
import com.example.mingc.ee7350_algorithm_project.util.ReportUtil;


/**
 * Created by Mingc on 4/22/2017.
 */

public class ResultActivity extends AppCompatActivity {
    RandomGeometricGraph RGG;
    //BackBone backBone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUI();
        setContentView(R.layout.activity_result);
        ViewPager viewPager = (ViewPager) findViewById(R.id.result_view_pager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), RGG));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.result_view_pager_tab);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setBackgroundResource(R.color.colorPrimary);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: // Click return button
                finish();
                return true;
            /*case R.id.ic_save:
                return true;*/
        }
        return false;
    }

    private void setupUI(){
        setTitle("Result Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        UserInput userInput = getIntent().getParcelableExtra(SettingActivity.KEY_USERINPUT);
        // Get Backbone
        RandomGeometricGraph RGG;
        switch (userInput.topology){
            case SQUARE:
                RGG = new Square(userInput);
                break;
            case CIRCLE:
                RGG = new Disk(userInput);
                break;
            case SPHERE:
                RGG = new Sphere(userInput);
                break;
            default:
                RGG = new Square();

        }
        this.RGG = RGG;
        //RGG.cellMethod();
        ColorUtil.color(RGG);
        //backBone = ReportUtil.backBoneGenerator(RGG);

    }


}
