import android.util.Log;

import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * Create by rye
 * at 2020-08-20
 *
 * @description: 测试java正则表达式
 */
@LargeTest
@RunWith(JUnit4.class)
public class RegexTest {
    private static final String str1 = "I Giao Giao...biu!&&biu!";
    private static final String str2 = "超级烧脑, 又脑洞大开的解谜游戏《There is No Game: Wrong Dimension》" +
            "完整实况, 感谢收看~! \\n喜欢的话可以求个三连吗\\u003e_\\u003c再次十分感谢!!\\n★微博@渗透之C菌\\n★" +
            "Instagram@roshinichi";
    private static final String str3 = "zhaozhenguo@bilibili.com";
    private static final String str4 = "13201383679@163.com";

    @Test
    public void testFirst() {
        boolean isMatch = str1.matches("^[A-Za-z_]{1,} ");
        System.out.println("--------isMatch:" + isMatch);
        Log.e("111111", String.valueOf(isMatch));
        //  assertTrue(isMatch);
    }

    @Test
    public void testSub() {
        String target = "5 packets transmitted, 5 received, 0% packet loss, time 4008ms";
        String[] chip = target.split(",");
        int send = Integer.valueOf(chip[0].substring(0, 1));
        int received = Integer.valueOf(chip[1].trim().substring(0, 1));
        if (send - received < 3) {//三次之内网关正常？或者百分之百？
            System.out.println("normal....");
        }
    }


}
