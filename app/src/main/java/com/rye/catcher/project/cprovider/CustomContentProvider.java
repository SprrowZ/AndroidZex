package com.rye.catcher.project.cprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Create by rye
 * at 2020-09-25
 *
 * @description: ContentProvider 测试
 */
public class CustomContentProvider extends ContentProvider {

    private SQLiteDatabase db;

    private static final String TAG = "RyeProvider";
    private static final String AUTHORITY_NAME = "com.rye.catcher"; //最好是用包名
    private static final String UGC_PATH = "ugc";//ugc表 路径
    private static final String PGC_PATH = "pgc";//pgc表 路径
    private static final String BASE_CONFIGURE_PATH = "baseConfigurePath"; //基础配置

    private static final int UGC_CODE = 0;
    private static final int PGC_CODE = 1;
    private static final int BASE_CONFIGURE_CODE = 2;

    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY_NAME, UGC_PATH, UGC_CODE);
        matcher.addURI(AUTHORITY_NAME, PGC_PATH, PGC_CODE);
        matcher.addURI(AUTHORITY_NAME, BASE_CONFIGURE_PATH, BASE_CONFIGURE_CODE);
    }


    @Override
    public boolean onCreate() { //onCreate的时候主要就是创建数据库
        DBHelper helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        int match = matcher.match(uri);
        Log.e(TAG, "query,match:" + match + ",projectionSize:" + projection.length);
        switch (match) {
            case UGC_CODE:
                return db.query(DBHelper.TABLE_PGC, projection, selection, selectionArgs,
                        null, null, sortOrder);
            case PGC_CODE:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = matcher.match(uri);
        Log.e(TAG, "insertBySqlite,match:" + match + ",name:" + values.get(DBHelper.TABLE_PGC_NAME));
        switch (match) {
            case UGC_CODE:
                long id = db.insert(DBHelper.TABLE_PGC, null, values);
                //将原有的uri跟id进行拼接后 形成新的api
                return ContentUris.withAppendedId(uri, id);
            case PGC_CODE:
                break;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
