package com.example.myappsecond.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ArrayAdapter基类，copy from ArrayAdapter of android System
 * @author ch_zhq
 */
public abstract class BaseArrayAdapter<T> extends BaseAdapter implements Filterable {
    private static final String TAG = BaseArrayAdapter.class.getSimpleName();

    /**
     * 展示的数据列表
     */
    private List<T> mObjects;
    private final Object mLock = new Object();
    private boolean mNotifyOnChange = true;

    protected Activity activity;

    /**
     * 原始的、完整的数据列表
     */
    private ArrayList<T> mOriginalValues;
    private ArrayFilter mFilter;
    private IService service;

    protected int mResource;
    protected LayoutInflater mInflater;

    /**
     * 构造器
     */
    public BaseArrayAdapter(Activity activity, int resource, List<T> objects) {
        init(activity, resource, objects);
    }

    /**
     * 添加Item
     */
    public void add(T object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Log.d(TAG, "add:mOriginalValues");
                mOriginalValues.add(object);
            } else {
                Log.d(TAG, "add:mObjects");
                mObjects.add(object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * 添加集合
     */
    public void addAll(Collection<? extends T> collection) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Log.d(TAG, "addAll:mOriginalValues");
                mOriginalValues.addAll(collection);
            } else {
                Log.d(TAG, "addAll:mObjects");
                mObjects.addAll(collection);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * 添加数组
     */
    public void addAll(T ... items) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Log.d(TAG, "addAll:mOriginalValues");
                Collections.addAll(mOriginalValues, items);
            } else {
                Log.d(TAG, "addAll:mObjects");
                Collections.addAll(mObjects, items);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * 插入Item
     */
    public void insert(T object, int index) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Log.d(TAG, "insert:mOriginalValues");
                mOriginalValues.add(index, object);
            } else {
                Log.d(TAG, "insert:mObjects");
                mObjects.add(index, object);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * 移除Item
     */
    public void remove(T object) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Log.d(TAG, "remove:mOriginalValues");
                mOriginalValues.remove(object);
            } 
            mObjects.remove(object);
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * 清除列表
     */
    public void clear() {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Log.d(TAG, "clear:mOriginalValues");
                mOriginalValues.clear();
            } else {
                Log.d(TAG, "clear:mObjects");
                mObjects.clear();
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * 排序
     */
    public void sort(Comparator<? super T> comparator) {
        synchronized (mLock) {
            if (mOriginalValues != null) {
                Log.d(TAG, "sort:mOriginalValues");
                Collections.sort(mOriginalValues, comparator);
            } else {
                Log.d(TAG, "sort:mObjects");
                Collections.sort(mObjects, comparator);
            }
        }
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    /**
     * 刷新列表
     */
    @Override
    public void notifyDataSetChanged() {
        Log.d(TAG, "notifyDataSetChanged");
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

    /**
     * 设置mNotifyOnChange
     */
    public void setNotifyOnChange(boolean notifyOnChange) {
        Log.d(TAG, "setNotifyOnChange:" + notifyOnChange);
        mNotifyOnChange = notifyOnChange;
    }

    private void init(Activity activity, int resource, List<T> objects) {
        this.activity = activity;
        mInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mObjects = objects;
        mResource = resource;
    }

    /**
     * 取得上下文
     */
    public Context getContext() {
        return activity;
    }

    /**
     * 取得数量
     */
    public int getCount() {
        return mObjects.size();
    }

    /**
     * 取得Item
     */
    public T getItem(int position) {
        return mObjects.get(position);
    }

    /**
     * 根据Item取得位置
     */
    public int getPosition(T item) {
        return mObjects.indexOf(item);
    }

    /**
     * 取得ItemId
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * 取得View
     */
    public abstract View getView(int position, View convertView, ViewGroup parent);

    /**
     * 取得过滤器
     */
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    /**
     * 设置过滤器业务逻辑
     */
    public void setService(IService service) {
        this.service = service;
    }

    public interface IService<T> {
        boolean add(T item, String query);
    }

    /**
     * 取得正在展示的数据列表
     */
    protected List<T> getDisplayList() {
        synchronized (mLock) {
            return new ArrayList<>(mObjects);
        }
    }

    /**
     * 过滤器实现类
     */
    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            Log.d(TAG, "performFiltering:" + prefix);
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(mObjects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<T> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                ArrayList<T> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final ArrayList<T> newValues = new ArrayList<>();

                for (int i = 0; i < values.size(); i++) {
                    final T value = values.get(i);
                    if (service != null) {
                        if (service.add(value, prefix.toString())) {
                            newValues.add(value);
                        }
                    }

                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Log.d(TAG, "publishResults:" + constraint);
//            mObjects = (List<T>) results.values;
            //bug修改：mObjects是展示的数据列表，如果直接等于就改变了此容器的地址，再进行notifyDataSetChanged时则不起作用
            List<T> values = (List<T>) results.values;
            mObjects.clear();
            mObjects.addAll(values);
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
