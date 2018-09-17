package com.example.myappsecond.project.dao;
import com.example.myappsecond.utils.ExtraUtil.Bean;
import java.util.List;

/**
 * Created by wangyanjing on 2016/9/8.
 */
public interface FullSearchInter {
    List<Bean> searchByMatch(String value, int num);
    List<Bean> searchByLike(String value, int num);
}
