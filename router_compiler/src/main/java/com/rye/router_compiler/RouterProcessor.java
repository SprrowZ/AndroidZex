package com.rye.router_compiler;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
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
@SupportedOptions("moduleName")
public class RouterProcessor extends AbstractProcessor {
    private String moduleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        Map<String, String> options = processingEnvironment.getOptions();
        moduleName = options.get("moduleName");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        String code = "package com.enjoy.routers;\n" +
                "import android.app.Activity;\n" +
                "import com.dawn.zgstep.ui.activity.ProxyActivity;\n" +
                "import com.rye.router_api.api.IRouterLoad;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "public class " + moduleName + "Router implements IRouterLoad{\n" +
                " @Override\n" +
                "public void loadInfo(Map<String,Class<? extends Activity>> routers ) {\n" +
                "     routers.put(\" /food/main\",ProxyActivity.class);\n" +
                " }\n" +
                "} \n";
        //随机router存放目录;文件工具
        Filer filer = processingEnv.getFiler();
        try {
            //将代码输入到文件中
            JavaFileObject sourceFile = filer.createSourceFile("com.enjoy.routers." + moduleName + "Router");
            OutputStream os = sourceFile.openOutputStream();
            os.write(code.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void buildClass(){

        ClassName map = ClassName.get("java.util", "Map");


        ParameterSpec.builder(  ParameterizedTypeName.get(Map.class,String))


        MethodSpec.methodBuilder("loadInfo")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter( )

    }

}
