package com.example.mingc.ee7350_algorithm_project.report;

import android.support.design.widget.TabLayout;

import com.example.mingc.ee7350_algorithm_project.model.BackBone;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.TableData;
import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;
import com.example.mingc.ee7350_algorithm_project.util.ColorUtil;
import com.example.mingc.ee7350_algorithm_project.util.ReportUtil;

import org.junit.Test;

/**
 * Created by Mingc on 4/29/2017.
 */

public class ReportData {
    int[] data1;
    int[] data2;

    @Test
    public void showData(){
        showDataTitle();
        UserInput[] userInputs = initialBenchMark();

        for(int i = 0; i < 10; i ++){
            UserInput userInput = userInputs[i];
            RandomGeometricGraph RGG = new Square(userInput);
            TableData tableData = new TableData(RGG);
            System.out.printf("%-8d", tableData.N);
            System.out.printf("%-7f ", tableData.R);
            System.out.printf("%-8d", tableData.M);
            System.out.printf("%-13d", tableData.minDegree);
            System.out.printf("%-13d", tableData.avgDegree);
            System.out.printf("%-13d", tableData.maxDegree);
            System.out.printf("%-13d", tableData.maxDegreeWhenDelete);
            System.out.printf("%-13d", tableData.numOfColor);
            System.out.printf("%-13d", tableData.largestColorSize);
            System.out.printf("%-13d", data1[i]);
            System.out.printf("%-13d\n", data2[i]);

/*            UserInput userInput = userInputs[i];
            RandomGeometricGraph RGG = new Square(userInput);
            ColorUtil.color(RGG);
            BackBone backBone = ReportUtil.backBoneGenerator(RGG);
            System.out.printf("%-15d", backBone.getBipartiteEdges().size());*/

        }
    }

    protected UserInput[] initialBenchMark(){
        UserInput[] userInputs = new UserInput[10];
        userInputs[0] = new UserInput(1000, 32, Topology.SQUARE);
        userInputs[1] = new UserInput(4000, 64, Topology.SQUARE);
        userInputs[2] = new UserInput(16000, 64, Topology.SQUARE);
        userInputs[3] = new UserInput(64000, 64, Topology.SQUARE);
        userInputs[4] = new UserInput(64000, 128, Topology.SQUARE);
        userInputs[5] = new UserInput(4000, 64, Topology.CIRCLE);
        userInputs[6] = new UserInput(4000, 128, Topology.CIRCLE);
        userInputs[7] = new UserInput(4000, 64, Topology.SPHERE);
        userInputs[8] = new UserInput(16000, 128, Topology.SPHERE);
        userInputs[9] = new UserInput(64000, 128, Topology.SPHERE);

        return userInputs;
    }

    protected void showDataTitle(){
        System.out.printf("%-8s", "N");
        System.out.printf("%-8s", "R");
        System.out.printf("%-8s", "M");
        System.out.printf("%-13s", "minDegree");
        System.out.printf("%-13s", "avgDegree");
        System.out.printf("%-13s", "maxDegree");
        System.out.printf("%-13s", "maxDwhenD");
        System.out.printf("%-13s", "# color");
        System.out.printf("%-13s", "max color");
        System.out.printf("%-13s", "# clique");
        System.out.printf("%-15s", "# edges in bip\n");

        data1 = new int[]{18, 35, 38, 41, 61, 34, 54, 38, 56, 64};
        data2 = new int[]{175, 424, 1655, 6474, 3719, 409, 227, 414, 955, 3712};
    }
}
