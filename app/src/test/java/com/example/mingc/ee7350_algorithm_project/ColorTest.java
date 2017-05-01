package com.example.mingc.ee7350_algorithm_project;

import com.example.mingc.ee7350_algorithm_project.model.Point;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;
import com.example.mingc.ee7350_algorithm_project.util.ColorUtil;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Mingc on 4/17/2017.
 */

public class ColorTest {


    @Test
    public void orderTest(){
/*        int n = 20;
        int[] nums = new int[n];
        long[] times = new long[n];
        double[] ratios = new double[n];

        for(int i = 0; i < n; i ++){
            UserInput userInput = RGG_GenerateTest.fakeUserInput(i + 1);
            RandomGeometricGraph RGG = RGG_GenerateTest.randomGeometricGraphFactory(userInput);
            nums[i] = RGG.getNumberOfPoints();
            Stack<List<Point>> toColorStack = new Stack<>();
            Map<Point, List<Point>> adjacentList = RGG.getAdjacentList();
            Map<Integer, List<Point>> degreeList;
            degreeList = new HashMap<>();
            Map<Point, Integer> degreeMap = new HashMap<>();
            long startTime = System.currentTimeMillis();
            ColorUtil.generateDegreeList(adjacentList, degreeList, degreeMap);
            ColorUtil.orderColor(toColorStack, degreeList, degreeMap, adjacentList);
            long endTime = System.currentTimeMillis();

            times[i] = endTime - startTime;
            ratios[i] = (double) times[i] / nums[i];
        }

        RGG_GenerateTest.showResult(nums, times, ratios);*/
    }


}
