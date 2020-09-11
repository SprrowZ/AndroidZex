import android.net.wifi.WifiManager;

import com.dawn.zgstep.design_patterns.structural.flyweight.BookFactory;

import org.junit.Test;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by rye
 * at 2020-08-20
 *
 * @description:
 */
public class RegexJavaTest {
    private static final String str1 = "IB Giao Giao...biu! && bia!11";
    private static final String str2 = "超级烧脑, 又脑洞大开的解谜游戏《There is No Game: Wrong Dimension》" +
            "完整实况, 感谢收看~! \\n喜欢的话可以求个三连吗\\u003e_\\u003c再次十分感谢!!\\n★微博@渗透之C菌\\n★" +
            "Instagram@roshinichi";
    private static final String str3 = "zhaozhenguo@bilibili.com";
    private static final String str4 = "13201383679@163.com";


    public static void testFirst() {

        Pattern pattern = Pattern.compile("[a-zA-Z_]{2,}u|b!");
        Matcher matcher  = pattern.matcher(str1);
        while (matcher.find()){ //匹配到
            System.out.println(matcher.group());
        }

    }

    public static void main(String[] args) {
       // testFirst();
        testFlyWeight();
    }

    public static void testFlyWeight(){
        String[] bookNames =new String[]{"《时间简史》","《物种起源》","《麦田守望者》"};
        for (int i =0;i<20;i++){
            int pos = new Random().nextInt(3);
            BookFactory.getBook(bookNames[pos]);
        }
    }
}
