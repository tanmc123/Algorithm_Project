package com.example.mingc.ee7350_algorithm_project.model;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.widget.ListView;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

/**
 * Created by Mingc on 4/1/2017.
 */

public class Square extends RandomGeometricGraph {

    // Default Settings if no input
    public Square() {
        numberOfPoints = 20;
        thresholdToConnect = 0.4;
        topology = Topology.SQUARE;
        points = new ArrayList<>();
        adjacentList = new LinkedHashMap<Point, List<Point>>();
        edges = new ArrayList<>();
        generate();
    }

    public Square(UserInput userInput){
        numberOfPoints = userInput.numberOfPoints;
        topology = Topology.SQUARE;
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
            Point point = new Point(random.nextDouble(),random.nextDouble());
            points.add(point);
        }
        fastCell();
        //cellMethod();
        //sweepMethod();
        //bruteForce();
        //fastCell2();

    }




    @Override
    protected double aveDegreeToThreshold(int avgDegree) {
        return Math.sqrt(avgDegree /
                        (numberOfPoints * Constants.PI));
    }
}
