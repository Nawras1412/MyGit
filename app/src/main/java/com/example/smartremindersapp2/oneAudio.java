//package com.example.smartremindersapp2;
//
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.IOException;
//
//public class oneAudio extends AppCompatActivity {
//    private ImageView playAudio;
//    private ImageView deleteAudio;
//    private SeekBar seekBar;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.one_audio);
//
//        playAudio=findViewById(R.id.playAudio);
//        deleteAudio=findViewById(R.id.delete_audio);
//        seekBar=findViewById(R.id.seekBar);
//
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if(fromUser)
//                    mediaPlayer.seekTo(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        playAudio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(play)
//                    play=false;
//                else
//                    play=true;
//                if(play) {
//                    mediaPlayer = new MediaPlayer();
//                    try {
//                        mediaPlayer.setDataSource(path);
//                        mediaPlayer.prepare();
//                        seekBar.setMax(mediaPlayer.getDuration());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    mediaPlayer.start();
//                    addReminder.UpdateSeekBar updateSeekBar = new addReminder.UpdateSeekBar();
//                    handler.post(updateSeekBar);
//                }else{
//                    if(mediaPlayer!=null){
//                        mediaPlayer.stop();
//                        mediaPlayer.release();
//                        setupMediaRecorder();
//                    }
//                }
//            }
//        });
//
//
//    }
//}
