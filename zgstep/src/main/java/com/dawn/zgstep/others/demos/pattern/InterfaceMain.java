package com.dawn.zgstep.others.demos.pattern;

/**
 * Created by ZZG on 2018/3/14.
 */

public class InterfaceMain {

public void n(){
    InterfaceTest.doAct("dd", new CmdCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    });
}


}