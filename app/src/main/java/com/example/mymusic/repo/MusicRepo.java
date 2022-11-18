package com.example.mymusic.repo;

import android.database.Cursor;
import android.provider.MediaStore;

import com.example.mymusic.MyApp;
import com.example.mymusic.repo.model.AudioEntity;

import java.io.File;
import java.util.ArrayList;

public class MusicRepo {

    public ArrayList<AudioEntity> getAudioList(){
        ArrayList<AudioEntity> audioList = new ArrayList<>();
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC +" != 0";

        Cursor cursor = MyApp.instance.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext()){
            AudioEntity audioData = new AudioEntity(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if(new File(audioData.getPath()).exists())
                audioList.add(audioData);
        }
        return audioList;
    }
}
