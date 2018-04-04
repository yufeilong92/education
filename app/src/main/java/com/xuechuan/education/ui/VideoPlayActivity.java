package com.xuechuan.education.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.easefun.polyvsdk.video.auxiliary.PolyvAuxiliaryVideoView;
import com.xuechuan.education.R;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: VideoPlayActivity
 * @Package com.xuechuan.education.ui
 * @Description: 视频播放界面
 * @author: L-BackPacker
 * @date: 2018/4/4 9:13
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/4
 */
public class VideoPlayActivity extends AppCompatActivity {

    private PolyvAuxiliaryVideoView mPlayVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initView();
    }

    private void initView() {
        mPlayVideo = (PolyvAuxiliaryVideoView) findViewById(R.id.playVideo);
        mPlayVideo.setVideoPath("/Sdcard/Movies/中兴威武智能手机延时摄影.mp4");
        mPlayVideo.start();
    }
}
