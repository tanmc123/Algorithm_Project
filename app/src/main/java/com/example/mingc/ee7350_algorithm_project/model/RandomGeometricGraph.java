package com.example.mingc.ee7350_algorithm_project.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mingc on 3/24/2017.
 */

public abstract class RandomGeometricGraph {
    protected int numberOfPoints;
    protected double thresholdToConnect;
    protected Topology topology;

    protected List<Point> points;
    //protected HashMap<Point, List<Point>> adjacentList;
    protected Map<Point, List<Point>> adjacentList;
    protected List<Set<Point>> edges;

    private boolean partIPassedFlag = false; // will be set to true when adjacent list generated
    private boolean coloredFlag = false; // Mark whether have been colored


    // Use to random generate the points
    protected abstract void generate();

    public void reset(){
        points = new ArrayList<>();
        adjacentList = new LinkedHashMap<Point, List<Point>>();
        edges = new ArrayList<>();
        generate();

    }

    // Judge whether two points are connected or not


    // Convert from average degree to threshold
    protected abstract double aveDegreeToThreshold(int aveDegree);

    // Method to generate adjacent list------------------------------------------------

    // O(n^2)
    public void bruteForce(){
        for(Point point : points){
            List<Point> list = new ArrayList<>();
            for(Point point_b : points){
                if(point == point_b) continue;

                if(isConnected(point, point_b)){
                    list.add(point_b);
                }
            }
            adjacentList.put(point, list);
        }
        adjToEdges();
        this.setPartIPassedFlag(true); // Mark this square has been assigned adjacent list
    }


    public void sweepMethod(){
        List<List<Point>> columnList = new ArrayList<>();
        int columnNum = (int) (1 / thresholdToConnect) + 1; // Constant
        //O(1)
        for(int i = 0; i < columnNum; i ++){
            columnList.add(new ArrayList<Point>());
        }

        // O(n): n points
        for(Point point : points){
            // Put each point into the corresponding list
            int colForPoint = (int) (point.x / thresholdToConnect);
            columnList.get(colForPoint).add(point);
        }

        List<Point> tempPointList = new ArrayList<>(); // Make the points arrange in order

        // O(1) loop: consider columnNum is a small constant
        for(int i = 0; i < columnNum; i ++){
            List<Point> list = columnList.get(i);
            List<Point> newList = new ArrayList<>(list);

            // Combine the neighbor lists
            // Only sweep the adjacent list
            // O(n)
            if(i + 1 < columnNum){
                newList.addAll(columnList.get(i + 1));
            }
            // O(n)
            tempPointList.addAll(list);

            // There are N * r points in begin list
            // O(N^2 * r)
            for(Point startPoint : list){
                List<Point> startPointNeighborList = (adjacentList.containsKey(startPoint)) ?
                        adjacentList.get(startPoint) : new ArrayList<Point>();

                // There are 2 * N * r points, but some of them has swept. Consider it as N * r points
                for(Point endPoint : newList){
                    if(startPoint == endPoint){
                        continue;
                    }
                    // O(1) judge
                    if(isConnected(startPoint, endPoint)){
                        if(startPointNeighborList.contains(endPoint)) {
                            continue;
                        }
                        // O(1)
                        startPointNeighborList.add(endPoint);
                        // Add startPoint to endPoint's list
                        List<Point> endPointNeighborList = (adjacentList.containsKey(endPoint)) ?
                                adjacentList.get(endPoint) : new ArrayList<Point>();

                        if(!endPointNeighborList.contains(startPoint)){
                            endPointNeighborList.add(startPoint);
                            adjacentList.put(endPoint, endPointNeighborList);
                        }

                    }
                }
                adjacentList.put(startPoint, startPointNeighborList);
            }
        }
        points = tempPointList;

        // Generate the edges
        adjToEdges();
        this.setPartIPassedFlag(true); // Mark this square has been assigned adjacent list
    }


    public void cellMethod(){
        // Step 1: divide the unit square into cells, O(rows^2)
        int rows = (int) (1 / thresholdToConnect) + 1; // Number of rows = columns.
        List<List<Point>> cells = new ArrayList<>();
        for(int i = 0; i < (rows * rows); i ++){
            List<Point> cell;
            cell = new ArrayList<>();
            cells.add(cell);
        }

        // Step 2: put each point into its corresponding cell, O(n)
        for(Point point : points){
            int id = findID(point, rows);
            cells.get(id).add(point);
        }

        List<Point> tempPointList = new ArrayList<>(); // Make the points arrange in order

        // Step 3: for each cell, find out its neighbor cells and combine them

        int[] d_id = {rows - 1, 1, rows, rows + 1}; // neighbor four cells
        for(int i = 0; i < cells.size(); i ++){
            List<Point> cell = cells.get(i);
            tempPointList.addAll(cell);

            List<Point> sweepCell = new ArrayList<>(cell);
            for(int k = 0; k <= 3; k ++){
                if(i + d_id[k] < 0 || i + d_id[k] >= cells.size()){
                    continue;
                }
                sweepCell.addAll(cells.get(i + d_id[k]) );
            }

            // Step 4: for each point in the cell, find its neighbors
            for(Point startPoint : cell){
                List<Point> startNeighbor = (adjacentList.containsKey(startPoint)) ?
                        adjacentList.get(startPoint) : new ArrayList<Point>();

                for(Point endPoint : sweepCell){
                    if(startPoint == endPoint || !isConnected(startPoint, endPoint)){
                        continue;
                    }
                    if(startNeighbor.contains(endPoint)) continue;
                    startNeighbor.add(endPoint);
                    List<Point> endNeighbor = (adjacentList.containsKey(endPoint)) ?
                            adjacentList.get(endPoint) : new ArrayList<Point>();
                    if(!endNeighbor.contains(startPoint)){
                        endNeighbor.add(startPoint);
                        adjacentList.put(endPoint, endNeighbor);
                    }
                }
                adjacentList.put(startPoint, startNeighbor);
            }


        }
        // Step 5 : re-arrange the points
        points = tempPointList;

        // Step 6 : Transfer the adjacentList to edges
        adjToEdges();

        this.setPartIPassedFlag(true); // Mark this square has been assigned adjacent list
    }

    public void fastCell() {
        // Step 1: divide the unit square into cells, O(rows^2)
        int n = (int) (1 / thresholdToConnect) + 1; // Number of rows = columns.
        int numOfCells = n * n;

        SparseArray<List<Point>> cells = new SparseArray<>();
        for(int i = 0; i < numOfCells; i ++){
            List<Point> cell = new ArrayList<>();
            cells.append(i, cell);
        }

        for(Point point : points){
            int id = findID(point, n);
            cells.get(id).add(point);
        }

        int[] dx = {0, 1, 1, 1, 0};
        int[] dy = {0, -1, 0, 1, 1};
        for(int x = 0; x < n; x ++) {
            for(int y = 0; y < n; y ++){
                int id = findID(x, y, n);
                for(Point startPoint : cells.get(id)){
                    List<Point> startNei = new ArrayList<>();

                    for(int k = 0; k < 5; k ++){
                        if(x + dx[k] < 0 || x + dx[k] >= n
                                || y + dy[k] < 0 || y + dy[k] >= n){
                            continue;
                        }
                        int id2 = findID(x + dx[k], y + dy[k], n);
                        List<Point> endList = cells.get(id2);
                        for(Point endPoint : endList){
                            if(startPoint == endPoint || !isConnected(startPoint, endPoint)){
                                continue;
                            }
                            startPoint.setDegree(startPoint.getDegree() + 1);
                            startNei.add(endPoint);
                            if(k == 0) continue;

                            List<Point> endNeighbor = (adjacentList.containsKey(endPoint)) ?
                                    adjacentList.get(endPoint) : new ArrayList<Point>();
                            endPoint.setDegree(endPoint.getDegree() + 1);
                            endNeighbor.add(startPoint);
                            adjacentList.put(endPoint, endNeighbor);
                        }
                    }
                    if(adjacentList.containsKey(startPoint)){
                        List<Point> list = adjacentList.get(startPoint);
                        list.addAll(startNei);
                    }else {
                        adjacentList.put(startPoint, startNei);
                    }
                }
            }
        }
        // Step 6 : Transfer the adjacentList to edges
        adjToEdges();

        this.setPartIPassedFlag(true); // Mark this square has been assigned adjacent list
    }

    // The only difference is this method did not use sparseArray which is easier to test
    public void fastCell2(){
        // Step 1: divide the unit square into cells, O(rows^2)
        int n = (int) (1 / thresholdToConnect) + 1; // Number of rows = columns.
        int numOfCells = n * n;

        List<Point>[] cells = new List[numOfCells];
        //SparseArray<List<Point>> cells = new SparseArray<>();
        for(int i = 0; i < numOfCells; i ++){
            cells[i] = new ArrayList<>();

        }

        for(Point point : points){
            int id = findID(point, n);
            cells[id].add(point);
        }

        int[] dx = {0, 1, 1, 1, 0};
        int[] dy = {0, -1, 0, 1, 1};
        for(int x = 0; x < n; x ++) {
            for(int y = 0; y < n; y ++){
                int id = findID(x, y, n);
                for(Point startPoint : cells[id]){
                    List<Point> startNei = new ArrayList<>();

                    for(int k = 0; k < 5; k ++){
                        if(x + dx[k] < 0 || x + dx[k] >= n
                                || y + dy[k] < 0 || y + dy[k] >= n){
                            continue;
                        }
                        int id2 = findID(x + dx[k], y + dy[k], n);
                        List<Point> endList = cells[id2];
                        for(Point endPoint : endList){
                            if(startPoint == endPoint || !isConnected(startPoint, endPoint)){
                                continue;
                            }
                            startPoint.setDegree(startPoint.getDegree() + 1);
                            startNei.add(endPoint);
                            if(k == 0) continue;

                            List<Point> endNeighbor = (adjacentList.containsKey(endPoint)) ?
                                    adjacentList.get(endPoint) : new ArrayList<Point>();
                            endPoint.setDegree(endPoint.getDegree() + 1);
                            endNeighbor.add(startPoint);
                            adjacentList.put(endPoint, endNeighbor);
                        }
                    }
                    if(adjacentList.containsKey(startPoint)){
                        List<Point> list = adjacentList.get(startPoint);
                        list.addAll(startNei);
                    }else {
                        adjacentList.put(startPoint, startNei);
                    }
                }
            }
        }
        // Step 6 : Transfer the adjacentList to edges
        adjToEdges();

        this.setPartIPassedFlag(true); // Mark this square has been assigned adjacent list
    }

/*    // Transfer the adjacent list to an edge list
    protected abstract void adjToEdges();*/


    // Getter for all fields
    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public double getThresholdToConnect() {
        return thresholdToConnect;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Map<Point, List<Point>> getAdjacentList() {
        return adjacentList;
    }

    public List<Set<Point>> getEdges() {
        return edges;
    }

    public boolean isPartIPassedFlag() {
        return partIPassedFlag;
    }

    public void setPartIPassedFlag(boolean partIPassedFlag) {
        this.partIPassedFlag = partIPassedFlag;
    }

    public boolean isColoredFlag() {
        return coloredFlag;
    }

    public void setColoredFlag(boolean coloredFlag) {
        this.coloredFlag = coloredFlag;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    // O(N * Degree)
    protected void adjToEdges(){
        Set<Set<Point>> allEdges = new HashSet<>();
        // N points
        for(Point startPoint : points){

            List<Point> list = adjacentList.get(startPoint);
            // Average Degree points: 32, 64, 128...
            for(Point endPoint : list){
                if(endPoint == startPoint) continue;

                Set<Point> set = new HashSet<>();
                set.add(startPoint);
                set.add(endPoint);

                if(allEdges.contains(set)) continue;

                allEdges.add(set);
                edges.add(set);
            }
        }
    }

    // Check Whether two points are connected
    protected boolean isConnected(Point A, Point B) {
        double distance = Math.sqrt(
                (A.x - B.x) * (A.x - B.x) +
                        (A.y - B.y) * (A.y - B.y)
        );
        return distance <= thresholdToConnect;
    }

    // find which cell the point belongs to, return its id
    @Contract(pure = true)  // Means no visible sides effects
    protected int findID(Point point, int rowsOfCells){
        int row = (int)(point.y / thresholdToConnect);
        int column = (int) (point.x / thresholdToConnect);
        return row * rowsOfCells + column;
    }

    protected int findID(int x, int y, int rowsOfCells){
        return y * rowsOfCells + x;
    }
}
