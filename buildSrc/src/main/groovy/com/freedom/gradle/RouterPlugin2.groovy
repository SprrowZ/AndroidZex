package com.freedom.gradle

import com.android.build.api.transform.Transform
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin

import groovy.json.JsonSlurper
import org.gradle.api.Plugin
import org.gradle.api.Project


class RouterPlugin2 implements Plugin<Project> {
    //实现apply方法，注入插件的逻辑
    @Override
    void apply(Project project) {
//        println("I\'m from RouterPlugin,apply from ${project.name}")
        registerTransform(project)
        injectGradleParams(project)
        cleanPreviousMappingFiles(project)
        registerExtensions(project)
    }

    /**
     * 注册Transform
     * @param project
     */
    private void registerTransform(Project project) {
        //有则说明是com.android.application的子工程，一般就是主工程；其他模块的是apply plugin: 'com.android.library'
        if (project.plugins.hasPlugin(AppPlugin)) { //目前发现只能在主工程注册
            AppExtension baseExtension = project.extensions.getByType(AppExtension)
            Transform transform = new RouterMappingTransform()
            baseExtension.registerTransform(transform)
        }
    }

    /**
     * 自动插入参数到build.gradle文件中去
     * @param project
     */
    private void injectGradleParams(Project project) {
        //1.自动帮助用户传递路径参数到注解处理器中（不需要在build.gradle中手动指定）
/**
 *         kapt { //给注解处理器传递参数 对应java defaultConfig 中的 javaCompileOptions.annotationProcessorOptions
 *             arguments {*                 arg("moduleName", project.getName())
 *                 arg("root_project_dir", rootProject.projectDir.absolutePath)
 *}*}*/
        if (project.extensions.findByName("kapt") != null) {
            project.extensions.findByName("kapt").arguments {
                arg("root_project_dir", project.rootProject.projectDir.absolutePath)
            }
        }
        println("~~~~~~~~~~~~~~~~~~~~~~project.extentions Type:${project.extensions.findByName("kapt").class}")
    }

    /**
     * 自动清理上次构建生成产物
     * @param project
     */
    private void cleanPreviousMappingFiles(Project project) {
        //2.实现旧的构建产物的自动清理(mapping文件要及时更新)
        project.clean.doFirst {
            File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
            if (routerMappingDir.exists()) {
                routerMappingDir.deleteDir()
            }
        }

    }

    /**
     * 注册扩展属性到build.gradle中去
     * @param project
     */
    private void registerExtensions(Project project) {
        if (!project.plugins.hasPlugin(AppPlugin)){ //在主工程中生成一次汇总文件就行，不要重复执行
            return
        }
        //注册Extension
        project.getExtensions().create("router", RouterExtension)
        //获取Extension
        project.afterEvaluate { //配置结束，可拿到用户配置的参数
            RouterExtension extension = project["router"]
            println("用户设置的wiki路径为:${extension.wikiDir}")
            buildMarkDown(project, extension.wikiDir)
        }
    }

    //3.在javac任务[compileDebugJavaWithJavac]后汇总生成文档(需要在配置结束后)
    private void buildMarkDown(Project project, String wikiDir) {
        project.tasks.findAll { task ->
            task.name.startsWith('compile') && task.name.endsWith('JavaWithJavac')
        }.each { task ->
            task.doLast {
                File routerMappingDir = new File(project.rootProject.projectDir, "router_mapping")
                if (!routerMappingDir.exists()) {
                    return
                }
                File[] allChildFiles = routerMappingDir.listFiles()

                if (allChildFiles.length < 1) {
                    return
                }
                StringBuilder markDownBuilder = new StringBuilder()
                markDownBuilder.append("# 页面文档\n\n")

                allChildFiles.each { child ->
                    if (child.name.endsWith(".json")) {
                        JsonSlurper jsonSlurper = new JsonSlurper()
                        def content = jsonSlurper.parse(child)
                        content.each { innerContent ->
                            def url = innerContent['url']
                            def description = innerContent['description']
                            def realPath = innerContent['realPath']
                            markDownBuilder.append("## $description \n")
                            markDownBuilder.append("-url: $url \n")
                            markDownBuilder.append("- realPath: $realPath \n\n")
                        }
                    }
                }

                File wikiFileDir = new File(wikiDir)
                if (!wikiFileDir.exists()) {
                    wikiFileDir.mkdir()
                }
                File wikiFile = new File(wikiFileDir, "页面文档.md")
                if (wikiFile.exists()) {
                    wikiFile.delete()
                }
                wikiFile.write(markDownBuilder.toString())
            }
        }
    }
}
