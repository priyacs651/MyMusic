package com.example.mymusic.music.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mymusic.R;
import com.example.mymusic.databinding.FragListMusicBinding;
import com.example.mymusic.music.viewmodel.MusicViewModel;

import java.util.Objects;

public class MusicListFrag extends Fragment implements AudioAdapter.OnItemClickListener {

    private FragListMusicBinding binding;
    private int REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 1;
    private MusicViewModel viewModel;
    private AudioAdapter audioAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_list_music, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        viewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
        if(checkPermission()){
            requestPermission();
        } else {
            getAudioList();
        }
    }

    private void initAdapter() {
        audioAdapter = new AudioAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(audioAdapter);
    }

    boolean checkPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == -1;
    }

    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){

            Toast.makeText(requireContext(),"READ PERMISSION IS REQUIRED,PLEASE ALLOW FROM SETTINGS",Toast.LENGTH_SHORT).show();
        }else
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSIONS_CODE_WRITE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_CODE_WRITE_STORAGE) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    getAudioList();
            }
            else{
                requestPermission();
            }
        }
    }

    private void getAudioList() {
        audioAdapter.setAudioEntityList(viewModel.getAudioList());
    }

    @Override
    public void onItemClick(int position) {
        ((HomeAct) getActivity()).callAudioPlayer(position);
    }
}
