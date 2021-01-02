package com.dawn.zgstep.design_patterns.behavioral.template;

/**
 * Create by rye
 * at 2021/1/2
 *
 * @description:
 */
public class PgcDetailAct extends VideoDetailAct {
    @Override
    void buildParams() {
        System.out.println("构造PGC播放参数完毕...");
    }
}
