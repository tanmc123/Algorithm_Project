package com.example.mingc.ee7350_algorithm_project.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Mingc on 4/22/2017.
 */

public class BackBone{
    //private RandomGeometricGraph RGG;
    private double thresholdToConnect;
    private Topology topology;



    private List<Point> bipartitePoints; // All BipartitePoint
    private List<Point> backbone;

    private Set<Point> colorSet1;
    private Set<Point> colorSet2;
    private List<Set<Point>> backBoneEdges; // Giant Component
    private List<Set<Point>> bipartiteEdges;


    // Use to find out backbone
    private class UnionFind {
        Map<Point, Point> father;

        UnionFind(List<Point> points){
            father = new HashMap<>();
            for(Point point : points){
                father.put(point, point);
            }
        }

        Point findFather(Point point){
            Point result = point;
            while(result != father.get(result)){
                result = father.get(result);
            }
            return result;
        }

        void connect(Point point1, Point point2){
            Point root1 = findFather(point1);
            Point root2 = findFather(point2);
            if(root1 != root2){
                father.put(root1, root2);
            }
        }
    }

    public BackBone(@NonNull RandomGeometricGraph RGG, Set<Point> colorSet1, Set<Point> colorSet2){
        //this.RGG = RGG;
        this.thresholdToConnect = RGG.thresholdToConnect;
        this.topology = RGG.topology;
        this.colorSet1 = colorSet1;
        this.colorSet2 = colorSet2;
        bipartitePoints = new ArrayList<>();
        bipartitePoints.addAll(colorSet1);
        bipartitePoints.addAll(colorSet2);
        this.bipartiteEdges = getMyEdges(RGG.edges, colorSet1, colorSet2);
        backbone = findBackBone();
        backBoneEdges = findBackBoneEdges();
    }



    private List<Set<Point>> findBackBoneEdges(){
        List<Set<Point>> backBoneEdges = new ArrayList<>();
        for(Set<Point> edge : bipartiteEdges){
            Point point = edge.iterator().next();
            if(backbone.contains(point)){
                backBoneEdges.add(edge);
            }
        }
        return backBoneEdges;
    }


    private List<Point> findBackBone(){
        List<Point> backBone = new ArrayList<>();
        UnionFind uf = new UnionFind(bipartitePoints);
        // Connect all the connected parts
        for(Point point1 : bipartitePoints){
            for(Point point2 : bipartitePoints){
                if(point1 == point2 || !isConnected(point1, point2)){
                    continue;
                }
                uf.connect(point1, point2);
            }
        }


/*        for(Point point1 : colorSet1){
            for(Point point2 : colorSet2){
                if(point1 == point2) continue;
                if(!isConnected(point1, point2)) continue;
                uf.connect(point1, point2);
            }
        }*/
        Map<Point, Set<Point>> hm = new HashMap<>();
        for(Point point : bipartitePoints){
            Point root = uf.findFather(point);
            if(!hm.containsKey(root)){
                hm.put(root, new HashSet<Point>());
            }
            Set<Point> set = hm.get(root);
            set.add(point);
            hm.put(root, set);
        }
        int size = 0;
        for(Set<Point> set : hm.values()){
            if(set.size() > size){
                size = set.size();
                backBone = new ArrayList<>(set);
            }
        }
        return backBone;
    }

    private List<Set<Point>> getMyEdges(List<Set<Point>> allEdges,
                                        Set<Point> colorSet1, Set<Point> colorSet2){
        List<Set<Point>> myEdges = new ArrayList<>();
        for(Point point1 : colorSet1){
            for (Point point2 : colorSet2){
                if(point1 == point2) continue;
                if(isConnected(point1, point2)){
                    Set<Point> set = new HashSet<>();
                    set.add(point1);
                    set.add(point2);
                    myEdges.add(set);
                }
            }
        }
        return myEdges;
    }


    public void setBipartitePoints(List<Point> bipartitePoints) {
        this.bipartitePoints = bipartitePoints;
    }

    private boolean isConnected(Point A, Point B) {
        double distance = Math.sqrt(
                (A.x - B.x) * (A.x - B.x) +
                        (A.y - B.y) * (A.y - B.y)
        );
        return distance <= thresholdToConnect;
    }

    // Attributes' getter ----------------------------------------------------------
    public List<Point> getBipartitePoints() {
        return bipartitePoints;
    }

    public List<Point> getBackbone() {
        return backbone;
    }

    public Set<Point> getColorSet1() {
        return colorSet1;
    }

    public Set<Point> getColorSet2() {
        return colorSet2;
    }

    public List<Set<Point>> getBackBoneEdges() {
        return backBoneEdges;
    }

    public List<Set<Point>> getBipartiteEdges() {
        return bipartiteEdges;
    }

    public Topology getTopology() {
        return topology;
    }
}
