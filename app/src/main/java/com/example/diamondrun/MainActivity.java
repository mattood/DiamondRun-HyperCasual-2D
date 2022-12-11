package com.example.diamondrun;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new LinearLayout to hold the play button
        LinearLayout layout = new LinearLayout(this);

        // Set the orientation of the LinearLayout to vertical
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create a new Button object
        Button playButton = new Button(this);

        // Set the text of the Button to "Play"
        playButton.setText("Play");

        // Set the background color of the Button to blue
        playButton.setBackgroundColor(Color.BLUE);



        final Activity activity = this;

// Set the OnClickListener of the Button to open the next scene when clicked
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent object to open the GameActivity, using the activity variable instead of the this keyword
                Intent intent = new Intent(activity, GameActivity.class);

                // Start the GameActivity using the Intent
                startActivity(intent);
            }
        });

        // Add the Button to the LinearLayout
        layout.addView(playButton);

        // Set the layout of the Activity to the LinearLayout
        setContentView(layout);
    }
}
