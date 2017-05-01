package com.example.mingc.ee7350_algorithm_project;

import com.example.mingc.ee7350_algorithm_project.model.Constants;
import com.example.mingc.ee7350_algorithm_project.model.Point;

import org.jetbrains.annotations.Contract;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Created by Mingc on 4/20/2017.
 * This test is to find out whether force cast (int) work correctly as espected
 */

public class NumberSystemTest {

    // Number cast test passed
    @Test
    public void castTest(){
        double n = 0.1234223; // random value
        double x = 0.132321323;

        int id = (int) (x / n);
        Assert.assertEquals(id, 1);
    }

    @Test
    public void fakeCellTest(){
        double thresholdToConnect = 0.10000001;
        int rows = (int) (1 / thresholdToConnect) + 1;
        Assert.assertEquals(rows, 10);
        // Fake point
        Point point1 = new Point(0.1, 0.1);
        int id = findID(point1, rows, thresholdToConnect);
        Assert.assertEquals(id, 0);

        Point point2 = new Point(0.2, 0.1);
        int id2 = findID(point2, rows, thresholdToConnect);
        Assert.assertEquals(id2, 1);

        Point point3 = new Point(0.1, 0.2);
        int id3 = findID(point3, rows, thresholdToConnect);
        Assert.assertEquals(id3, 10);
    }

    @Test
    public void sinFunctionTest(){
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            double r = Math.sqrt(random.nextDouble());
            //double r = random.nextDouble();
            double theta = 2 * Constants.PI * random.nextDouble();
            double x = 0.5 + 0.5 * r * Math.sin(theta);
            double y = 0.5 + 0.5 * r * Math.cos(theta);
            Assert.assertFalse(x > 1 || y > 1);
            Assert.assertFalse(x < 0 || y < 0);
        }

    }


    @Contract(pure = true)  // Means no visible sides effects
    private int findID(Point point, int rowsOfCells, double thresholdToConnect){
        int row = (int)(point.y / thresholdToConnect);
        int column = (int) (point.x / thresholdToConnect);
        return row * rowsOfCells + column;
    }
}
