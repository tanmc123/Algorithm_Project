package com.example.mingc.ee7350_algorithm_project;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mingc.ee7350_algorithm_project.model.Point;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.util.ColorUtil;
import com.example.mingc.ee7350_algorithm_project.util.ReportUtil;
import com.example.mingc.ee7350_algorithm_project.views.RGGView;
import com.example.mingc.ee7350_algorithm_project.views.SqaureView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Mingc on 4/30/2017.
 */

public class WalkThroughActivity extends AppCompatActivity {
    public static final int DRAW_NEXT_STEP = 401;
    RandomGeometricGraph RGG;
    Stack<List<Point>> toColorStack;
    private int step = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Walk Through");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupUI();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: // Click return button
                finish();
                return true;
        }
        return false;
    }

    private void setupUI(){
        RGG = new Square();
        final Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();

        setContentView(R.layout.avtivity_walk_through);
        final RGGView rggView = (SqaureView) findViewById(R.id.sqaure_view);
        rggView.setBackgroundColor(Color.LTGRAY);
        //rggView.doDrawPoints(RGG);

        final TextView vertexTextView = (TextView) findViewById(R.id.num_of_vertices);
        final TextView edgesTextView = (TextView) findViewById(R.id.num_of_edges);
        final TextView minDegreeTextView = (TextView) findViewById(R.id.num_of_minDegree);
        final TextView maxDegreeTextView = (TextView) findViewById(R.id.num_of_maxDegree);
        final TextView DegreeWhenDeleteTextView = (TextView) findViewById(R.id.degree_when_delete_textview);
        final TextView orignalDegreeTextView = (TextView) findViewById(R.id.original_degree);

        vertexTextView.setText("" + RGG.getNumberOfPoints());
        edgesTextView.setText("" + RGG.getEdges().size());
        minDegreeTextView.setText("" + ReportUtil.getMinDegree(RGG));
        maxDegreeTextView.setText("" + ReportUtil.getMaxDegree(RGG));

        toColorStack = prepareSquare();

        Button nextStepBtn = (Button) findViewById(R.id.next_step_btn);
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step ++;
                List<Point> list = toColorStack.pop();
                Point point = list.get(0);
                int color = ColorUtil.assignColor(list);
                point.setColor(color);
                rggView.doDrawWalkThrough(RGG);

                String string = "" + (list.size() - 1);
                DegreeWhenDeleteTextView.setText(string);

                string = "" + adjacentList.get(point).size();
                orignalDegreeTextView.setText(string);
                if(step == 20){
                    step = 0;
                    RGG = new Square();
                    toColorStack = prepareSquare();
                }

            }
        });

        Button resetBtn = (Button) findViewById(R.id.reset_btn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step = 0;
                RGG = new Square();
                toColorStack = prepareSquare();
            }
        });

    }

    private Stack<List<Point>> prepareSquare(){
        // prepare to color

        Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();
        Map<Integer, List<Point>> degreeList;
        degreeList = new HashMap<>();
        ColorUtil.generateDegreeList(adjacentList, degreeList);
        Stack<List<Point>> toColorStack = new Stack<>();
        Map<Point, List<Point>> leftAdjacentList = ColorUtil.copyList(adjacentList);
        ColorUtil.orderColor(toColorStack, degreeList, leftAdjacentList);

        return toColorStack;

    }


}
