package com.example.diamondrun;

public class GameGrid {

    private int axis_interval;

    GameGrid(int numAxis, int screenWidth){
        axis_interval = screenWidth/numAxis;
    }

    public int getAxisLength(){
        return axis_interval;
    }

    public boolean insideAxis(int axisIndex, int eventGetXLocation) {
        int leftX = axis_interval * axisIndex;
        int rightX = (axis_interval * (axisIndex+1)) - 1;

        return (eventGetXLocation >= leftX && eventGetXLocation < rightX);
    }


    public int getDiamondXposition(int diamondWidth, int axisIndex){
        int leftX = axis_interval * axisIndex;  //this is the left of the interval

        int leftXofDiamond = leftX + ((axis_interval-diamondWidth)/2);   //puts the left x of diamond at the left x of interval so its centered inside
        return leftXofDiamond;
    }




}
