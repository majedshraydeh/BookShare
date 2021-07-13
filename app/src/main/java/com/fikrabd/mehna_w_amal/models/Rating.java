package com.fikrabd.mehna_w_amal.models;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("avg")
    private double average;

    @SerializedName("avg1")
    private int average1;

    @SerializedName("avg2")
    private int average2;

    @SerializedName("avg3")
    private int average3;

    @SerializedName("avg4")
    private int average4;

    @SerializedName("avg5")
    private int average5;


    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getAverage1() {
        return average1;
    }

    public void setAverage1(int average1) {
        this.average1 = average1;
    }

    public int getAverage2() {
        return average2;
    }

    public void setAverage2(int average2) {
        this.average2 = average2;
    }

    public int getAverage3() {
        return average3;
    }

    public void setAverage3(int average3) {
        this.average3 = average3;
    }

    public int getAverage4() {
        return average4;
    }

    public void setAverage4(int average4) {
        this.average4 = average4;
    }

    public int getAverage5() {
        return average5;
    }

    public void setAverage5(int average5) {
        this.average5 = average5;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "average=" + average +
                ", average1=" + average1 +
                ", average2=" + average2 +
                ", average3=" + average3 +
                ", average4=" + average4 +
                ", average5=" + average5 +
                '}';
    }
}
