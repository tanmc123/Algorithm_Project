package com.example.mingc.ee7350_algorithm_project;

import com.example.mingc.ee7350_algorithm_project.model.Point;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Mingc on 4/17/2017.
 * Test the usage of ArrayList
 */

public class ArrayListTest {
    ArrayList<LinkedList<Point>> degreeList;

    @Test
    public void setTest(){
        degreeList = new ArrayList<>();
        LinkedList<Point> list = new LinkedList<>();
        Point point1 = new Point(0.1, 0.1);
        list.add(point1);
        degreeList.ensureCapacity(10);
        degreeList.set(3, list);
    }
}
