package com.example.mingc.ee7350_algorithm_project;

import com.example.mingc.ee7350_algorithm_project.model.Point;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mingc on 4/5/2017.
 */

public class SquareClassMethodTest {

    @Test
    public void adj2edgesTest(){
        UserInput userInput = fakeUserInput();
        Square square = new Square(userInput);
        long startTime = 0;
        long endTime = 0;




/*
        // Burte Force-----------------------------------------------------------------------
        square.clean();
        startTime = System.currentTimeMillis();
        square.bruteForce();
        endTime = System.currentTimeMillis();
        System.out.println("BurteForce Running time is " + (endTime - startTime) + " ms");

        square.doAdjToEdgesTransform();

        showSquareDetail(square);

*/


    }

    private UserInput fakeUserInput(){
        int n = 1;
        int numOfPoints = 1000 * n;
        int aveDegree = 32 * (int) Math.sqrt(n);
        Topology teTopology = Topology.SQUARE;
        return new UserInput(numOfPoints, aveDegree, teTopology);
    }

    public void showSquareDetail(RandomGeometricGraph RGG){
        System.out.println("The number of points is " + RGG.getPoints().size());
        System.out.println("The threshold is " + RGG.getThresholdToConnect());
        System.out.println("The number of edges is " + RGG.getEdges().size());

    }
}
