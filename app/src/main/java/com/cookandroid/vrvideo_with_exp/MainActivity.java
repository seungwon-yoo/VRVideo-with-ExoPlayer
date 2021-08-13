package com.cookandroid.vrvideo_with_exp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer.Builder;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource.Factory;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import com.google.android.exoplayer2.C;

public final class MainActivity extends AppCompatActivity {
    private SimpleExoPlayer player;
    private PlayerView playerView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        playerView = (PlayerView)findViewById(R.id.player_view);
        ((SphericalGLSurfaceView)playerView.getVideoSurfaceView()).setDefaultStereoMode(C.STEREO_MODE_MONO);
    }

    protected void onStart() {
        super.onStart();
        if (VERSION.SDK_INT > 23) {
            initializePlayer();
        }

    }

    protected void onResume() {
        super.onResume();
        if (VERSION.SDK_INT <= 23 || this.player == null) {
            initializePlayer();
        }

    }

    protected void onPause() {
        super.onPause();
        if (VERSION.SDK_INT <= 23) {
            releasePlayer();
        }

    }

    protected void onStop() {
        super.onStop();
        if (VERSION.SDK_INT > 23) {
            releasePlayer();
        }

    }

    private final MediaSource buildMediaSource(Uri uri) {
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory((Context)this, "javiermarsicano-VR-app");
        ProgressiveMediaSource mediaSource = (new Factory((com.google.android.exoplayer2.upstream.DataSource.Factory)dataSourceFactory)).createMediaSource(uri);
        return mediaSource;
    }

    private final void initializePlayer() {
        this.player = (new Builder((Context)this)).build();
        Uri uri = Uri.parse("http://192.168.0.20/Doi.mp4");
        MediaSource mediaSource = this.buildMediaSource(uri);
        player.prepare(mediaSource);

        playerView.setPlayer((Player)this.player);
        playerView.onResume();
    }

    private final void releasePlayer() {
        playerView.onPause();
        player.release();
    }
}
