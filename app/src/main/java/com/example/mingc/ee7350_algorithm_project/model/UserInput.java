package com.example.mingc.ee7350_algorithm_project.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mingc.ee7350_algorithm_project.util.TopologyUtil;

/**
 * Created by Mingc on 3/29/2017.
 */

public class UserInput implements Parcelable {
    public int numberOfPoints;
    public int avgDegree;
    public Topology topology;

    public UserInput(){
        // Give a initialization for userInput, this can be seen as default values
        this.numberOfPoints = 1000;
        this.avgDegree = 32;
        topology = Topology.SQUARE;
    }

    public UserInput(int numberOfPoints, int aveDegree, Topology topology) {
        this.numberOfPoints = numberOfPoints;
        this.avgDegree = aveDegree;
        this.topology = topology;
    }

    protected UserInput(Parcel in) {
        numberOfPoints = in.readInt();
        avgDegree = in.readInt();
        topology = TopologyUtil.StringToTopology(in.readString());
    }

    public static final Creator<UserInput> CREATOR = new Creator<UserInput>() {
        @Override
        public UserInput createFromParcel(Parcel in) {
            return new UserInput(in);
        }

        @Override
        public UserInput[] newArray(int size) {
            return new UserInput[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(numberOfPoints);
        dest.writeInt(avgDegree);
        dest.writeString(TopologyUtil.topologyToString(topology));
    }
}
