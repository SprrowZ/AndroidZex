package com.dawn.zgstep.design_patterns.creational.factorymethod;

/**
 * Create by rye
 * at 2020-07-07
 *
 * @description:
 */
public class PythonFactory extends VideoFactory {
    @Override
    Video getVideo() {
        return new PythonVideo();
    }
}
