package com.example.mymusic.repo;

import android.media.MediaPlayer;

public class MyMediaPlayer {

    private static MediaPlayer instance;

    public static MediaPlayer getInstance(){
        if(instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }

}
