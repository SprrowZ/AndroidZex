package com.rye.catcher;


import android.util.Log;

import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String str1 = "I Giao Giao...biu!&&biu!";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.example.myappsecond", appContext.getPackageName());
    }

    @Test
    public void testMaths(){
        assertEquals(Math.rint(19.455),1.0,1.0);
    }

    @Test
    public void testFirst() {
        boolean isMatch = str1.matches("^[A-Za-z_]{1,} ");
        System.out.println("--------isMatch:"+isMatch);
        assertTrue(!isMatch);
    }
    @Test
    public void testAnd(){
        int j = 1,k=1;
        boolean variable = false;
        if (variable && ( ++k == 1)){

        }
        assertEquals(k,3);
    }

}
