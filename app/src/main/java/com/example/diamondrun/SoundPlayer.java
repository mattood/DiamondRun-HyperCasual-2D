package com.example.diamondrun;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 2;
    private static SoundPool soundPool;
    private static int glassBreakingSound;
    private static int errorSound;



    public SoundPlayer(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes = new AudioAttributes.Builder().
                    setUsage(AudioAttributes.USAGE_GAME).
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }
        else{
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        glassBreakingSound = soundPool.load(context, R.raw.glassbreaking, 1);
        errorSound = soundPool.load(context, R.raw.errorsound,1);
    }


    public void playGlassBreakingSound(){
        soundPool.play(glassBreakingSound, 1.0f, 1.0f,1, 0,1.0f);
    }

    public void playErrorSound(){
        soundPool.play(errorSound, 1.0f, 1.0f,1, 0,1.0f);
    }

    public void stopGlassBreakingSound()
    {
        if ((soundPool != null) && (glassBreakingSound != 0))
        {
            soundPool.stop(glassBreakingSound);
        }
    }


}
