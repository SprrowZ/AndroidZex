import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.PreferenceMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.greaterThan;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class) //AndroidJUnitRunner
public class TestMethods {
    private int ast = 1;

    private SharedPreferences mSharePreferences;

    @Before
    public void doSth() {
        System.out.println("-----开始之前");
        ast++;

        Context mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        mSharePreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        //assertThat--is || not ：等于或者不等于
        assertThat(InstrumentationRegistry.getInstrumentation().getProcessName(),
                not("ddss"));
        //assertThat---allOf ：需要满足所有条件
        assertThat(1, allOf(greaterThan(0), lessThan(2)));
        //assertThat---anyOf ：只需要满足任一条件
        assertThat(333, anyOf(greaterThan(1111), lessThan(334)));
        //assertThat---containsString : 包含目标字符串
        assertThat("DawnOrNight", containsString("Dawn"));
        //assertThat--endsWith/startsWith/equalTo
        assertThat("TonightIsEnd", endsWith("sEnd"));
        assertTrue(true);
        Context context=   ApplicationProvider.getApplicationContext();

        assertThat(mContext.getClass().getName(),is("12"));

    }

    @Test
    public void testMethod() {
        System.out.println("------测试中");
        assertEquals("数据不相等：", 2, ast);
    }

    @After
    public void doSthAfter() {
        System.out.println("-----测试结束");

    }
}
