package com.rye.router_compiler;



import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Create by rye
 * at 2021/1/23
 *
 * @description: 注解处理器 /这个时候需要在compiler模块下main包新建resource目录，
 * 创建Processor文件。其内容为该注解处理器的全类名；
 * 如果有多个注解处理器，在面的Processor处理器中继续添加即可。
 */
//只处理自己关心的注解
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.rye.router_annotation.Route")
public class RouterProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        String code ="package com.enjoy.routers;\n"+
                "\n" +
                "import android.app.Activity;\n" +
                "\n" +
                "import com.enjoy.food.FoodActivity;\n" +
                "\n" +
                "import java.util.Map;\n"+
                "\n"+
                "public class FoodRouter implements IRouterLoad{\n"+
                " @Override\n"+
                "public void loadInfo(Map<String,Class<? extends Activity>> routers ) {\n"+
                "routers.put(\" /food/main\",FoodActivity.class);\n" +
                "\n" +
                " }\n" +
                "} \n";
         //随机router存放目录;文件工具
        Filer filer = processingEnv.getFiler();
        try {
            //将代码输入到文件中
            JavaFileObject sourceFile = filer.createSourceFile("com.enjoy.routers.FoodRouter");
            OutputStream os = sourceFile.openOutputStream();
            os.write(code.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("12222222223333333333");

        return false;
    }
}
