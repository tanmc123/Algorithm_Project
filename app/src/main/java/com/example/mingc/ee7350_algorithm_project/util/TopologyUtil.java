package com.example.mingc.ee7350_algorithm_project.util;

import android.support.annotation.NonNull;

import com.example.mingc.ee7350_algorithm_project.model.Topology;

/**
 * Created by Mingc on 3/29/2017.
 */

public class TopologyUtil {

    @NonNull
    public static String topologyToString(Topology topology){
        switch (topology){
            case SQUARE:
                return "Square";
            case CIRCLE:
                return "Circle";
            case SPHERE:
                return "Sphere";
        }
        return "Square";
    }

    public static Topology StringToTopology(String string){
        switch (string){
            case "Square":
                return Topology.SQUARE;
            case "Circle":
                return Topology.CIRCLE;
            case "Sphere":
                return Topology.SPHERE;
        }
        return Topology.SQUARE;
    }
}
