package com.example.mymusic.music.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.example.mymusic.databinding.ViewHolderMusicBinding;
import com.example.mymusic.repo.model.AudioEntity;

import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioViewHolder> {
    private OnItemClickListener onItemClickListener;
    private ArrayList<AudioEntity> audioEntityList = new ArrayList<>();

    public AudioAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AudioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderMusicBinding binding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.view_holder_music, parent, false);
        return new AudioViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioViewHolder holder, int position) {
        holder.bind(audioEntityList.get(position));

    }

    @Override
    public int getItemCount() {
        return audioEntityList.size();
    }

    void setAudioEntityList(ArrayList<AudioEntity> audioEntityList){
        this.audioEntityList = audioEntityList;
        notifyDataSetChanged();
    }

    class AudioViewHolder extends RecyclerView.ViewHolder {
        private ViewHolderMusicBinding binding;
        public AudioViewHolder(ViewHolderMusicBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(AudioEntity audioEntity){
            binding.setAudioEntity(audioEntity);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    interface OnItemClickListener {
        public void onItemClick(int position);
    }
}
