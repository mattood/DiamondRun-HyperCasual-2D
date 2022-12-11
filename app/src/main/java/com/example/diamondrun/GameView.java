package com.example.diamondrun;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

public class GameView extends View {
// move the playerdiamond object

    private Handler handler;
    private Runnable r;
    private DiamondCollection diamondCollection;
    private PlayerDiamond playerDiamond;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        diamondCollection = new DiamondCollection();
        playerDiamond = new PlayerDiamond();
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();
                update();
            }
        };
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        for(int i = 0; i < diamondCollection.maxDiamondsNum; i++){
            diamondCollection.draw(canvas);
            playerDiamond.draw(canvas);
        }
    }

    public void update(){

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }






    }
