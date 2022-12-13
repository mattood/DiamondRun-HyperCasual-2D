package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

public class GameView extends View {

    private Handler handler;
    private Runnable r;
    private DiamondCollection diamondCollection;
    private PlayerDiamond playerDiamond;
    private int numDiamonds = 5;
    private int screenX, screenY;
    private GameGrid gameGrid;

    public GameView(Context context, int screenWidth, int screenHeight)  {
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

        screenX = screenWidth;
        screenY = screenHeight;


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

        gameGrid = diamondCollection.getGameGrid();

        switch(event.getAction()) {



            case MotionEvent.ACTION_DOWN: //this is for taps, check this either first or second

            if (gameGrid.insideAxis(0, (int) event.getX())) { //if he clicked inside axis 1
                movePlayerDiamondIntoCenterAxis(0); //move him to same location
                break;
            }
            if (gameGrid.insideAxis(1, (int) event.getX())) { //if he clicked inside axis 2
                movePlayerDiamondIntoCenterAxis(1); //move him to same location
                break;
            }
            if (gameGrid.insideAxis(2, (int) event.getX())) { //if he clicked inside axis 3
                movePlayerDiamondIntoCenterAxis(2); //move him to same location
                break;
            }
            if (gameGrid.insideAxis(3, (int) event.getX())) { //if he clicked inside axis 4
                movePlayerDiamondIntoCenterAxis(3); //move him to same location
                break;
            }
            if (gameGrid.insideAxis(4, (int) event.getX())) { //if he clicked inside axis 5
                movePlayerDiamondIntoCenterAxis(4); //move him to same location
                break;
            }
        }
        return true;
    }

    public void movePlayerDiamondIntoCenterAxis(int i){
        playerDiamond.setXLocation(diamondCollection.getXLocation(i)); //set him to same x location as specific diamondCollection index
    }






    }
