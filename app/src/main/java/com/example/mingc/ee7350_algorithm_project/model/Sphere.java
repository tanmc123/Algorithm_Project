package com.example.mingc.ee7350_algorithm_project.model;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Mingc on 4/29/2017.
 */

public class Sphere extends RandomGeometricGraph {

    public Sphere() {
        numberOfPoints = 1000;
        thresholdToConnect = 0.1;
        topology = Topology.SPHERE;
        points = new ArrayList<>();
        adjacentList = new LinkedHashMap<Point, List<Point>>();
        edges = new ArrayList<>();
        generate();
    }

    public Sphere(UserInput userInput){
        numberOfPoints = userInput.numberOfPoints;
        topology = Topology.SPHERE;
        thresholdToConnect = aveDegreeToThreshold(userInput.avgDegree);
        points = new ArrayList<>();
        adjacentList = new LinkedHashMap<Point, List<Point>>();
        edges = new ArrayList<>();
        generate();
    }

    @Override
    protected void generate() {
        Random random = new Random();
        for (int i = 0; i < numberOfPoints; i++) {
            double z = random.nextDouble() * 2 - 1;
            double r = Math.sqrt(1 - z * z);
            //double r = random.nextDouble();
            double theta = 2 * Constants.PI * random.nextDouble();
            double x = 0.5 + 0.5 * r * Math.sin(theta);
            double y = 0.5 + 0.5 * r * Math.cos(theta);
            Point point = new Point(x, y, z);
            points.add(point);
        }
        fastCell();
    }

    @Override
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

    @Override
    protected boolean isConnected(Point A, Point B) {
        double distance = Math.sqrt(
                (A.x - B.x) * (A.x - B.x) +
                        (A.y - B.y) * (A.y - B.y) +
                        (A.z - B.z) * (A.z - B.z)
        );
        return distance <= thresholdToConnect;
    }

    @Override
    protected double aveDegreeToThreshold(int avgDegree) {
        return Math.sqrt((double) avgDegree /
                numberOfPoints);
    }
}
