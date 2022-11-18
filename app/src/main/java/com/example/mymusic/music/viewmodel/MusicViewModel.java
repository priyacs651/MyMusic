package com.example.mymusic.music.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.mymusic.repo.MusicRepo;
import com.example.mymusic.repo.model.AudioEntity;

import java.util.ArrayList;

public class MusicViewModel extends ViewModel {

    ArrayList<AudioEntity> audioList = new ArrayList<>();
    MusicRepo musicRepo = new MusicRepo();

    public ArrayList<AudioEntity> getAudioList(){
        return musicRepo.getAudioList();
    }
}
