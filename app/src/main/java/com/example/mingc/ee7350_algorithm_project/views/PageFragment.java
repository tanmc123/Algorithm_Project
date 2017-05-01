package com.example.mingc.ee7350_algorithm_project.views;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mingc.ee7350_algorithm_project.R;
import com.example.mingc.ee7350_algorithm_project.model.BackBone;
import com.example.mingc.ee7350_algorithm_project.model.HistogramData;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.util.ReportUtil;

/**
 * Created by Mingc on 4/22/2017.
 */

public class PageFragment extends android.support.v4.app.Fragment {

    public static final String KEY_PAGE = "page";
    public static final int DRAW_BIPARTITE_POINT = 301;
    public static final int DRAW_BIPARTITE_EDGES = 302;
    public static final int DRAW_COLOR_SET_1     = 303;
    public static final int DRAW_COLOR_SET_2     = 304;
    public static final int DRAW_BACKBONE        = 305;
    public static final int DRAW_BACKBONE_EDGES  = 306;

    public static final int DRAW_HISTOGRAM       = 307;
    public static final int DRAW_LINECHART       = 308;

    private LinearLayout linearLayout;
    private static RandomGeometricGraph RGG;
    private static BackBone backBone;

    public static PageFragment newInstances(int page, RandomGeometricGraph myRGG){
        RGG = myRGG;
        backBone = ReportUtil.backBoneGenerator(RGG);
        Bundle args = new Bundle();
        args.putInt(KEY_PAGE, page);

        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_fragment, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.page_fragment_container);
        return view;
    }

    // TODO: finish all the four pages
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        int page = getArguments().getInt(KEY_PAGE);
        View pageView;
        RGGView rggView;
        FigureView figureView;
        switch(page){
            case 0:
                switch (backBone.getTopology()){
                    default:
                    case SQUARE:
                        pageView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_result_square, null);
                        rggView = (SqaureView) pageView.findViewById(R.id.sqaure_view);
                        break;
                    case CIRCLE:
                        pageView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_result_disk, null);
                        rggView = (DiskView) pageView.findViewById(R.id.disk_view);
                        break;
                    case SPHERE:
                        pageView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_result_sphere, null);
                        rggView = (SphereView) pageView.findViewById(R.id.sphere_view);
                        break;
                }
                final RGGView finalRGGView = rggView;
                setupPage1Button(finalRGGView, pageView);
                linearLayout.addView(pageView);
                break;
            case 1:
                pageView = getLayoutInflater(savedInstanceState).inflate(R.layout.figure_fragment, null);
                figureView = (FigureView) pageView.findViewById(R.id.my_figure_view);
                HistogramData histogramData = ReportUtil.getDegreeHistogramData(RGG);
                figureView.doDrawHistogram(histogramData);
                figureView.setBackgroundColor(Color.LTGRAY);
                linearLayout.addView(pageView);
                break;
            case 2:
                pageView = getLayoutInflater(savedInstanceState).inflate(R.layout.figure_fragment, null);
                figureView = (FigureView) pageView.findViewById(R.id.my_figure_view);
                RGG.reset();
                HistogramData[] datas = ReportUtil.getDegreeWhenColor(RGG);
                figureView.doDrawLineCharts(datas);
                figureView.setBackgroundColor(Color.WHITE);
                linearLayout.addView(pageView);
                break;
            case 3:
                pageView = getLayoutInflater(savedInstanceState).inflate(R.layout.figure_fragment, null);
                figureView = (FigureView) pageView.findViewById(R.id.my_figure_view);
                HistogramData histogramData2 = ReportUtil.getColorDistribution(RGG);
                figureView.doDrawHistogram(histogramData2);
                linearLayout.addView(pageView);
                figureView.setBackgroundColor(Color.LTGRAY);
                break;
        }
    }

    private void setupPage1Button(final RGGView rggView, View pageView){
        Button resultBipartiteBtn = (Button) pageView.findViewById(R.id.result_bipartitePoints);
        resultBipartiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rggView.doDrawBipartitePoints(backBone);
            }
        });

        Button resultBipartiteEdgeBtn = (Button) pageView.findViewById(R.id.result_bipartiteEdges);
        resultBipartiteEdgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rggView.doDrawBipartiteEdges(backBone);
            }
        });

        Button resultColorSet1Btn = (Button) pageView.findViewById(R.id.result_colorset1);
        resultColorSet1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rggView.doDrawColorSet1(backBone);
            }
        });

        Button resultColorSet2Btn = (Button) pageView.findViewById(R.id.result_colorset2);
        resultColorSet2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rggView.doDrawColorSet2(backBone);
            }
        });

        Button resultBackBoneBtn = (Button) pageView.findViewById(R.id.result_backbone);
        resultBackBoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rggView.doDrawBackBone(backBone);
            }
        });

        Button resultBackBoneEdgeBtn = (Button) pageView.findViewById(R.id.result_backbone_edge);
        resultBackBoneEdgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rggView.doDrawBackBoneEdges(backBone);
            }
        });
    }
}
