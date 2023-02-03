package com.example.diamondrun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Animation backgroundAnim;
    Animation diamondWordAnim;
    Animation runWordAnim;
    Animation shardAnim;
    ImageView image;
    ImageView imageDiamondWord;
    ImageView imageRunWord;
    ImageView imageShard;
    AnimatorSet animSet = new AnimatorSet();
    private MediaPlayer gameStartMusic;
    SharedPreferences settings;
    String playerName;
    Intent intent;
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";

    //retrieving the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //creating a reference/ location where the data is going to go
    DatabaseReference databaseReference = database.getReference("Location");



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);



        //username never been saved so take us into playername class to set it
        intent = null;
        if (!preferences.contains(KEY_CREDENTIALS)) {
            intent = new Intent(MainActivity.this, PlayerName.class);
            startActivity(intent);
        }
        //username already saved in preferences so continue to main game
        else {
            intent = new Intent(this, MainActivity.class);
            setContentView(R.layout.activity_main);


            backgroundAnim = AnimationUtils.loadAnimation(this, R.anim.background_animation);
            diamondWordAnim = AnimationUtils.loadAnimation(this, R.anim.move_right);
            runWordAnim = AnimationUtils.loadAnimation(this, R.anim.move_left);
            shardAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            gameStartMusic = MediaPlayer.create(MainActivity.this, R.raw.gamemusic);
            gameStartMusic.start();

            image = findViewById(R.id.background);
            imageShard = findViewById(R.id.shard);
            imageShard.setVisibility(View.INVISIBLE);
            imageDiamondWord = findViewById(R.id.diamond);
            imageRunWord = findViewById(R.id.run);
            imageRunWord.setVisibility(View.INVISIBLE);
            //image.setAnimation(backgroundAnim);
            imageDiamondWord.setAnimation(diamondWordAnim);
            //imageRunWord.setAnimation(runWordAnim);


            //progress bar of task while waiting for response
        /*ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Waiting for opponent");
        progressDialog.show();*/


            diamondWordAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imageRunWord.setVisibility(View.VISIBLE);
                    imageRunWord.startAnimation(runWordAnim);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            runWordAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imageShard.setVisibility(View.VISIBLE);
                    imageShard.startAnimation(shardAnim);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, GameActivity.class));

                }
            });
        }
        //storing player username to database
        databaseReference.child("Username").setValue(preferences.getString(KEY_CREDENTIALS, ""));
    }

}


