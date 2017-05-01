package com.example.mingc.ee7350_algorithm_project.model;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by Mingc on 4/19/2017.
 */

public class Disk extends RandomGeometricGraph {


    // Default Settings if no input
    public Disk() {
        // Set first Benchmark Data as default
        numberOfPoints = 4000;
        thresholdToConnect = aveDegreeToThreshold(64);
        topology = Topology.CIRCLE;
        points = new ArrayList<>();
        adjacentList = new LinkedHashMap<Point, List<Point>>();
        edges = new ArrayList<>();
        generate();
    }

    public Disk(UserInput userInput){
        numberOfPoints = userInput.numberOfPoints;
        thresholdToConnect = aveDegreeToThreshold(userInput.avgDegree);
        topology = Topology.CIRCLE;
        points = new ArrayList<>();
        adjacentList = new LinkedHashMap<Point, List<Point>>();
        edges = new ArrayList<>();
        generate();
    }

    @Override
    protected void generate() {
        Random random = new Random();
        for (int i = 0; i < numberOfPoints; i++) {
            double r = Math.sqrt(random.nextDouble());
            //double r = random.nextDouble();
            double theta = 2 * Constants.PI * random.nextDouble();
            double x = 0.5 + 0.5 * r * Math.sin(theta);
            double y = 0.5 + 0.5 * r * Math.cos(theta);
            Point point = new Point(x, y);
            points.add(point);
        }
        fastCell();
    }



    @Override
    protected double aveDegreeToThreshold(int avgDegree) {
        return Math.sqrt((double) avgDegree /
                numberOfPoints);
    }








}
