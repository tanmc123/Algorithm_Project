package com.example.mingc.ee7350_algorithm_project.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mingc.ee7350_algorithm_project.model.BackBone;
import com.example.mingc.ee7350_algorithm_project.model.HistogramData;
import com.example.mingc.ee7350_algorithm_project.model.Point;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Mingc on 4/21/2017.
 * This class contains some static function to help generate the
 * data, table or plots that need to be shown in the report
 */

public class ReportUtil {

    /*
    * Given the colored point list find out the max number of color that used
    * */
    public static int findNumberOfColors(List<Point> points){
        int numColors = 0;
        for(Point point : points){
            numColors = Math.max(numColors, point.getColor());
        }
        return numColors;
    }

    /*
    * Given the list of colored points, return a list of set that contains
    * all the points with same color
    * */
    public static List<Set<Point>> sortPointByColor(List<Point> points, int numColors){
        List<Set<Point>> results = new ArrayList<>();

        // Initialize the results
        for(int i = 0; i <= numColors; i ++){
            Set<Point> set = new HashSet<>();
            results.add(set);
        }
        for(Point point : points){
            Set<Point> set = results.get(point.getColor());
            set.add(point);
        }
        return results;
    }


    @Nullable
    public static BackBone backBoneGenerator(RandomGeometricGraph RGG){
        BackBone backBone = null;
        if(!RGG.isColoredFlag()){
            return null; // The RGG should be colored
        }
        List<Point> points = RGG.getPoints();
        int numColor = findNumberOfColors(points);
        List<Set<Point>> coloredSet = sortPointByColor(points, numColor);
        Set<Point> color1 = null;
        Set<Point> color2 = null;
        // Check every combinations
        int maxNumOfEdge = 0;
        for(int i = 0; i < 4; i ++){
            for(int j = i + 1; j < 4; j ++){
                int numOfEdge = getNumberOfEdges(coloredSet.get(i), coloredSet.get(2), RGG.getThresholdToConnect());
                if(numOfEdge > maxNumOfEdge){
                    maxNumOfEdge = numOfEdge;
                    color1 = coloredSet.get(i);
                    color2 = coloredSet.get(j);
                }
            }
        }
        if(color1 == null || color2 == null) return null;
        backBone = new BackBone(RGG, color1, color2);
        return backBone;
    }

    private static int getNumberOfEdges(Set<Point> set1, Set<Point> set2, double threshold){
        int numOfEdges = 0;
        for (Point point1 : set1) {
            for (Point point2 : set2) {
                if (isConnected(point1, point2, threshold)) {
                    numOfEdges++;
                }
            }
        }
        return numOfEdges;
    }

    public static int getMinDegree(@NonNull RandomGeometricGraph RGG){
        Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();
        int minDegree = Integer.MAX_VALUE;
        for(List<Point> list : adjacentList.values()){
            minDegree = Math.min(list.size(), minDegree);
        }
        return minDegree;
    }

    public static int getMaxDegree(@NonNull RandomGeometricGraph RGG){
        Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();
        int maxDegree = 0;
        for(List<Point> list : adjacentList.values()){
            maxDegree = Math.max(list.size(), maxDegree);
        }
        return maxDegree;
    }

    public static int getAvgDegree(@NonNull RandomGeometricGraph RGG){
        Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();
        long avgDegree = 0;
        for(List<Point> list : adjacentList.values()){
            avgDegree += list.size();
        }
        avgDegree /= adjacentList.size();
        return (int) avgDegree;
    }



    public static HistogramData[] getDegreeWhenColor(RandomGeometricGraph RGG){
        HistogramData[] results = new HistogramData[3];
        Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();
        Map<Integer, List<Point>> degreeList = new HashMap<>();
        ColorUtil.generateDegreeList(adjacentList, degreeList);
        Stack<List<Point>> toColorStack = new Stack<>();
        Map<Point, List<Point>> leftAdjacentList = ColorUtil.copyList(adjacentList);

        ColorUtil.orderColor(toColorStack, degreeList, leftAdjacentList);


        int nums = toColorStack.size();
        List<Integer> xData = new ArrayList<>();
        for(int i = 0; i < nums; i ++){
            xData.add(i);
        }

        HistogramData degreeWhenDelete;


        String title1 = "Degree When Delete";
        List<Integer> yData1 = new ArrayList<>();

        HistogramData OriginalDegree;

        int maxY = getMaxDegree(RGG);
        String title2 = "Original Degree";
        List<Integer> yData2 = new ArrayList<>();

        HistogramData avgDegree;


        String title3 = "Average Degree";
        List<Integer> yData3 = new ArrayList<>();


        while(!toColorStack.isEmpty()){
            List<Point> toColorList = toColorStack.pop();
            Point colorPoint = toColorList.get(0);
            int degree = toColorList.size() - 1;
            yData1.add(degree);
            int degree2 = adjacentList.get(colorPoint).size();
            yData2.add(degree2);

            yData3.add((degree + degree2) / 2);
            int color = ColorUtil.assignColor(toColorList);
            colorPoint.setColor(color);

        }
        degreeWhenDelete = new HistogramData(xData, yData1, nums, maxY, title1);
        OriginalDegree = new HistogramData(xData, yData2, nums, maxY, title2);
        avgDegree = new HistogramData(xData, yData3, nums, maxY, title3);
        results[0] = degreeWhenDelete;
        results[1] = OriginalDegree;
        results[2] = avgDegree;

        return results;
    }

    public  static HistogramData getColorDistribution(RandomGeometricGraph RGG){
        List<Point> points = RGG.getPoints();
        int maxXlabel = 0;
        int maxYlabel = 0;
        String title = "Color Distribution";
        Map<Integer, Integer> colorMap = new HashMap<>();
        for(Point point : points){
            int color = point.getColor();
            maxXlabel = Math.max(maxXlabel, color);
            if(!colorMap.containsKey(color)){
                colorMap.put(color, 1);
            }else {
                colorMap.put(color, colorMap.get(color) + 1);
            }
        }
        for(Integer i : colorMap.values()){
            maxYlabel = Math.max(maxYlabel, i);
        }
        List<Integer> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        for(int i = 0; i <= maxXlabel; i ++){
            xData.add(i);
            int y = colorMap.containsKey(i) ? colorMap.get(i) : 0;
            yData.add(y);
        }
        return new HistogramData(xData, yData, maxXlabel, maxYlabel, title);
    }

    public static HistogramData getDegreeHistogramData(RandomGeometricGraph RGG){
        Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();
        int maxXlabel = 0;
        int maxYlabel = 0;
        String title = "Degree Figure";
        Map<Integer, Integer> degreeMap = new HashMap<>();
        for(Point point : adjacentList.keySet()){
            int degree = adjacentList.get(point).size();
            maxXlabel = Math.max(maxXlabel, degree);
            if(!degreeMap.containsKey(degree)){
                degreeMap.put(degree, 1);
            }else{
                degreeMap.put(degree, degreeMap.get(degree) + 1);
            }
        }
        for(Integer i : degreeMap.values()){
            maxYlabel = Math.max(maxYlabel, i);
        }
        List<Integer> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        for(int i = 0; i <= maxXlabel; i ++){
            xData.add(i);
            int y = degreeMap.containsKey(i) ? degreeMap.get(i) : 0;
            yData.add(y);
        }
        return new HistogramData(xData, yData, maxXlabel, maxYlabel, title);
    }

    // Test Passed
    public static List<Set<Point>> getLargestFourSet(List<Set<Point>> coloredSet){
        List<Set<Point>> results = new ArrayList<>();
        // Use pq to find out the largest four set
        PriorityQueue<Set<Point>> pq = new PriorityQueue<>(4, new Comparator<Set<Point>>() {
            @Override
            public int compare(Set<Point> o1, Set<Point> o2) {
                return o2.size() - o1.size(); // if o2.size > o1.size : switch
            }
        });
        for(Set<Point> set : coloredSet){
            pq.add(set);
        }

        // Obtain the first four
        for(int i = 0; i < 4; i ++){
            results.add(pq.poll());
        }
        return results;
    }

    private static boolean isConnected(Point A, Point B, double thresholdToConnect) {
        double distance = Math.sqrt(
                (A.x - B.x) * (A.x - B.x) +
                        (A.y - B.y) * (A.y - B.y)
        );
        return distance <= thresholdToConnect;
    }


}
