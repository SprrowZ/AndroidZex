package com.rye.catcher.project.review.search;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.SearchRecentSuggestions;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.rye.base.BaseFragment;
import com.rye.base.thread.HandlerThreads;
import com.rye.catcher.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by rye
 * at 2020-09-28
 *
 * @description:  搜索Fragment
 */
public class SearchFragment extends BaseFragment {

    private static final String TAG = "SearchFragment";
    //搜索相关
    private LinearLayout mContainer;
    private EditText mSearchView;
    private TextView mEnsureSearch;
    private TextView mRecentSearch;

    private GetSuggestionsRunnable mGetSuggestionsRunnable;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initEvent() {
        super.initEvent();
        mContainer = mRoot.findViewById(R.id.base_container);
        mSearchView = mRoot.findViewById(R.id.search_view);
        mEnsureSearch = mRoot.findViewById(R.id.ensure_search);
        mRecentSearch = mRoot.findViewById(R.id.recent_search);
        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) { //编写完成后，应该给出最近搜索值
                querySuggestionText(s.toString());
            }
        });

        mEnsureSearch.setOnClickListener(v -> {
            if (mSearchView.getText() != null) {
                saveRecentSearch(mSearchView.getText().toString());
            }
        });

        initSuggestionRunnable();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initSuggestionRunnable() {
        mGetSuggestionsRunnable = new GetSuggestionsRunnable(getContext().getContentResolver(), list -> {
            //所有的搜索历史
            StringBuilder builder = new StringBuilder();
            list.stream().forEach(searchSuggestItem -> {
                builder.append(searchSuggestItem.text)
                        .append("\n");
                Log.i(TAG, "搜索历史之：" + searchSuggestItem.text);
            });
            getActivity().runOnUiThread(() -> {
                mRecentSearch.setText(builder);
            });
        });
    }

    private void dealSearchView() {
        if (mContainer.getVisibility() == View.VISIBLE) {
            mContainer.setVisibility(View.GONE);
        } else {
            mContainer.setVisibility(View.VISIBLE);
        }

    }


    private void querySuggestionText(String text) {
        Log.i(TAG, "querySuggestionText");
        mGetSuggestionsRunnable.setQuery(text);
        HandlerThreads.getHandler(HandlerThreads.THREAD_BACKGROUND)
                .postDelayed(mGetSuggestionsRunnable, 300);
    }


    private void saveRecentSearch(String query) {
        SearchRecentSuggestions recentSuggestions = new SearchRecentSuggestions(getContext(),
                RSearchSuggestionProvider.AUTHORITY, RSearchSuggestionProvider.MODE);
        Log.i(TAG, "存储了搜索历史：" + query);
        recentSuggestions.saveRecentQuery(query, null);
    }


    private class GetSuggestionsRunnable implements Runnable {
        private ContentResolver contentResolver;
        private GetQueryHistory queryHistory;
        private String query = "";

        public GetSuggestionsRunnable(ContentResolver contentResolver, GetQueryHistory queryHistory) {
            this.contentResolver = contentResolver;
            this.queryHistory = queryHistory;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        @Override
        public void run() {
            Uri uri = RSearchSuggestionProvider.buildUri();
            Log.i(TAG, "查询的uri:" + uri.toString());
            String selection = " ?";
            String[] selArgs = new String[]{query};
            Cursor cursor = contentResolver.query(uri, null, selection, selArgs, null);
            if (cursor != null) {
                List<SearchSuggestItem> suggestItems = new ArrayList<>();
                while (cursor.moveToNext()) {
                    SearchSuggestItem suggestItem = SearchSuggestItem.fromCursorByQueryHistory(cursor);
                    suggestItems.add(suggestItem);
                }
                Log.i(TAG, "cursor 不为空，suggestItemList大小：" + suggestItems.size());
                queryHistory.get(suggestItems);
                cursor.close();
            }
        }
    }

    interface GetQueryHistory {
        void get(List<SearchSuggestItem> list);
    }
}
