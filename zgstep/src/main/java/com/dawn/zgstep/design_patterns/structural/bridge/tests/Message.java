package com.dawn.zgstep.design_patterns.structural.bridge.tests;

import com.dawn.zgstep.design_patterns.structural.bridge.SendAction;

/**
 * Create by rye
 * at 2020-09-10
 *
 * @description:
 */
public interface Message {
    void sendMessage(SendAction sendAction);
}
