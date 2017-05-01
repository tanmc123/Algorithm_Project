package com.example.mingc.ee7350_algorithm_project.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mingc on 3/24/2017.
 */

public class Point implements Parcelable{

    public double x;
    public double y;
    public double z = 0;
    // public double z;   // for sphere
    private int color = 0; // 0 means have not set yet
    private int degree = 0;



    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected Point(Parcel in) {
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
        color = in.readInt();
        degree = in.readInt();
    }

    public static final Creator<Point> CREATOR = new Creator<Point>() {
        @Override
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        @Override
        public Point[] newArray(int size) {
            return new Point[size];
        }
    };

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(x);
        dest.writeDouble(y);
        dest.writeDouble(z);
        dest.writeInt(color);
        dest.writeInt(degree);
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
