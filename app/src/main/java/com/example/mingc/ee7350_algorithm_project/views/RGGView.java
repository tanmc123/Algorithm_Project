package com.example.mingc.ee7350_algorithm_project.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingc.ee7350_algorithm_project.DisplayActivity;
import com.example.mingc.ee7350_algorithm_project.model.BackBone;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Square;

/**
 * Created by Mingc on 4/20/2017.
 */

public abstract class RGGView extends View {
    protected int drawSpeed = 50;
    protected int drawEdgeSpeed = 1;
    protected int curtPaintTime = 1;

    protected Paint RGGPaint;
    protected Paint pointPaint;
    protected Paint edgePaint;

    protected BackBone backBone;

    protected int drawFlag = DisplayActivity.DRAW_EMPTY_FLAG;

    public RGGView(Context context) {
        super(context);
        init();
    }

    public RGGView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public abstract void doDrawPoints(@NonNull RandomGeometricGraph RGG);

    public abstract void doDrawEdges(@NonNull RandomGeometricGraph RGG);

    public abstract void doDrawColoredPoint(@NonNull RandomGeometricGraph RGG);

    public void doDrawBipartitePoints(@NonNull BackBone backBone){
        this.drawFlag = PageFragment.DRAW_BIPARTITE_POINT;
        this.backBone = backBone;
        this.curtPaintTime = 1;
        invalidate();
    }

    public void doDrawBipartiteEdges(@NonNull BackBone backBone){
        this.drawFlag = PageFragment.DRAW_BIPARTITE_EDGES;
        this.backBone = backBone;
        this.curtPaintTime = 1;
        invalidate();
    }

    public void doDrawColorSet1(@NonNull BackBone backBone){
        this.drawFlag = PageFragment.DRAW_COLOR_SET_1;
        this.backBone = backBone;
        this.curtPaintTime = 1;
        invalidate();
    }
    public void doDrawColorSet2(@NonNull BackBone backBone){
        this.drawFlag = PageFragment.DRAW_COLOR_SET_2;
        this.backBone = backBone;
        this.curtPaintTime = 1;
        invalidate();
    }
    public void doDrawBackBone(@NonNull BackBone backBone){
        this.drawFlag = PageFragment.DRAW_BACKBONE;
        this.backBone = backBone;
        this.curtPaintTime = 1;
        invalidate();
    }
    public void doDrawBackBoneEdges(@NonNull BackBone backBone){
        this.drawFlag = PageFragment.DRAW_BACKBONE_EDGES;
        this.backBone = backBone;
        this.curtPaintTime = 1;
        invalidate();
    }

    public abstract void doDrawWalkThrough(RandomGeometricGraph RGG);


    public void setDrawSpeed(int drawSpeed) {
        this.drawSpeed = drawSpeed;
    }

    public void setDrawEdgeSpeed(int drawEdgeSpeed) {
        this.drawEdgeSpeed = drawEdgeSpeed;
    }

    private void init(){
        RGGPaint = new Paint();
        RGGPaint.setStrokeWidth(10);
        RGGPaint.setColor(Color.RED);
        RGGPaint.setStyle(Paint.Style.FILL);

        pointPaint = new Paint();
        pointPaint.setStrokeWidth(5);
        pointPaint.setColor(Color.WHITE);

        pointPaint.setStyle(Paint.Style.FILL);

        edgePaint = new Paint();
        edgePaint.setStrokeWidth(3);
        edgePaint.setColor(Color.WHITE);
        edgePaint.setStyle(Paint.Style.FILL);
    }
}
