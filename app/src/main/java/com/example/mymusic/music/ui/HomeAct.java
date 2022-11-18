package com.example.mymusic.music.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.mymusic.R;

public class HomeAct extends AppCompatActivity  {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        fragmentManager = getSupportFragmentManager();
        addMusicListFrag();
    }


    private void addMusicListFrag() {
        MusicListFrag musicListFrag = new MusicListFrag();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FragemtContainer,musicListFrag).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void callAudioPlayer(int position){
        AudioplayerFrag audioplayerFrag = new AudioplayerFrag();
        Bundle bundle = new Bundle();
        bundle.putInt(AudioplayerFrag.SELECTED_POSITION_EXTRA,position);
        audioplayerFrag.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.FragemtContainer,audioplayerFrag).addToBackStack(null).commit();
    }

}