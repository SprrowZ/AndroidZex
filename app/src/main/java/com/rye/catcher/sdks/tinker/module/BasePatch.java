package com.rye.catcher.sdks.tinker.module;

import java.io.Serializable;

/**
 * Created by qndroid on 17/1/14.
 */

public class BasePatch implements Serializable {
    public int ecode;
    public String emsg;
    public PatchInfo data;
}
