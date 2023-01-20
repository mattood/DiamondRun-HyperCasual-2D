package com.example.diamondrun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        backgroundAnim = AnimationUtils.loadAnimation(this, R.anim.background_animation);
        diamondWordAnim = AnimationUtils.loadAnimation(this, R.anim.move_right);
        runWordAnim = AnimationUtils.loadAnimation(this, R.anim.move_left);
        shardAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        image = findViewById(R.id.background);
        imageShard = findViewById(R.id.shard);
        imageShard.setVisibility(View.INVISIBLE);
        imageDiamondWord = findViewById(R.id.diamond);
        imageRunWord = findViewById(R.id.run);
        imageRunWord.setVisibility(View.INVISIBLE);
        //image.setAnimation(backgroundAnim);
        imageDiamondWord.setAnimation(diamondWordAnim);
        //imageRunWord.setAnimation(runWordAnim);

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

}


