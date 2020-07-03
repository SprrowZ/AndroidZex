package com.dawn.zgstep.design_patterns.creational.simplefactory;

/**
 * Create by rye
 * at 2020-07-02
 *
 * @description: 简单工厂----工厂类
 */
public class VideoFactory {
    public static Video createVideo(String name) {
        if (name.equalsIgnoreCase("JavaVideo")) {
            return new JavaVideo();
        } else if (name.equalsIgnoreCase("PythonVideo")) {
            return new AdultVideo();
        }
        return null;
    }

    /**
     * 通过反射 解决开闭原则
     * @param c
     * @return
     */
    public  static  Video getVideo(Class c){
        Video video = null;
        try {
            video = (Video) Class.forName(c.getName()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  video;
    }
}
