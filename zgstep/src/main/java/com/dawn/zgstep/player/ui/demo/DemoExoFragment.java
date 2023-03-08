package com.dawn.zgstep.player.ui.demo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.dawn.zgstep.R;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.DefaultAudioSink;
import com.google.android.exoplayer2.audio.TeeAudioProcessor;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class DemoExoFragment extends Fragment {
   private SimpleExoPlayer player;
   private SurfaceView surfaceView;
   private View mRoot;
   private static  final String HLS_PATH = "https://1688living.alicdn.com/mediaplatform/90ed0fdb-6583-4892-a8e1-6dbc52b540e4.m3u8";
    public DemoExoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRoot = inflater.inflate(R.layout.fragment_demo_exo, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
          surfaceView = mRoot.findViewById(R.id.surface);
//          test1();
//        test2();
        test3();
    }

    private void test1() {
        player = new SimpleExoPlayer.Builder(getContext()).build();
        MediaItem item = MediaItem.fromUri("https://1688living.alicdn.com/mediaplatform/90ed0fdb-6583-4892-a8e1-6dbc52b540e4.m3u8");
//        MediaItem item = MediaItem.fromUri("https://cloud.video.taobao.com/play/u/933701333/p/2/e/6/t/1/360705200313.mp4");
        player.setMediaItem(item);
        player.setVideoSurfaceHolder(surfaceView.getHolder());
        player.setRepeatMode(Player.REPEAT_MODE_ONE);
        player.prepare();
        player.play();
    }

    private void test2() {
//        TeeAudioProcessor teeAudioProcessor = new TeeAudioProcessor( new CustomAudioSink());
//        TeeAudioProcessor[] audioArray = new TeeAudioProcessor[]{teeAudioProcessor};
//
//        DefaultAudioSink audioSink =  new DefaultAudioSink.Builder()
//                .setAudioCapabilities(AudioCapabilities.DEFAULT_AUDIO_CAPABILITIES)
//                .setAudioProcessors(audioArray)
//                 .build();
//        DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(getContext()){
//            @Nullable
//            @Override
//            protected AudioSink buildAudioSink(Context context, boolean enableFloatOutput, boolean enableAudioTrackPlaybackParams, boolean enableOffload) {
//                return audioSink;
//            }
//        };
//
//        ExoPlayer exoPlayer = new ExoPlayer.Builder(getContext(),defaultRenderersFactory).build();
//        exoPlayer.setMediaItem(MediaItem.fromUri(HLS_PATH));
//        exoPlayer.setVideoSurfaceHolder(surfaceView.getHolder());
//        exoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
//        exoPlayer.prepare();
//        exoPlayer.play();
    }

    private void test3() {
        TeeAudioProcessor teeAudioProcessor = new TeeAudioProcessor(new CustomAudioSink());
        TeeAudioProcessor  teeAudioProcessor1 = new TeeAudioProcessor(new CustomAudioSink());

        TeeAudioProcessor[] audioArray = new TeeAudioProcessor[]{teeAudioProcessor,teeAudioProcessor1};

        DefaultAudioSink audioSink1 = new DefaultAudioSink(AudioCapabilities.DEFAULT_AUDIO_CAPABILITIES,audioArray);

        RenderersFactory renderersFactory = new DefaultRenderersFactory(getContext()){
            @Nullable
            @Override
            protected AudioSink buildAudioSink(Context context, boolean enableFloatOutput, boolean enableAudioTrackPlaybackParams, boolean enableOffload) {
                return audioSink1;
            }
        };

        SimpleExoPlayer simpleExoPlayer = new SimpleExoPlayer.Builder(getContext(),renderersFactory)
                .build();
        simpleExoPlayer.setMediaItem(MediaItem.fromUri(HLS_PATH));
        simpleExoPlayer.setVideoSurfaceHolder(surfaceView.getHolder());
        simpleExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();
    }
}