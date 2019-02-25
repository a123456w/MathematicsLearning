package com.xf.wxf.realizationofmathematicalmodel.coordinate;

import java.util.ArrayList;
import java.util.List;

/*
 *  Created by 李  on 2018/12/10.
 */
public class Axis {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean isXorY=true;
    private List<Spot> mList=new ArrayList();

    public Axis() {
    }

    public Axis(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }
    public boolean isXorY() {
        return isXorY;
    }

    public void setXorY(boolean xorY) {
        isXorY = xorY;
    }

    public List getList() {
        if (isXorY){

        }
        return mList;
    }
}
