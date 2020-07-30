package com.dawn.zgstep.design_patterns.structural.proxy;

public class PlayDelegate implements PlayExpand {
   private PlayController mPlayController;
    public PlayDelegate(PlayController playController){
        this.mPlayController = playController;
    }
    @Override
    public void speedPlay() {
        //---扩展后的逻辑处理
        //----
        mPlayController.speedPlay();
        //---逻辑处理
        System.out.println("PlayDelegate speedPlay...");
    }
}
