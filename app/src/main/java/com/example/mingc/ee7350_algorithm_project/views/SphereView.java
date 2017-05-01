package com.example.mingc.ee7350_algorithm_project.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingc.ee7350_algorithm_project.DisplayActivity;
import com.example.mingc.ee7350_algorithm_project.model.BackBone;
import com.example.mingc.ee7350_algorithm_project.model.Disk;
import com.example.mingc.ee7350_algorithm_project.model.Point;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Sphere;
import com.example.mingc.ee7350_algorithm_project.util.NumberToColorUtil;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Mingc on 4/19/2017.
 */

public class SphereView extends RGGView {


    //private Disk disk;

    private Sphere sphere;
    private int diskDiameter;

    private int curtPaintTime = 1;


    public SphereView(Context context) {
        super(context);
    }

    public SphereView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        diskDiameter = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(diskDiameter, diskDiameter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw Disk Background
        float radius = (float) diskDiameter / 2;
        canvas.drawCircle(radius, radius, radius, RGGPaint);
        int index = 0;
        int size = 0;
        if(sphere != null){
            index = 0;
            size = sphere.getNumberOfPoints();
            switch (drawFlag) {
                case DisplayActivity.DRAW_COLORED_POINT_FLAG:
                    for (Point point : sphere.getPoints()) {
                        int color = NumberToColorUtil.numberToColor(point.getColor());
                        pointPaint.setColor(color);
                        drawPoint(canvas, point, pointPaint);
                        index++;
                        if (index > size / drawSpeed * curtPaintTime) {
                            curtPaintTime++;
                            invalidate();
                            break;
                        }
                    }
                    break;

                case DisplayActivity.DRAW_EDGES_FLAG:
                    size = sphere.getEdges().size();

                    int modulos = 0;
                    pointPaint.setColor(Color.RED);
                    for (Set<Point> set : sphere.getEdges()) {
                        if(modulos % 5 != 0){
                            modulos ++;
                            continue;
                        }
                        Iterator<Point> it = set.iterator();
                        Point startPoint = it.next();
                        Point endPoint = it.next();

                        drawPoint(canvas, startPoint, pointPaint);
                        drawPoint(canvas, endPoint, pointPaint);
                        drawEdges(canvas, startPoint, endPoint, edgePaint);
                        modulos ++;
                    }

                    break;

                case DisplayActivity.DRAW_POINT_FLAG:
                    for (Point point : sphere.getPoints()) {
                        //pointPaint.setColor(Color.GREEN);
                        drawPoint(canvas, point, pointPaint);
                        index++;
                        if (index > size / drawSpeed * curtPaintTime) {
                            curtPaintTime++;
                            invalidate();
                            break;
                        }
                    }
                    break;
            }
        }

        if(backBone != null) {
            switch (drawFlag) {
                case PageFragment.DRAW_BIPARTITE_POINT:

                    for (Point point : backBone.getBipartitePoints()) {
                        int color = NumberToColorUtil.numberToColor(point.getColor());
                        pointPaint.setColor(color);
                        drawPoint(canvas, point, pointPaint);

                    }
                    break;

                case PageFragment.DRAW_BIPARTITE_EDGES:

                    for (Set<Point> set : backBone.getBipartiteEdges()) {
                        Iterator<Point> it = set.iterator();
                        Point startPoint = it.next();
                        Point endPoint = it.next();
                        drawEdges(canvas, startPoint, endPoint, edgePaint);
                    }
                    break;

                case PageFragment.DRAW_COLOR_SET_1:

                    for (Point point : backBone.getColorSet1()) {
                        int color = NumberToColorUtil.numberToColor(point.getColor());
                        pointPaint.setColor(color);
                        drawPoint(canvas, point, pointPaint);
                    }
                    break;

                case PageFragment.DRAW_COLOR_SET_2:

                    for (Point point : backBone.getColorSet2()) {
                        int color = NumberToColorUtil.numberToColor(point.getColor());
                        pointPaint.setColor(color);
                        drawPoint(canvas, point, pointPaint);
                    }
                    break;

                case PageFragment.DRAW_BACKBONE:

                    for (Point point : backBone.getBackbone()) {
                        int color = NumberToColorUtil.numberToColor(point.getColor());
                        pointPaint.setColor(color);
                        drawPoint(canvas, point, pointPaint);

                    }
                    break;
                case PageFragment.DRAW_BACKBONE_EDGES:

                    for (Set<Point> set : backBone.getBackBoneEdges()) {
                        Iterator<Point> it = set.iterator();
                        Point startPoint = it.next();
                        Point endPoint = it.next();
                        drawEdges(canvas, startPoint, endPoint, edgePaint);

                    }
                    break;
            }

        }
    }


    private void drawEdges(Canvas canvas, Point startPoint, Point endPoint, Paint edgePaint){
        canvas.drawLine((float) startPoint.x * diskDiameter,
                (float) startPoint.y * diskDiameter,
                (float) endPoint.x * diskDiameter,
                (float) endPoint.y * diskDiameter,
                edgePaint
        );
    }


    private void drawPoint(Canvas canvas, Point point, Paint pointPaint){
        float x = (float) point.x;
        float y = (float) point.y;
        x *= diskDiameter;
        y *= diskDiameter;
        canvas.drawPoint(x, y, pointPaint);
    }





    @Override
    public void doDrawPoints(@NonNull RandomGeometricGraph RGG) {
        this.sphere = (Sphere) RGG;
        this.drawFlag = DisplayActivity.DRAW_POINT_FLAG;
        this.curtPaintTime = 1;
        invalidate();
    }

    @Override
    public void doDrawEdges(@NonNull RandomGeometricGraph RGG) {
        this.sphere = (Sphere) RGG;
        this.drawFlag = DisplayActivity.DRAW_EDGES_FLAG;
        this.curtPaintTime = 1;
        invalidate();
    }

    @Override
    public void doDrawColoredPoint(@NonNull RandomGeometricGraph RGG) {
        this.sphere = (Sphere) RGG;
        this.drawFlag = DisplayActivity.DRAW_COLORED_POINT_FLAG;
        this.curtPaintTime = 1;
        invalidate();
    }

    @Override
    public void doDrawWalkThrough(RandomGeometricGraph RGG) {

    }
}
