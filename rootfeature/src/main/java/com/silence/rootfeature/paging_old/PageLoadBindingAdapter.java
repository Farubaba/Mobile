package com.silence.rootfeature.paging_old;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.silence.rootfeature.logger.LogManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by violet on 15/9/6.
 */
public abstract class PageLoadBindingAdapter<T> extends PageLoadAdapter<T> {

    public final String TAG = PageLoadBindingAdapter.class.getSimpleName();

    public PageLoadBindingAdapter(Context context) {
        super(context);
    }

    public PageLoadBindingAdapter(Context context, List<T> data) {
        super(context, data);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return renderViewItem(position, convertView, parent);
    }

    public List<T> getData() {
        return mData;
    }

    public PageLoadBindingAdapter resetData(List<T> data) {
        if (data != null) {
            this.mData = data;
        } else {
            this.mData.clear();
        }
        notifyDataSetChanged();
        return this;
    }

    public PageLoadBindingAdapter addMoreData(List<T> data) {
        if (mData.size() == 0) {
            if (data != null) {
                mData.addAll(data);
            }
        } else {
            if (data != null) {
                LinkedList<T> temp = new LinkedList<>();
                for (T newData : data) {
                    boolean exist = false;
                    for (T oldData : mData) {
                        if (newData.equals(oldData)) {
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        temp.add(newData);
                    }
                }//end for
                //把增量数组加到所有列表中
                mData.addAll(temp);
            }
        }
        LogManager.getInstance().d(TAG, "PageLoadAdapter.size = " + mData.size());
        notifyDataSetChanged();
        return this;
    }

    @Override
    public int getLayoutResId() {
        return 0;
    }

    //databinding中不使用
    @Override
    protected void renderViewItem(int position, View view) {

    }

    //public abstract int getLayoutResId();

    protected abstract View renderViewItem(int position, View view, ViewGroup parent);

    public PageLoadBindingAdapter clearData() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
        return this;
    }
}
