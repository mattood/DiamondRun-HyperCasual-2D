package com.example.diamondrun;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
    Timer timer = new Timer();
    TimerTask timerTask;
    int time;

    public MyTimer(int time){
        this.time = time;
    }

    public void startTimer(){
        timerTask = new TimerTask()
        {
            @Override
            public void run(){
                time--;
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public int getTimerSeconds(){
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        return seconds;
    }

    public int getTimerMinutes(){
        int rounded = (int) Math.round(time);
        int minutes = ((rounded % 86400) % 3600) /60;
        return minutes;
    }

    public String formatTime(int seconds, int minutes){
        return String.format(""+minutes) + ":" + String.format("%02d", seconds);
    }


    public void stopTimer(){
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
    }

    public void resetTimer(){
        if(timerTask!= null){
            timerTask.cancel();
            //time = 10;
        }
    }

}