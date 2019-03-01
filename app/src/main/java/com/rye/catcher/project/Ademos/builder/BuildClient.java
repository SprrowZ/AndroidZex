package com.rye.catcher.project.Ademos.builder;

/**
 * Created at 2019/3/1.
 *
 * @author Zzg
 * @function:
 */
public class BuildClient {
    public static void main(String[] args){
        Builder builder=new ConcreateBuilder();
        Director director=new Director(builder);
        Product product=director.construct();
    }
}
