package com.example.myappsecond.project.dao;



import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;

/**
 * Created by Administrator on 2016/5/9 0009.
 */
public class MatchUtils {
    public static String tokenize1(String str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            sb.append(c);
            if (c > 256) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    public static String tokenize(String str) {

        StringBuffer sb = new StringBuffer();
        //创建分词对象
        /**
         * 最细粒度和智能分词模式
         * 如果构造函数参数为false，那么使用最细粒度分词。
         * 如果构造函数参数为true，那么使用智能分词。
         * 默认为最细粒度分词
         */
        Analyzer anal=new IKAnalyzer();
        StringReader reader=new StringReader(str);
        //分词
        TokenStream ts=anal.tokenStream("", reader);
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
        try {
            //遍历分词数据
            while(ts.incrementToken()){
                sb.append(term.toString()+" ");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        reader.close();

        return sb.toString();
    }

    public static String tokenizeBySmart(String str) {

        StringBuffer sb = new StringBuffer();
        //创建分词对象
        Analyzer anal=new IKAnalyzer();
        StringReader reader=new StringReader(str);
        //分词
        TokenStream ts=anal.tokenStream("", reader);
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
        try {
            //遍历分词数据
            while(ts.incrementToken()){
                sb.append(term.toString()+" ");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        reader.close();

        return sb.toString();
    }
}
