package com.rye.catcher.utils;

import com.rye.base.utils.FileUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created at 2019/1/9.
 *
 * @author Zzg
 * @function:
 */
public class FileUtilsTest {
@Test
 public  void checkMethod1(){
    assertNotEquals("预期时间", FileUtils.getFileName(SDHelper.getImageFolder()));
}
}