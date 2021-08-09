package com.example.smartremindersapp2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class RecordingAudio extends AppCompatActivity {
    private ImageView mRecordBtn;
    private String PathName="";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    final int REQUEST_PERMISSION_CODE=1000;

    private Button btnStopRecord;
    private Button btnPlay;
    private Button btnStop;
    private Button btnRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording_audio);
        mRecordBtn=findViewById(R.id.mRecordBtn);

        if(!checkPermissionFromDevice())
            requestPermission();

        mRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionFromDevice()) {
                    PathName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                            UUID.randomUUID().toString() + "_audio_record.3gb";
                    setupMediaRecorder();
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // btn declare
                    // btn declare

                    Toast.makeText(RecordingAudio.this, "Recording ...", Toast.LENGTH_SHORT).show();
                } else
                    requestPermission();
            }
        });

        btnStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                btnStopRecord.setEnabled(false);
                btnPlay.setEnabled(true);
                btnRecord.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStop.setEnabled(true);
                btnStopRecord.setEnabled(false);
                btnRecord.setEnabled(false);
                mediaPlayer=new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(PathName);
                    mediaPlayer.prepare();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(RecordingAudio.this,"Playing ...",Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopRecord.setEnabled(false);
                btnPlay.setEnabled(true);
                btnRecord.setEnabled(true);
                btnStop.setEnabled(false);
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    setupMediaRecorder();
                }
            }
        });
    }

    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(PathName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO

        },REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result= ContextCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result=ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result== PackageManager.PERMISSION_GRANTED
                && record_audio_result==PackageManager.PERMISSION_GRANTED;
    }
}
