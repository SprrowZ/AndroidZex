package com.dawn.zgstep.design_patterns.structural.proxy;

public class PlayController implements  PlayExpand {
    public void normalPlay(){
        System.out.println("均速播放");
    }

    @Override
    public void speedPlay() {
        System.out.println("PlayController speedPlay....");
    }
}
