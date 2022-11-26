package com.example.mymusic.music.ui;

import static com.example.mymusic.util.Utils.convertToMMSS;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymusic.R;
import com.example.mymusic.databinding.FragPlayerAudioBinding;
import com.example.mymusic.music.viewmodel.MusicViewModel;
import com.example.mymusic.repo.MyMediaPlayer;
import com.example.mymusic.repo.model.AudioEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AudioplayerFrag extends Fragment {

    private FragPlayerAudioBinding binding;
    public static String SELECTED_POSITION_EXTRA = "POS";
    private ArrayList<AudioEntity> list;
    AudioEntity audioEntity;
    private int position;
    MediaPlayer mediaPlayer = new MediaPlayer();
    int x;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(SELECTED_POSITION_EXTRA);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_player_audio, container, false);
        MusicViewModel viewModel = new ViewModelProvider(getActivity()).get(MusicViewModel.class);
        binding.tvAudioTitle.setSelected(true);
        list = viewModel.getAudioList();
        audioEntity = list.get(position);
        setListeners();
        setResourcesWithAudio();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                   binding.setMediaPlayer(mediaPlayer);
                }
                if (mediaPlayer.isPlaying()) {
                    binding.imgPausePlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    binding.imgAudioLogo.setRotation(x++);
                } else {
                    binding.imgPausePlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                    binding.imgAudioLogo.setRotation(0);
                }
                new Handler().postDelayed(this, 100);
            }

        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return binding.getRoot();
    }

    private void setListeners() {
        binding.imgPausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                else
                    mediaPlayer.start();
            }
        });
        binding.imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0)
                    return;
                position -= 1;
                audioEntity =list.get(position);
                mediaPlayer.reset();
                setResourcesWithAudio();
            }
        });
        binding.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == list.size() - 1)
                    return;
                position += 1;
                audioEntity = list.get(position);
                mediaPlayer.reset();
                setResourcesWithAudio();
            }
        });

    }
    private void setResourcesWithAudio(){
        binding.setAudioEntity(audioEntity);
        playMusic();
    }

    private void playMusic() {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(audioEntity.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            binding.seekBar.setProgress(0);
            binding.seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
