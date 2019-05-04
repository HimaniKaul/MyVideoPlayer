package com.example.videoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

    VideoView videoview;
    String url;
    MediaController mediaController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity_layout);
        getWidgets();
        playVideo();
    }

    private void playVideo() {
        url=getIntent().getStringExtra("video");
        videoview.setVideoPath(url);
        videoview.start();
        videoview.setMediaController(mediaController);
        mediaController.setAnchorView(videoview);
    }

    private void getWidgets() {
        videoview=findViewById(R.id.videoView);
        mediaController=new MediaController(this);
    }
}
