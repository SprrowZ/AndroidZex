package com.dawn.zgstep.player.ui.demo;

import android.util.Log;

import com.google.android.exoplayer2.audio.TeeAudioProcessor;

import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Create by  [Rye]
 * <p>
 * at 2022/9/7 2:03 下午
 */
public class CustomAudioSink implements TeeAudioProcessor.AudioBufferSink {

    private ByteBuffer assistBuffer = ByteBuffer.allocateDirect(2055 * 2);

    @Override
    public void flush(int sampleRateHz, int channelCount, int encoding) {

    }

    @Override
    public void handleBuffer(ByteBuffer buffer) {
        Log.i("RRye", "handleBuffer");
        test2(buffer);
    }

    private void test(ByteBuffer buffer) {
        byte[] bytes = bytebuffer2ByteArray(buffer);
        int length = bytes.length;
        Log.i("RRye", "byte size:" + length);
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        queue.size();

    }

    private void test2(ByteBuffer buffer) {
        assistBuffer.put(buffer);
        while (buffer.hasRemaining()) {
            Log.e("RRye","assistBuffer:"+assistBuffer.limit());
            byte[] dst = new byte[1024];
            assistBuffer.get(dst, 0, 1024);
            assistBuffer.compact();
        }
    }

    /**
     * byteBuffer 转 byte数组
     *
     * @param buffer
     * @return
     */
    public static byte[] bytebuffer2ByteArray(ByteBuffer buffer) {
        //重置 limit 和postion 值
        buffer.flip();
        //获取buffer中有效大小
        int len = buffer.limit() - buffer.position();

        byte[] bytes = new byte[len];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = buffer.get();

        }

        return bytes;
    }

}
