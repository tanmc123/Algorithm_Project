package com.example.mingc.ee7350_algorithm_project.model;

import android.support.annotation.NonNull;

import com.example.mingc.ee7350_algorithm_project.util.ColorUtil;
import com.example.mingc.ee7350_algorithm_project.util.ReportUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Mingc on 4/27/2017.
 * This is the class that will generate and store every data needed for
 * showing in the table
 */

public class TableData {
    public int ID_Number;
    public int N; // Number of Vertices
    public double R; // Threshold for connecting
    public int M; // Number of Edges
    public int minDegree;
    public int avgDegree;
    public int maxDegree;
    public int maxDegreeWhenDelete;
    public int numOfColor; // number of color that be used
    public int largestColorSize;
    public int terminalCliqueSize;
    public int edgesInBipartite;

    public TableData(@NonNull RandomGeometricGraph RGG) {
        this.N = RGG.getNumberOfPoints();
        this.R = RGG.getThresholdToConnect();
        this.M = RGG.getEdges().size();
        this.minDegree = ReportUtil.getMinDegree(RGG);
        this.avgDegree = ReportUtil.getAvgDegree(RGG);
        this.maxDegree = ReportUtil.getMaxDegree(RGG);
        getColorData(RGG);
        /*BackBone backBone = ReportUtil.backBoneGenerator(RGG);
        this.edgesInBipartite = backBone.getBipartiteEdges().size();*/
    }

    private void getColorData(RandomGeometricGraph RGG){
        Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();
        Map<Integer, List<Point>> degreeList = new HashMap<>();
        ColorUtil.generateDegreeList(adjacentList, degreeList);
        Stack<List<Point>> toColorStack = new Stack<>();
        Map<Point, List<Point>> leftAdjacentList = ColorUtil.copyList(adjacentList);

        ColorUtil.orderColor(toColorStack, degreeList, leftAdjacentList);

        int maxDegreeWhenDelete = 0;
        int numOfColor = 0;

        int terminalCliqueSize = 0;
        int preNeighbors = -1;
        while(!toColorStack.isEmpty()){
            List<Point> toColorList = toColorStack.pop();
            if(toColorList.size() - 1 == preNeighbors - 1){
                terminalCliqueSize ++;
            }else{
                terminalCliqueSize = 1;
            }
            preNeighbors = toColorList.size() - 1;

            Point colorPoint = toColorList.get(0);
            int degree = toColorList.size() - 1;
            maxDegreeWhenDelete = Math.max(maxDegreeWhenDelete, degree);

            int color = ColorUtil.assignColor(toColorList);
            numOfColor = Math.max(numOfColor, color);
            colorPoint.setColor(color);
        }
        this.terminalCliqueSize = terminalCliqueSize;
        this.maxDegreeWhenDelete = maxDegreeWhenDelete;
        this.numOfColor = numOfColor;
        int[] colors = new int[numOfColor + 1];
        for(Point point : RGG.getPoints()){
            colors[point.getColor()] ++;
        }
        int largestColorSize = 0;
        for(int i = 0; i < numOfColor; i ++){
            largestColorSize = Math.max(largestColorSize, colors[i]);
        }
        this.largestColorSize = largestColorSize;


    }







}
