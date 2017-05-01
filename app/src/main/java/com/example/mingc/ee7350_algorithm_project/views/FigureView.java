package com.example.mingc.ee7350_algorithm_project.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.mingc.ee7350_algorithm_project.DisplayActivity;
import com.example.mingc.ee7350_algorithm_project.model.HistogramData;
import com.example.mingc.ee7350_algorithm_project.util.ColorUtil;
import com.example.mingc.ee7350_algorithm_project.util.NumberToColorUtil;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mingc on 4/23/2017.
 */

public class FigureView extends View {

    private int drawFlag = DisplayActivity.DRAW_EMPTY_FLAG;
    private int viewWidth;
    private int viewHeight;
    private HistogramData histogramData;
    private HistogramData[] histogramDatas;

    int numOfXTitle = 15;
    int numOfYTitle = 20;

    int topMargin = 10;
    int leftMargin = 100;
    int bottomMargin = 100;
    int rightMargin = 10;


    public FigureView(Context context) {
        super(context);
    }

    public FigureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (drawFlag){
            case PageFragment.DRAW_HISTOGRAM:
                drawHistogram(canvas, histogramData);
                break;
            case PageFragment.DRAW_LINECHART:
                drawLineCharts(canvas, histogramDatas);
                break;
        }
    }

    private void drawLineCharts(Canvas canvas, HistogramData[] histogramDatas){
        drawAxisLine(canvas, histogramDatas[0]);
        drawLegend(canvas);
        for(int i = 0; i < histogramDatas.length; i ++){
            drawLineChart(canvas, histogramDatas[i]);
        }
    }

    private void drawLineChart(Canvas canvas, HistogramData histogramData){


        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(3);
        switch (histogramData.getFigureTitle()){
            case "Degree When Delete" :
                linePaint.setColor(Color.BLUE);
                break;
            case "Original Degree" :
                linePaint.setColor(Color.DKGRAY);
                break;
            case "Average Degree" :
                linePaint.setColor(Color.RED);
                break;
            default:
                linePaint.setColor(Color.WHITE);
        }

        List<Integer> datas = histogramData.getyData();
        int dataSize = datas.size();
        int chartHeight = viewHeight - topMargin - bottomMargin;
        int chartWidth = viewWidth - leftMargin - rightMargin;

/*        List<Integer> drawData = histogramData.getyData();

        if(dataSize > chartWidth){
            int dataDelta = dataSize / chartWidth + 1;
            drawData = new ArrayList<>();
            int i = 0;
            while(i < histogramData.getyData().size()){
                drawData.add(histogramData.getyData().get(i));
                i += dataDelta;
            }
        }*/
        float lineWidth = (float) chartWidth / dataSize;
        float startX = leftMargin;
        float startY = viewHeight - bottomMargin -
                ((float) datas.get(0) / histogramData.getMaxYlabel()) * chartHeight;
        for(int i = 1; i < dataSize; i ++){
            float endX = startX + lineWidth;
            float endY = viewHeight - bottomMargin -
                    ((float) datas.get(i) / histogramData.getMaxYlabel()) * chartHeight;
            canvas.drawLine(startX, startY, endX, endY, linePaint);
            startX = endX;
            startY = endY;
        }

    }

    private void drawHistogram(Canvas canvas, HistogramData histogramData){
        drawAxisLine(canvas, histogramData);

        Paint rectPaint = new Paint();
        rectPaint.setStrokeWidth(3);
        // Draw Rect
        int dataSize = histogramData.getxData().size();
        int rectWidth = (viewWidth - leftMargin - rightMargin) / dataSize;
        int totalHeight = viewHeight - topMargin - bottomMargin;
        //rectPaint.setStrokeWidth(rectWidth);
        for(int i = 0; i < dataSize; i ++){
            Rect rect = new Rect();
            int rectHeight = (int)((double) histogramData.getyData().get(i) / histogramData.getMaxYlabel() * totalHeight);
            rect.left = leftMargin + i * rectWidth;
            rect.top = viewHeight - bottomMargin - rectHeight;
            rect.right = rect.left + rectWidth;
            rect.bottom = viewHeight - bottomMargin;

            rectPaint.setColor(NumberToColorUtil.numberToColor(i));
            canvas.drawRect(rect, rectPaint);
        }
    }

    private void drawLegend(Canvas canvas){
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(6);
        linePaint.setColor(Color.DKGRAY);

        Paint titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTextSize(30);

        canvas.drawLine(viewWidth - 400, 50, viewWidth - 300, 50, linePaint);
        canvas.drawText("Degree when delete", viewWidth - 280, 50, titlePaint);

        linePaint.setColor(Color.RED);
        canvas.drawLine(viewWidth - 400, 100, viewWidth - 300, 100, linePaint);
        canvas.drawText("Average Degree", viewWidth - 280, 100, titlePaint);

        linePaint.setColor(Color.BLUE);
        canvas.drawLine(viewWidth - 400, 150, viewWidth - 300, 150, linePaint);
        canvas.drawText("Original delete", viewWidth - 280, 150, titlePaint);

    }

    private void drawAxisLine(Canvas canvas, HistogramData histogramData){
        Paint axisLinePaint = new Paint();
        axisLinePaint.setStrokeWidth(5);
        axisLinePaint.setColor(Color.BLACK);

        Paint titlePaint = new Paint();
        titlePaint.setStrokeWidth(50);
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextAlign(Paint.Align.RIGHT);
        titlePaint.setTextSize(50);

        String[] yTitleStrings = getLabelTitle(histogramData.getMaxYlabel(), numOfYTitle);
        String[] xTitleStrings = getLabelTitle(histogramData.getMaxXlabel(), numOfXTitle);

        // Draw axis
        canvas.drawLine(leftMargin, topMargin, leftMargin, viewHeight - bottomMargin, axisLinePaint);// Y axis
        canvas.drawLine(leftMargin, viewHeight - bottomMargin, viewWidth-rightMargin , viewHeight - bottomMargin, axisLinePaint);// X axis

        int xDelta = (viewWidth - (leftMargin + rightMargin)) / numOfXTitle;
        int yDelta = (viewHeight - (topMargin + bottomMargin)) / numOfYTitle;

        // Draw X text
        for(int i = 0; i < numOfXTitle; i ++){
            Path path = new Path();
            path.moveTo(leftMargin + i * xDelta, viewHeight - bottomMargin + 10);
            path.lineTo(leftMargin + i * xDelta, viewHeight - bottomMargin + 50);
            //canvas.drawText(xTitleStrings[i], leftMargin + i * xDelta, viewHeight - (bottomMargin / 2), titlePaint);
            char[] chars = xTitleStrings[i].toCharArray();
            canvas.drawTextOnPath(chars, 0, chars.length, path, 0, 0, titlePaint);
            //canvas.drawTextOnPath(xTitleStrings[i], path, 0, 0, titlePaint);
        }
        for(int i = 1; i < numOfYTitle; i ++){
            canvas.drawText(yTitleStrings[i], leftMargin, (viewHeight - bottomMargin) - i * yDelta, titlePaint);
        }

    }

    @Contract(pure = true)
    private String[] getLabelTitle(int maxData, int numOfTitle){
        String[] results = new String[numOfTitle];
        int delta = maxData / numOfTitle;
        for(int i = 0; i < numOfTitle; i ++){
            results[i] = "" + (i * delta);
        }
        return results;
    }



    public void doDrawHistogram(HistogramData histogramData){
        this.histogramData = histogramData;
        this.drawFlag = PageFragment.DRAW_HISTOGRAM;
        invalidate();
    }

    public void doDrawLineCharts(HistogramData[] histogramDatas){
        this.histogramDatas = histogramDatas;
        this.drawFlag = PageFragment.DRAW_LINECHART;
        invalidate();
    }
}
