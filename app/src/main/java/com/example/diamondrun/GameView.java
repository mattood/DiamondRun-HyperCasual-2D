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
    private int numDiamonds = 5;
    private int screenX, screenY;


    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);
        numDiamonds = 5;
        diamondCollection = new DiamondCollection(context, numDiamonds, screenWidth);
         //gamegrid created here
        playerDiamond = new PlayerDiamond
                (context
                ,diamondCollection.get_at(0).getBitmapWidth()
                , diamondCollection.get_at(0).getBitmapHeight(),
                screenWidth,
                screenHeight);

        this.screenX = screenWidth;
        this.screenY = screenHeight;

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
        handler.postDelayed(r, 1);
        diamondCollection.draw(canvas);
        playerDiamond.draw(canvas);
    }

    public void update(){
        diamondCollection.moveDiamondsDownScreen();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }






    }
