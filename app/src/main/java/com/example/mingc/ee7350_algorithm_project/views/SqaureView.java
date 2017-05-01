package com.example.mingc.ee7350_algorithm_project.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.example.mingc.ee7350_algorithm_project.DisplayActivity;
import com.example.mingc.ee7350_algorithm_project.R;
import com.example.mingc.ee7350_algorithm_project.ResultActivity;
import com.example.mingc.ee7350_algorithm_project.WalkThroughActivity;
import com.example.mingc.ee7350_algorithm_project.model.BackBone;
import com.example.mingc.ee7350_algorithm_project.model.Point;

import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;
import com.example.mingc.ee7350_algorithm_project.util.NumberToColorUtil;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import static com.example.mingc.ee7350_algorithm_project.R.string.points;

/**
 * Created by Mingc on 3/27/2017.
 */

public class SqaureView extends RGGView {
    // The larger number means smaller speed

    //private Canvas canvas;



    private Square square; // The square going to be drawn
    private Rect rect;

    private int squareWidth;
    private int squareHeight;
    private boolean drawMovingFlag = true;

    public float pixelRatio = getResources().getDisplayMetrics().density;

    public SqaureView(Context context){
        super(context);
        rect = rectInit();
    }

    public SqaureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        rect = rectInit();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(rect, RGGPaint);
        if(square != null) {
            // Moving Diagram
            if(drawMovingFlag){
                int index = 0;
                int size = square.getNumberOfPoints();
                switch (drawFlag) {
                    case DisplayActivity.DRAW_COLORED_POINT_FLAG:
                        for (Point point : square.getPoints()) {
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
                    case DisplayActivity.DRAW_POINT_FLAG:
                        for (Point point : square.getPoints()) {
                            pointPaint.setColor(Color.WHITE);
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
                        size = square.getEdges().size();
                        for(Set<Point> set : square.getEdges()){
                            Iterator<Point> it = set.iterator();
                            Point startPoint = it.next();
                            Point endPoint = it.next();
                            drawEdges(canvas, startPoint, endPoint, edgePaint);
                            index ++;
                            if (index > (size / (drawEdgeSpeed)) * curtPaintTime) {
                                curtPaintTime++;
                                invalidate();
                                break;
                            }
                        }
                        break;
                    case WalkThroughActivity.DRAW_NEXT_STEP:
                        for(Set<Point> set : square.getEdges()) {
                            Iterator<Point> it = set.iterator();
                            Point startPoint = it.next();
                            Point endPoint = it.next();
                            int color = NumberToColorUtil.numberToColor(startPoint.getColor() % 2);
                            if(startPoint.getColor() == 0){
                                color = Color.RED;
                            }
                            edgePaint.setColor(color);
                            drawEdges(canvas, startPoint, endPoint, edgePaint);
                        }
                        for (Point point : square.getPoints()) {
                            int color = NumberToColorUtil.numberToColor(point.getColor() % 2);
                            if(point.getColor() == 0){
                                color = Color.RED;
                            }
                            pointPaint.setColor(color);
                            drawPoint(canvas, point, pointPaint);
                            index++;
                        }

                }
            }else {
                switch (drawFlag) {
                    case DisplayActivity.DRAW_POINT_FLAG:
                        for (Point point : square.getPoints()) {
                            drawPoint(canvas, point, pointPaint);
                        }
                        break;
                    case DisplayActivity.DRAW_EDGES_FLAG:
                        drawEdges(canvas, square.getAdjacentList(), edgePaint);
                        break;
                }
            }
        }

        if(backBone != null){

            switch (drawFlag){
                case PageFragment.DRAW_BIPARTITE_POINT:

                    for(Point point : backBone.getBipartitePoints()){
                        int color = NumberToColorUtil.numberToColor(point.getColor());
                        pointPaint.setColor(color);
                        drawPoint(canvas, point, pointPaint);

                    }
                    break;

                case PageFragment.DRAW_BIPARTITE_EDGES:

                    for(Set<Point> set : backBone.getBipartiteEdges()){
                        Iterator<Point> it = set.iterator();
                        Point startPoint = it.next();
                        Point endPoint = it.next();
                        drawEdges(canvas, startPoint, endPoint, edgePaint);
                    }
                    break;

                case PageFragment.DRAW_COLOR_SET_1:

                    for(Point point : backBone.getColorSet1()){
                        int color = NumberToColorUtil.numberToColor(point.getColor());
                        pointPaint.setColor(color);
                        drawPoint(canvas, point, pointPaint);
                    }
                    break;

                case PageFragment.DRAW_COLOR_SET_2:

                    for(Point point : backBone.getColorSet2()){
                        int color = NumberToColorUtil.numberToColor(point.getColor());
                        pointPaint.setColor(color);
                        drawPoint(canvas, point, pointPaint);
                    }
                    break;

                case PageFragment.DRAW_BACKBONE:

                    for(Point point : backBone.getBackbone()){
                        int color = NumberToColorUtil.numberToColor(point.getColor());
                        pointPaint.setColor(color);
                        drawPoint(canvas, point, pointPaint);

                    }
                    break;
                case PageFragment.DRAW_BACKBONE_EDGES:

                    for(Set<Point> set : backBone.getBackBoneEdges()){
                        Iterator<Point> it = set.iterator();
                        Point startPoint = it.next();
                        Point endPoint = it.next();
                        drawEdges(canvas, startPoint, endPoint, edgePaint);

                    }
                    break;
            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        squareWidth = MeasureSpec.getSize(widthMeasureSpec);
        squareHeight = squareWidth;

        setMeasuredDimension(squareWidth, squareHeight);

        /*
        * Test which mode is default and the result is EXACTLY is the default
        * setting
        *
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        switch (mode){
            case MeasureSpec.UNSPECIFIED:
                Toast.makeText(getContext(),"width UNSPECIFIED", Toast.LENGTH_LONG).show();
                break;
            case MeasureSpec.AT_MOST:
                Toast.makeText(getContext(),"width AT_MOST", Toast.LENGTH_LONG).show();
                break;
            case MeasureSpec.EXACTLY:
                Toast.makeText(getContext(),"width EXACTLY", Toast.LENGTH_LONG).show();
                break;
        }*/


        //setMeasuredDimension(widthMeasureSpec - 20, heightMeasureSpec - 100);
    }



    private void drawPoint(Canvas canvas, Point point, Paint pointPaint){
        float x = (float) point.x;
        float y = (float) point.y;
        x *= squareWidth;
        y *= squareHeight;
        canvas.drawPoint(x, y, pointPaint);
    }

    private void drawEdges(Canvas canvas, Point startPoint, Point endPoint, Paint edgePaint){
        canvas.drawLine((float) startPoint.x * squareWidth,
                (float) startPoint.y * squareHeight,
                (float) endPoint.x * squareWidth,
                (float) endPoint.y * squareHeight,
                edgePaint
        );
    }

    private void drawEdges(Canvas canvas, Map<Point, List<Point>> adjacentList, Paint edgePaint) {
        for(Point startPoint : adjacentList.keySet()){
            List<Point> neighbors = adjacentList.get(startPoint);
            for(Point endPoint : neighbors){
                canvas.drawLine((float) startPoint.x * squareWidth,
                        (float) startPoint.y * squareHeight,
                        (float) endPoint.x * squareWidth,
                        (float) endPoint.y * squareHeight,
                        edgePaint
                );
            }
        }
//        Toast.makeText(getContext(),"Drawing Lines", Toast.LENGTH_LONG).show();
    }





    @Contract(" -> !null")
    private Rect rectInit(){
        int[] rectDimension = {0, 0, squareWidth, squareHeight};
        /*for(int i = 0; i < 4; i ++){
            rectDimension[i] *= pixelRatio;
        }*/
        return new Rect(rectDimension[0], rectDimension[1], rectDimension[2], rectDimension[3]);
    }

    @Override
    public void doDrawPoints(@NonNull RandomGeometricGraph RGG) {
        this.square = (Square) RGG;
        this.drawFlag = DisplayActivity.DRAW_POINT_FLAG;
        this.curtPaintTime = 1;
        invalidate();
    }

    // Draw edges
    @Override
    public void doDrawEdges(@NonNull RandomGeometricGraph RGG) {
        this.drawFlag = DisplayActivity.DRAW_EDGES_FLAG;
        this.square = (Square) RGG;
        this.curtPaintTime = 1;
        invalidate();
    }

    // Draw colored points
    @Override
    public void doDrawColoredPoint(@NonNull RandomGeometricGraph RGG) {
        this.drawFlag = DisplayActivity.DRAW_COLORED_POINT_FLAG;
        this.square = (Square) RGG;
        this.curtPaintTime = 1;
        invalidate();
    }

    @Override
    public void doDrawWalkThrough(RandomGeometricGraph RGG){
        this.square = (Square) RGG;
        this.drawFlag = WalkThroughActivity.DRAW_NEXT_STEP;
        invalidate();
    }
}
