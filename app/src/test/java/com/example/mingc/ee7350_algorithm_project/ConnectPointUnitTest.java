package com.example.mingc.ee7350_algorithm_project;

import com.example.mingc.ee7350_algorithm_project.model.Point;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;

import org.junit.Test;

import java.util.List;

/**
 * Created by Mingc on 4/4/2017.
 */

public class ConnectPointUnitTest {

    @Test
    public void testConnectionMethodRunningTime(){
        UserInput userInput = fakeUserInput();
        Square square = new Square(userInput);

        // BurteForce Running Time
        long startTime = System.nanoTime();
        //square.bruteForce();
        long endTime = System.nanoTime();
        System.out.println("BurteForce Running time is " + ((endTime - startTime) / 1000000) + " in ms");

        startTime = System.nanoTime();
        square.sweepMethod();
        endTime = System.nanoTime();
        System.out.println("Sweep Running time is " + ((endTime - startTime) / 1000000) + " in ms");

        List<Point> pointList = square.getPoints();
        for(int i = 1; i < pointList.size(); i ++){
            if(pointList.get(i).x - pointList.get(i - 1).x > 2 * square.getThresholdToConnect()){
                System.out.println("Point Order Error");
            }
        }
    }



    private UserInput fakeUserInput(){
        int n = 100;
        int numOfPoints = 1000 * n;
        int aveDegree = 32 * (int) Math.sqrt(n);
        Topology teTopology = Topology.SQUARE;
        return new UserInput(numOfPoints, aveDegree, teTopology);
    }
}
