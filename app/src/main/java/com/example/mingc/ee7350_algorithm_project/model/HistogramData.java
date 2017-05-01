package com.example.mingc.ee7350_algorithm_project.model;

import android.content.Intent;

import java.util.List;

/**
 * Created by Mingc on 4/23/2017.
 */

public class HistogramData {
    private List<Integer> xData;
    private List<Integer> yData;
    private int maxXlabel;
    private int maxYlabel;
    private String figureTitle;

    public HistogramData(List<Integer> xData, List<Integer> yData, int maxXlabel, int maxYlabel, String figureTitle) {
        this.xData = xData;
        this.yData = yData;
        this.maxXlabel = maxXlabel;
        this.maxYlabel = maxYlabel;
        this.figureTitle = figureTitle;
    }

    public List<Integer> getxData() {
        return xData;
    }

    public List<Integer> getyData() {
        return yData;
    }

    public String getFigureTitle() {
        return figureTitle;
    }

    public int getMaxXlabel() {
        return maxXlabel;
    }

    public int getMaxYlabel() {
        return maxYlabel;
    }
}
