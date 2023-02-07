package com.example.diamondrun;

import static com.example.diamondrun.PlayerName.KEY_CREDENTIALS;
import static com.example.diamondrun.PlayerName.PREF_LOGIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;
    int result;
    Bundle extras;
    public static String user = "";
    public static String name = "Name";
    User user1;

    //retrieving the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //creating a reference for the location username in database
    DatabaseReference databaseReference = database.getReference("DiamondRun");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        extras = this.getIntent().getExtras();
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = getResources().getDimensionPixelSize(resId);
        }

        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);

        //key value store from preferences, setting the users name with value stored from preferences
        user1 = new User(preferences.getString(KEY_CREDENTIALS, ""));

        //storing player username saved in preferences to database
        //databaseReference.setValue(user1.getName());
        //databaseReference.updateChildren("Score", User1.).setValue(10);

        gameView = new GameView(this, dm.widthPixels, dm.heightPixels, user1, databaseReference);

        setContentView(gameView);


    }



}