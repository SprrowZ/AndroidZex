package com.dawn.zgstep.player.media.localplay.ecode;

import android.media.AudioFormat;
import android.media.MediaRecorder;

public class AudioConfiguration {

    public static final int DEFAULT_AUDIO_FREQUENCY = 44100;
    public static final int DEFAULT_AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    public static final int DEFAULT_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    public static final int DEFAULT_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    public static final int DEFAULT_AUDIO_BPS = 128;

    public final int frequency;
    public final int encoding;
    public final int channel;
    public final int source;
    public final int bps;

    private AudioConfiguration(final Builder builder) {
        frequency = builder.frequency;
        encoding = builder.encoding;
        channel = builder.channel;
        source = builder.source;
        bps = builder.bps;
    }

    public static AudioConfiguration createDefault() {
        return new Builder().build();
    }

    public static class Builder {
        private int frequency = DEFAULT_AUDIO_FREQUENCY;
        private int encoding = DEFAULT_AUDIO_ENCODING;
        private int channel = DEFAULT_AUDIO_CHANNEL;
        private int source = DEFAULT_AUDIO_SOURCE;
        private int bps = DEFAULT_AUDIO_BPS;

        public Builder setFrequency(int frequency) {
            this.frequency = frequency;
            return this;
        }

        public Builder setChannel(int channel) {
            this.channel = channel;
            return this;
        }

        public Builder setSource(int source) {
            this.source = source;
            return this;
        }

        public Builder setBps(int bps) {
            this.bps = bps;
            return this;
        }

        public AudioConfiguration build() {
            return new AudioConfiguration(this);
        }
    }

}
