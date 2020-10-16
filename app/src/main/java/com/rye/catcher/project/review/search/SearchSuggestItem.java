package com.rye.catcher.project.review.search;

import android.app.SearchManager;
import android.database.Cursor;

/**
 * Create by rye
 * at 2020-09-15
 *
 * @description:
 */
public class SearchSuggestItem {
    public String query;
    public String value;
    public String url;
    public String type;
    public String id;
    public String text;

    /**
     * 通过游标中 查到的数据 构造数据类
     * @param cursor
     * @return
     */
    public static SearchSuggestItem fromCursor(Cursor cursor){
        if (cursor!=null){
            SearchSuggestItem item = new SearchSuggestItem();
            item.query = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_QUERY));
            item.value = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
            item.url = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2_URL));
            item.type = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_2));
            return item;
        }else{
            return null;
        }
    }

    public static SearchSuggestItem fromCursorByQueryHistory(Cursor cursor){
        if (cursor!=null){
            SearchSuggestItem item = new SearchSuggestItem();
            item.id = cursor.getString(cursor.getColumnIndex("_id"));
            item.text = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_QUERY));
            return item;
        }else {
            return null;
        }
    }
}
