//package com.dawn.zgstep.player.media;
//
///**
// * Create by  [Rye]
// * <p>
// * at 2021/12/27 9:06 下午
// */
//
//import android.media.MediaExtractor;
//
///***
// * @author ： 于德海
// * time ： 2022/1/26 11:20
// * desc ：
// */
//public class VideoDecoder {
//
//    private MediaExtractor videoExtractor;
//    private FileInputStream fileInputStream;
//    private Surface mSurface;
//    private MediaCodec mVideoCodec;
//    boolean isRunning;
//    private int frameInterval;
//    private String videoUrl;
//    interface CallBack {
//        void onError(String str);a
//    }
//
//    public VideoDecoder(String videoUrl, Surface surface) {
//        if (videoUrl.isEmpty()) {
//            log("videoUrl is empty");
//            return;
//        }
//        log("videoUrl  = " + videoUrl);
//        mSurface = surface;
//        this.videoUrl = videoUrl;
//        initDecoder();
//
//    }
//
//    private void initDecoder() {
//        videoExtractor = new MediaExtractor();
//        try {
//            fileInputStream = new FileInputStream(new File(videoUrl));
//            videoExtractor.setDataSource(fileInputStream.getFD());
//            for (int i = 0; i <= videoExtractor.getTrackCount(); i++) {
//                String mime = videoExtractor.getTrackFormat(i).getString(MediaFormat.KEY_MIME);
//                log("format = " + videoExtractor.getTrackFormat(i).toString());
//                log("mime = " + mime + ";" + videoExtractor.getTrackCount());
//
//                if (mime.startsWith("video")) {
//                    int frameRate = videoExtractor.getTrackFormat(i).getInteger(MediaFormat.KEY_FRAME_RATE);
//                    if (frameRate <= 0) {
//                        frameRate = 60;
//                    }
//                    frameInterval = 1000 / frameRate ;
//                    log("frameInterval = "+frameInterval);
//                    videoExtractor.selectTrack(i);
//                    mVideoCodec = MediaCodec.createDecoderByType(mime);
//                    mVideoCodec.configure(videoExtractor.getTrackFormat(i), mSurface, null, 0);
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            log(Log.getStackTraceString(e));
//            videoExtractor = null;
//        } finally {
//            if (fileInputStream != null) {
//                try {
//                    fileInputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private void reStart(){
//        log("restart");
//        stop();
//        initDecoder();
//        start();
//    }
//
//    public void start() {
//        if (mVideoCodec == null) {
//            log(" Codec is empty");
//            return;
//        }
//        mVideoCodec.start();
//        isRunning = true;
//        new DecoderThread().start();
//
//    }
//
//    public void stop() {
//        isRunning = false;
//        try {
//            if(mVideoCodec != null) {
//                mVideoCodec.stop();
//                mVideoCodec.release();
//            }
//            if(videoExtractor != null){
//                videoExtractor.release();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private class DecoderThread extends Thread {
//
//        long lastTime = 0;
//        ByteBuffer byteBuffer;
//        MediaCodec.BufferInfo bufferInfo;
//        int inputCount;
//        int outputCount;
//        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//        @Override
//        public void run() {
//            super.run();
//            log("decoder start");
//            lastTime = System.currentTimeMillis();
//            bufferInfo = new MediaCodec.BufferInfo();
//            inputCount = 0;
//            outputCount = 0;
//            while (isRunning) {
//                try {
//                    int inputIndex = mVideoCodec.dequeueInputBuffer(10 * 1000L);
//                    if (inputIndex > 0) {
//                        byteBuffer = mVideoCodec.getInputBuffer(inputIndex);
//                        byteBuffer.clear();
//                        int size = videoExtractor.readSampleData(byteBuffer, 0);
//                        int flag = 0;
//                        if (size < 0) {
//                            flag = MediaCodec.BUFFER_FLAG_END_OF_STREAM;
//                        }
//                        log("input Count = "+inputCount );
//                        mVideoCodec.queueInputBuffer(inputIndex, 0, size, System.currentTimeMillis(), flag);
//                        if (System.currentTimeMillis() - lastTime < frameInterval) {
//                            try {
//                                Thread.sleep(frameInterval - (System.currentTimeMillis() - lastTime));
//                            } catch (Exception e) {
//                            }
//                        }
//                        inputCount++;
//                        lastTime = System.currentTimeMillis();
//                        videoExtractor.advance();
//                    }
//                    int outputIndex = mVideoCodec.dequeueOutputBuffer(bufferInfo, 1000L * 10);
//                    if(outputIndex >= 0){
//                        log("output Count = "+outputCount );
//                        mVideoCodec.releaseOutputBuffer(outputIndex,true);
//                        outputCount++;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    log("error = "+Log.getStackTraceString(e));
//                    if(isRunning) {
//                        isRunning = false;
//                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                reStart();
//                            }
//                        }, 500);
//                    }
//                }
//            }
//            log("decoder end");
//        }
//    }
//
//
//    private void log(String content) {
//        Log.e("Video", content);
//    }
//}
