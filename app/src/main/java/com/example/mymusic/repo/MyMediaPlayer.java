package com.example.mymusic.repo;

import android.media.MediaPlayer;

public class MyMediaPlayer {

    public static MediaPlayer instance;
    public static MediaPlayer getInstance(){
        if(instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }

}
