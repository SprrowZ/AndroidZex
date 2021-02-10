package com.rye.router_compiler;


import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

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
 * 如果有多个注解处理器，在META-INF中的Processor处理器中继续添加即可。
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//要处理的注解
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

    /**
     * 编译器找到要处理的注解后，会回调此方法
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        String code = "package com.enjoy.routers;\n" +
//                "import android.app.Activity;\n" +
//                "import com.dawn.zgstep.ui.activity.ProxyActivity;\n" +
//                "import com.rye.router_api.api.IRouterLoad;\n" +
//                "import java.util.Map;\n" +
//                "\n" +
//                "public class " + moduleName + "Router implements IRouterLoad{\n" +
//                " @Override\n" +
//                "public void loadInfo(Map<String,Class<? extends Activity>> routers ) {\n" +
//                "     routers.put(\" /food/main\",ProxyActivity.class);\n" +
//                " }\n" +
//                "} \n";
//        //随机router存放目录;文件工具
//        Filer filer = processingEnv.getFiler();
//        try {
//            //将代码输入到文件中
//            JavaFileObject sourceFile = filer.createSourceFile("com.enjoy.routers." + moduleName + "Router");
//            OutputStream os = sourceFile.openOutputStream();
//            os.write(code.getBytes());
//            os.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        buildClass();
        return false;
    }


    private void buildClass() {
        //生成loadInfo参数
        ClassName typeName = ClassName.get("android.app", "Activity");
        ClassName proxyClassName = ClassName.get("com.dawn.zgstep.ui.activity","ProxyActivity");
        ParameterizedTypeName subType = ParameterizedTypeName.get(ClassName.get(Class.class),
                WildcardTypeName.subtypeOf(typeName));
        ParameterSpec paramRouters = ParameterSpec.builder(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), subType), "routers")
                .build();
        //生成loadInfo方法
        MethodSpec loadInfo = MethodSpec.methodBuilder("loadInfo")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(paramRouters)
                .addStatement("routers.put(\" /food/main\",$T.class)",proxyClassName)
                .build();
        //生成类
        TypeSpec clazz = TypeSpec.classBuilder(moduleName)
                .addMethod(loadInfo)
                .addModifiers(Modifier.PUBLIC)

                .build();
        JavaFile file = JavaFile.builder("com.enjoy.routers", clazz)
                .build();
        try {
            file.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file);
    }

}
