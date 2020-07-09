package com.dawn.zgstep.design_patterns.creational.factorymethod;

/**
 * Create by rye
 * at 2020-07-02
 *
 * @description: 使用抽象类而不是接口，是可能此类中需要一些已知的实现，interface也有default方法了
 * 只约定规范，具体实现交给子类
 */
public abstract class VideoFactory {
    abstract Video  getVideo();
//    public static Video createVideo(String name) {
//        if (name.equalsIgnoreCase("JavaVideo")) {
//            return new JavaVideo();
//        } else if (name.equalsIgnoreCase("PythonVideo")) {
//            return new PythonVideo();
//        }
//        return null;
//    }
//
//    /**
//     * 通过反射 解决开闭原则
//     * @param c
//     * @return
//     */
//    public  static Video getVideo(Class c){
//        Video video = null;
//        try {
//            video = (Video) Class.forName(c.getName()).newInstance();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return  video;
//    }
}
