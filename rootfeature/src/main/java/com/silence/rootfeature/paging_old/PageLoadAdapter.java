package com.silence.rootfeature.paging_old;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.silence.rootfeature.logger.LogManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by violet on 15/9/6.
 */
public abstract class PageLoadAdapter<T> extends BaseAdapter {

    public final String TAG = PageLoadAdapter.class.getSimpleName();
    public static final int INVALIDE_RES_ID = 0;
    protected Context mContext;
    protected List<T> mData = new LinkedList<>();
    protected PageLoadParam pageLoadParam;
    protected ListView listView;

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public PageLoadAdapter(Context context) {
        this.mContext = context;
    }

    public PageLoadAdapter(Context context, List<T> data) {
        this.mContext = context;
        if (data != null) {
            this.mData = data;
        } else if (this.mData != null) {
            this.mData.clear();
        }
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
        LogManager.getInstance().d(TAG, "getLayoutResId() = " + getLayoutResId());
        if (convertView == null && getLayoutResId() != INVALIDE_RES_ID) {
            convertView = LayoutInflater.from(mContext).inflate(getLayoutResId(), null);
        }

        renderViewItem(position, convertView);


        return convertView;

    }

    public List<T> getData() {
        return mData;
    }

    public PageLoadAdapter resetData(List<T> data) {
        if (data != null) {
            this.mData = data;
        } else {
            this.mData.clear();
        }
        notifyDataSetChanged();
        return this;
    }

    public PageLoadAdapter addMoreData(List<T> data) {
        if (mData.size() == 0) {
            if (data != null) {
                mData.addAll(data);
            }
        } else {
            if (getPageLoadParam() != null && getPageLoadParam().isFirstLoad()) {
                mData.clear();
                updateDataList(data);
                if (getListView() != null) {
                    getListView().setAdapter(this);
                }
            } else {
                updateDataList(data);
            }
        }
        if (getPageLoadParam() != null) {
            getPageLoadParam().setFirstLoad(false);
        }
        LogManager.getInstance().d(TAG, "PageLoadAdapter.size addMoreData()= " + mData.size());
        notifyDataSetChanged();
        return this;
    }

    private void updateDataList(List<T> data) {
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

    public abstract int getLayoutResId();

    protected abstract void renderViewItem(int position, View view);

    public PageLoadAdapter clearData() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
        return this;
    }

    public PageLoadParam getPageLoadParam() {
        return pageLoadParam;
    }

    public void setPageLoadParam(PageLoadParam pageLoadParam) {
        this.pageLoadParam = pageLoadParam;
    }
}
