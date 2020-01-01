package com.dawn.zgstep.demos.pattern.proxy_pattern;

public class Accuser implements IDefender {
    @Override
    public void defend() {
        System.out.println("被告严重侵犯了公民的人身自由");
    }
}
