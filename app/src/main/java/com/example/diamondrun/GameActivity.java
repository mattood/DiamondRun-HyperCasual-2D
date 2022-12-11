package com.example.diamondrun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends MainActivity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        gameView = new GameView(this, size.x, size.y);
        setContentView(gameView);

        // Create a new LinearLayout to hold the content of the NextSceneActivity
        LinearLayout layout = new LinearLayout(this);

        // Set the orientation of the LinearLayout to vertical
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create a new TextView to display a message
        TextView messageView = new TextView(this);

        // Set the text of the TextView to "This is the next scene"
        messageView.setText("This is the next scene");

        // Add the TextView to the LinearLayout
        layout.addView(messageView);

        // Set the layout of the NextSceneActivity to the LinearLayout
        setContentView();

    }

    private void setContentView() {
    }
}