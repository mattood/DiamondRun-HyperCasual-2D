package com.example.diamondrun;

public class GameGrid {

    //intervals are arbitrary numbers for now
//    public int[] axis1Interval= {0,216};
//    public int[] axis2Interval= {216,432};
//    public int[] axis3Interval={432, 648};
//    public int[] axis4Interval= {648, 864};
//    public int[] axis5Interval = {864, 1080};

    private int axis_interval;

    GameGrid(int numAxis, int screenWidth){
        axis_interval = screenWidth/numAxis;
    }

    public int getAxisLength(){
        return axis_interval;
//        int difference = Math.abs(axis1Interval[0]-axis1Interval[1]); //interval length
//        return difference;
    }

    // axisIndex = 0 .. numAxis
    public boolean insideAxis(int axisIndex, int eventGetXLocation) {
        int leftX = axis_interval * axisIndex;
        int rightX = (axis_interval * (axisIndex+1)) - 1;

        return (eventGetXLocation >= leftX && eventGetXLocation < rightX);
    }

  /*


    public boolean insideAxis1(int eventGetXLocation) {
        if (eventGetXLocation >= axis1Interval[0] && eventGetXLocation <= axis1Interval[1]) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insideAxis2(int eventGetXLocation){
        if(eventGetXLocation >= axis2Interval[0] && eventGetXLocation <= axis2Interval[1]){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean insideAxis3(int eventGetXLocation){
        if(eventGetXLocation >= axis3Interval[0] && eventGetXLocation <= axis3Interval[1]){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean insideAxis4(int eventGetXLocation){
        if(eventGetXLocation >= axis4Interval[0] && eventGetXLocation <= axis4Interval[1]){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean insideAxis5(int eventGetXLocation){
        if(eventGetXLocation >= axis5Interval[0] && eventGetXLocation <= axis5Interval[1]){
            return true;
        }
        else{
            return false;
        }
    }
*/
    public int getDiamondXposition(int diamondWidth, int axisIndex){
        int leftX = axis_interval * axisIndex;

        int leftXofDiamond = leftX + (axis_interval-diamondWidth)/2;
        return leftXofDiamond;
    }




}
