package com.example.mingc.ee7350_algorithm_project.util;

import android.support.annotation.NonNull;

import com.example.mingc.ee7350_algorithm_project.model.Point;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Mingc on 4/17/2017.
 * This class is used to realize the smallest last coloring algorithm
 * It need to have a initialized adjacent list which should be
 * generate from Square class
 * This should be defined in constractor
 */

public class ColorUtil {
    // private Map<Point, List<Point>> adjacentList;
    // private Map<Integer, List<Point>> degreeList; // Map<degree, Points>
    // private Map<Point, Integer> degreeMap; // Map<Point, degree>


/*    public ColorUtil(@NonNull Map<Point, List<Point>> adjacentList) {
        this.adjacentList = adjacentList;
        degreeMap = new HashMap<>();
        degreeList = new HashMap<>();
    }*/

    /*
    * Use this function to generate the colored HashMap
    * Each point will get a specific color
    * Use integer number to represent the color
    * */
    public static void color(RandomGeometricGraph RGG){
        // Verify its adjacent list has been created
        if(!RGG.isPartIPassedFlag()){
            return;
        }
        Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();
        Map<Integer, List<Point>> degreeList;
        degreeList = new HashMap<>();


        // O(n)
        generateDegreeList(adjacentList, degreeList);

        Stack<List<Point>> toColorStack = new Stack<>();
        //O(n^2)
        //Map<Point, List<Point>> leftAdjacentList = copyList(adjacentList);
        //adjacentList = copyList(adjacentList);
        orderColor(toColorStack, degreeList, adjacentList);// Generate the to color stack

        //Begin Coloring
        List<Point> newPoints = new ArrayList<>(); // Change the points' order to show the colored orders
        while(!toColorStack.isEmpty()){
            List<Point> toColorList = toColorStack.pop();
            Point point = toColorList.get(0);
            int color = assignColor(toColorList);
            point.setColor(color);
            newPoints.add(point);
        }

        RGG.setPoints(newPoints);
        RGG.setColoredFlag(true); // Mark this RGG has been colored
    }

    public static int assignColor(List<Point> neighborList){
        ArrayList<Integer> usedColorSet = new ArrayList<>();
        for(int i = 1; i < neighborList.size(); i ++){
            Point point = neighborList.get(i);
            usedColorSet.add(point.getColor());
        }
        int color = 1;
        while(usedColorSet.contains(color)){
            color ++;
        }

        return color;
    }



    /*
    * Generate the toColorStack with the help of degreeList and degreeMap
    * */
    public static void orderColor(Stack<List<Point>> toColorStack,
                           Map<Integer, List<Point>> degreeList,
                           Map<Point, List<Point>> leftAdjacentList){


        HashSet<Point> allPoints = new HashSet<>();
        // Add all points into a set O(n) complexity
        for(Point point : leftAdjacentList.keySet()){
            allPoints.add(point);
        }

        checkPoints:
        while(!allPoints.isEmpty()){ // Make sure every point has been colored
            int degree = 0;
            // get the lowest degree point, should be just a few iteration, can be seen as O(1)
            while(degreeList.get(degree).size() == 0){
                degree ++;
                if(!degreeList.containsKey(degree)){
                    break checkPoints; // Reach the last possible degree, no more point available
                }
            }
            List<Point> list = degreeList.get(degree);
            Point toColorPoint = list.get(0); // Take the first one out
            list.remove(0); //remove the point
            //degreeList.put(degree, list); // Delete a point in the degreeList

            List<Point> toColor = new ArrayList<>(); // to be add into stack
            toColor.add(toColorPoint); // index of to color point is always 0
            List<Point> neighbors = leftAdjacentList.get(toColorPoint);
            //leftAdjacentList.remove(toColorPoint);

            toColor.addAll(neighbors);
            toColorStack.add(toColor);

            for(Point neighbor : neighbors){
                // Delete the toColorPoint from the neighbor's adjacent list
                List<Point> nei_neighbor = leftAdjacentList.get(neighbor);
                nei_neighbor.remove(toColorPoint);
                // leftAdjacentList.put(neighbor, nei_neighbor);

                // find its degree
                int neighborDegree = neighbor.getDegree();
                neighbor.setDegree(neighborDegree - 1);


                List<Point> neiList = degreeList.get(neighborDegree);
                neiList.remove(neighbor); // Remove this point
                neiList = degreeList.get(neighborDegree - 1);
                neiList.add(neighbor); // Add into the smaller one

            }

            allPoints.remove(toColorPoint);
        }
    }

    /*
    * Copy a adjacentList which will be edit later
    * The copied list contains the left adjacent points
    * At most O(n^2) complexity
    * */
    public static Map<Point, List<Point>> copyList(Map<Point, List<Point>> adjacentList){
        Map<Point, List<Point>> copyList = new HashMap<>();
        for(Point point : adjacentList.keySet()){
            List<Point> list = new LinkedList<>(); // LinkedList is easier to insert and delete
            list.addAll(adjacentList.get(point));
            copyList.put(point, list);
        }
        return copyList;
    }


    // Generate DegreeList, O(n) complexity, Unit Test Passed
    public static void generateDegreeList(Map<Point, List<Point>> adjacentList,
                                   Map<Integer, List<Point>> degreeList
                                    ){
        // Find max size, O(n)
        int maxDegree = 0;
        for(List<Point> lists : adjacentList.values()){
            maxDegree = Math.max(maxDegree, lists.size());
        }

        // Initialize degreeList, make sure all degree has a list O(n)
        for(int i = 0; i <= maxDegree; i ++){
            List<Point> list = new LinkedList<>();
            degreeList.put(i, list);
        }

        // Add points into degreeList, O(n)
        for(Point point : adjacentList.keySet()){
            int degree = adjacentList.get(point).size();
            List<Point> points = degreeList.get(degree);
            points.add(point);
            degreeList.put(degree, points);
        }

    }


    // Setter and Getter ------------------------------------------------------
/*
    public void setAdjacentList(Map<Point, List<Point>> adjacentList) {
        this.adjacentList = adjacentList;
    }

    public Map<Point, List<Point>> getAdjacentList() {
        return adjacentList;
    }

    public Map<Integer, List<Point>> getDegreeList() {
        return degreeList;
    }

    public Map<Point, Integer> getDegreeMap() {
        return degreeMap;
    }
*/


}
