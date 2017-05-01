package com.example.mingc.ee7350_algorithm_project;

import com.example.mingc.ee7350_algorithm_project.model.Disk;
import com.example.mingc.ee7350_algorithm_project.model.Point;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;
import com.example.mingc.ee7350_algorithm_project.util.ColorUtil;
import com.example.mingc.ee7350_algorithm_project.util.ReportUtil;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Mingc on 4/22/2017.
 */

public class ReportUtilTest {


    @Test
    public void getLargestFourSetTest(){
        RandomGeometricGraph RGG = RGGGenerator(Topology.SQUARE);
        RGG.cellMethod();
        // Check it is celled
        if (!RGG.isPartIPassedFlag()) throw new AssertionError();

        // Colored
        ColorUtil.color(RGG);
        if(!RGG.isColoredFlag()) throw new AssertionError();


        List<Point> points = RGG.getPoints();
        int numColor = ReportUtil.findNumberOfColors(points);
        List<Set<Point>> coloredSet = ReportUtil.sortPointByColor(points, numColor);
        if(coloredSet.size() != numColor + 1) throw new AssertionError();

        List<Set<Point>> list = ReportUtil.getLargestFourSet(coloredSet);
        Assert.assertEquals(list.size(), 4); // Only four largest color set
        for(int i = 0; i < 4; i ++){
            Iterator<Point> it = list.get(i).iterator();
            Point point1 = it.next();
            while(it.hasNext()){
                Point point = it.next();
                if(point.getColor() != point1.getColor()){
                    throw new AssertionError();
                }
            }
        }

    }


    public void multipleTimeTest(){
        int n = 1000;
        for(int i = 0; i < n; i ++){
            getLargestFourSetTest();
        }
    }


    private RandomGeometricGraph RGGGenerator(Topology topology){
        int n = 1;
        int numOfPoints = 1000 * n;
        int aveDegree = 32 * (int) Math.sqrt(n);
        Topology teTopology = Topology.SQUARE;
        UserInput userInput = new UserInput(numOfPoints, aveDegree, teTopology);
        switch (topology){
            default:
            case SQUARE:
                RandomGeometricGraph square = new Square(userInput);
                return square;
            case CIRCLE:
                teTopology = Topology.CIRCLE;
                userInput = new UserInput(numOfPoints, aveDegree, teTopology);
                RandomGeometricGraph disk = new Disk(userInput);
                return disk;
        }
    }
}
