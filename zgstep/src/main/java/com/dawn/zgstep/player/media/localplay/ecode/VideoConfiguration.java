package com.dawn.zgstep.player.media.localplay.ecode;

public class VideoConfiguration {

    public static final int DEFAULT_HEIGHT = 960;
    public static final int DEFAULT_WIDTH = 540;
    public static final int DEFAULT_FPS = 30;
    public static final int DEFAULT_BPS = 6000;
    public static final int DEFAULT_IFI = 1;

    public final int height;
    public final int width;
    public final int bps;
    public final int fps;
    public final int ifi;

    private VideoConfiguration(final Builder builder) {
        height = builder.height;
        width = builder.width;
        bps = builder.mBps;
        fps = builder.fps;
        ifi = builder.ifi;
    }

    public static VideoConfiguration createDefault() {
        return new Builder().build();
    }

    public static class Builder {
        private int height = DEFAULT_HEIGHT;
        private int width = DEFAULT_WIDTH;
        private int mBps = DEFAULT_BPS;
        private int fps = DEFAULT_FPS;
        private int ifi = DEFAULT_IFI;

        public Builder setSize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setBps(int bps) {
            this.mBps = bps;
            return this;
        }

        public Builder setFps(int fps) {
            this.fps = fps;
            return this;
        }

        public Builder setIfi(int ifi) {
            this.ifi = ifi;
            return this;
        }

        public VideoConfiguration build() {
            return new VideoConfiguration(this);
        }
    }
}
