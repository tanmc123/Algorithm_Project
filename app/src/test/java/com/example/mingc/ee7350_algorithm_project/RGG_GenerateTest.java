package com.example.mingc.ee7350_algorithm_project;

import com.example.mingc.ee7350_algorithm_project.model.Disk;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Mingc on 4/27/2017.
 */

public class RGG_GenerateTest {

    @Test
    public void generateTest(){
        int n = 10;
        int[] nums = new int[n];
        long[] times = new long[n];
        double[] ratios = new double[n];

        for(int i = 0; i < n; i ++){
            UserInput userInput = fakeUserInput(5 * i + 1);
            RandomGeometricGraph RGG = randomGeometricGraphFactory(userInput);
            nums[i] = RGG.getNumberOfPoints();
            long startTime = System.currentTimeMillis();
            RGG.fastCell2();
            long endTime = System.currentTimeMillis();
            times[i] = endTime - startTime;
            ratios[i] = (double) times[i] / RGG.getEdges().size();
        }
        showResult(nums, times, ratios);

    }

    public static void showResult(int[] nums, long[] times, double[] ratios){
        Assert.assertEquals(nums.length, times.length);
        System.out.println("Points #     Time (ms)     Ratio");
        for(int i = 0; i < nums.length; i ++){
            System.out.printf("%-13d", nums[i]);
            System.out.printf("%-14d", times[i]);
            System.out.printf("%-1.5f\n", ratios[i]);



        }
    }


    public static RandomGeometricGraph randomGeometricGraphFactory(UserInput userInput){
        RandomGeometricGraph RGG = null;
        switch (userInput.topology){
            case SQUARE:
                RGG = new Square(userInput);
                break;
            case CIRCLE:
                RGG = new Disk(userInput);
                break;
        }
        return RGG;
    }

    public static UserInput fakeUserInput(int n){
        int numOfPoints = 1000 * n;
        int aveDegree = 32 * (n / 10 + 1);
        Topology teTopology = Topology.SQUARE;
        return new UserInput(numOfPoints, aveDegree, teTopology);
    }
}
