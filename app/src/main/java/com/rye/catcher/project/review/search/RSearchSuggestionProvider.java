package com.rye.catcher.project.review.search;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.SearchRecentSuggestionsProvider;
import android.net.Uri;

/**
 * Create by rye
 * at 2020-09-14
 *
 * @description: 搜索建议
 */
public class RSearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    /**
     * SearchRecentSuggestions 所需参数，第一个值固定为AndroidManifest.xml里声明的值，第二个值为可选
     */
    public static final String AUTHORITY = "com.rye.catcher.project.review.others.RSearchSuggestionProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;//????这是什么？

    public RSearchSuggestionProvider() {
        //必须实现
        setupSuggestions(AUTHORITY, MODE);
    }

    /**
     * 构造SearchSuggestionProvider的uri，格式很固定
     * @param query
     * @return
     */
    public static Uri buildUri(){

        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITY);
        uriBuilder.appendPath(SearchManager.SUGGEST_URI_PATH_QUERY);
        return uriBuilder.build();
    }

}
