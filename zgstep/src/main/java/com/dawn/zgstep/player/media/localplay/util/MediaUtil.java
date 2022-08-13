package com.dawn.zgstep.player.media.localplay.util;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Build;
import android.util.Range;


import com.dawn.zgstep.player.media.localplay.ecode.AudioConfiguration;
import com.dawn.zgstep.player.media.localplay.ecode.VideoConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MediaUtil {

    public static MediaExtractor createExtractor(String path) throws IOException {
        MediaExtractor extractor;
//        File inputFile = new File(path);   // must be an absolute path
//        if (!inputFile.canRead()) {
//            throw new FileNotFoundException("Unable to read " + inputFile);
//        }
        extractor = new MediaExtractor();
        extractor.setDataSource(path);
        return extractor;
    }

    public static int getAndSelectVideoTrackIndex(MediaExtractor extractor) {
        try {
            for (int index = 0; index < extractor.getTrackCount(); ++index) {
                if (isVideoFormat(extractor.getTrackFormat(index))) {
                    extractor.selectTrack(index);
                    return index;
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public static boolean isVideoFormat(MediaFormat format) {
        return getMimeTypeFor(format).startsWith("video/");
    }

    public static String getMimeTypeFor(MediaFormat format) {
        return format.getString(MediaFormat.KEY_MIME);
    }

    public static int getAndSelectAudioTrackIndex(MediaExtractor extractor) {
        for (int index = 0; index < extractor.getTrackCount(); ++index) {
            if (isAudioFormat(extractor.getTrackFormat(index))) {
                extractor.selectTrack(index);
                return index;
            }
        }
        return -1;
    }

    public static boolean isAudioFormat(MediaFormat format) {
        return getMimeTypeFor(format).startsWith("audio/");
    }


    /**
     * 创建Video MediaCodec
     */

    public static final String VIDEO_MIME = "video/avc";

    public static MediaCodec getVideoMediaCodec(VideoConfiguration videoConfiguration) throws IOException {
        int videoWidth = getVideoSize(videoConfiguration.width);
        int videoHeight = getVideoSize(videoConfiguration.height);
        MediaFormat format = MediaFormat.createVideoFormat(VIDEO_MIME, videoWidth, videoHeight);
        format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
        format.setInteger(MediaFormat.KEY_BIT_RATE, videoConfiguration.bps * 1024);
        format.setInteger(MediaFormat.KEY_FRAME_RATE, videoConfiguration.fps);
        format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, videoConfiguration.ifi);
        setBitrateMode(format);
        MediaCodec mediaCodec = MediaCodec.createEncoderByType(VIDEO_MIME);
        mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        return mediaCodec;
    }

    public static int getVideoSize(int size) {
        int multiple = (int) Math.ceil(size/16.0);
        return multiple*16;
    }

    public static void setBitrateMode(MediaFormat format) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        MediaCodecInfo mediaCodecInfo = selectCodecInfo(VIDEO_MIME);
        if(mediaCodecInfo == null) {
            return;
        }
        MediaCodecInfo.CodecCapabilities codecCapabilities = mediaCodecInfo.getCapabilitiesForType(VIDEO_MIME);
        if(codecCapabilities == null) {
            return;
        }
        MediaCodecInfo.EncoderCapabilities capabilities = codecCapabilities.getEncoderCapabilities();
        if(capabilities == null) {
            return;
        }
        if (capabilities.isBitrateModeSupported(MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_CQ)) {
            format.setInteger(MediaFormat.KEY_BITRATE_MODE, MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_CQ);
        } else if (capabilities.isBitrateModeSupported(MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_VBR)) {
            format.setInteger(MediaFormat.KEY_BITRATE_MODE, MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_VBR);
        } else if (capabilities.isBitrateModeSupported(MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_CBR)) {
            format.setInteger(MediaFormat.KEY_BITRATE_MODE, MediaCodecInfo.EncoderCapabilities.BITRATE_MODE_CBR);
        }
        Range<Integer> complexityRange = capabilities.getComplexityRange();
        if (complexityRange != null) {
            format.setInteger(MediaFormat.KEY_COMPLEXITY, complexityRange.getLower());
        }
    }

    public static MediaCodecInfo selectCodecInfo(String mimeType) {
        int numCodecs = MediaCodecList.getCodecCount();
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfo.isEncoder()) {
                continue;
            }
            String[] types = codecInfo.getSupportedTypes();
            for (int j = 0; j < types.length; j++) {
                if (types[j].equalsIgnoreCase(mimeType)) {
                    return codecInfo;
                }
            }
        }
        return null;
    }

    /**
     * 创建Audio MediaCodec
     */

    public static final String AUDIO_MIME = "audio/mp4a-latm";

    public static MediaCodec getAudioMediaCodec(AudioConfiguration configuration) throws IOException {
        int channelCount = (configuration.channel == AudioFormat.CHANNEL_IN_STEREO ? 2 : 1);
        int frequency = configuration.frequency;
        MediaFormat format = MediaFormat.createAudioFormat(AUDIO_MIME, configuration.frequency, channelCount);
        format.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
        format.setInteger(MediaFormat.KEY_BIT_RATE, configuration.bps * 1024);
        format.setInteger(MediaFormat.KEY_SAMPLE_RATE, frequency);
        int maxInputSize = getRecordBufferSize(configuration)*10;
        format.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, maxInputSize);
        format.setInteger(MediaFormat.KEY_CHANNEL_COUNT, channelCount);
        MediaCodec mediaCodec = MediaCodec.createEncoderByType(AUDIO_MIME);
        mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        return mediaCodec;
    }

    public static int getRecordBufferSize(AudioConfiguration audioConfiguration) {
        return AudioRecord.getMinBufferSize(audioConfiguration.frequency,
                audioConfiguration.channel, audioConfiguration.encoding);
    }

}
