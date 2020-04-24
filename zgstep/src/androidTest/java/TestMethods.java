import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.jetbrains.annotations.TestOnly;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class TestMethods {
    private int ast= 1;
   @Before
    public void doSth() {
       System.out.println("-----开始之前");
       ast++;
   }
   @Test
    public void testMethod() {
       System.out.println("------测试中");
       assertEquals("数据不相等：",1,ast);
   }
   @After
    public void doSthAfter() {
       System.out.println("-----测试结束");

   }
}
