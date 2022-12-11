package com.example.diamondrun;

import android.content.Context;
import android.view.View;
import android.os.Handler;

public class GameView extends View {
// move the playerdiamond object

    private Handler handler;
    private Runnable r;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                invalidate();

            }
        };
    }





}
