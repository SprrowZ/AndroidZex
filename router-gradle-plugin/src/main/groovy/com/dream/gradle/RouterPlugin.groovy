package com.dream.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {
    //实现apply方法，注入插件的逻辑
    @Override
    void apply(Project project) {
        println("I\'m from RouterPlugin,apply from ${project.name}")
        //注册Extension
        project.getExtensions().create("router", RouterExtension)
        //获取Extension
        project.afterEvaluate { //配置结束，可拿到用户配置的参数
            RouterExtension extension = project["router"]
            println("用户设置的wiki路径为:${extension.wikiDir}")
        }
    }
}