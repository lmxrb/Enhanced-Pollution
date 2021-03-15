package com.lmx1.enhancedpollution.Utils;

public class Coordinate {

    private int x;
    private int z;

    public Coordinate( int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX(){
        return this.x;
    }

    public int getZ(){
        return this.z;
    }

    public void setX(int newX){
        this.x = newX;
    }

    public void setZ(int newZ){
        this.z = newZ;
    }
}