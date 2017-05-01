package com.example.mingc.ee7350_algorithm_project;

import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;

import org.junit.Test;

/**
 * Created by Mingc on 4/20/2017.
 */

public class CellMethodTest {

    @Test
    public void cellMethodTest(){
        Square square = (Square) fakeData();
        square.cellMethod();
    }


    private RandomGeometricGraph fakeData(){
        int n = 1;
        int numOfPoints = 1000 * n;
        int aveDegree = 32 * (int) Math.sqrt(n);
        Topology teTopology = Topology.SQUARE;
        UserInput userInput = new UserInput(numOfPoints, aveDegree, teTopology);
        RandomGeometricGraph square = new Square(userInput);
        return square;
    }
}
