package com.example.diamondrun;

public class GameGrid {

    //intervals are arbitrary numbers for now
    public int[] axis1Interval= {0,216};
    public int[] axis2Interval= {216,432};
    public int[] axis3Interval={432, 648};
    public int[] axis4Interval= {648, 864};
    public int[] axis5Interval = {864, 1080};


    public int getAxisLength(){
        int difference = Math.abs(axis1Interval[0]-axis1Interval[1]); //interval length
        return difference;
    }
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

    public int getCenterInsideAxis(int diamondWidth, int[] axisInterval){
        int axisMiddleLocation = (axisInterval[0] + axisInterval[1]) / 2;
        return axisMiddleLocation - diamondWidth;
    }




}
