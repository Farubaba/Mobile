package com.silence.rootfeature.paging_old;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 之所以继承自PageLoadAdapter,而不是继承BaseAdapter,是因为在Activity,Fragment中
 * 等试用当前PageLoader机制的地方，在初始化PageLoader()的时候，都需要传入一个PageLoaderAdapter的子类
 * ，所以这里为了适配，让CommonAdapter继承自PageLoadAdapter.当然也默认实现了PageLoadAdapter的getLayoutResId(),
 * renderViewItem()方法，省去子类需要重复实现的问题
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends PageLoadAdapter<T> {

    protected int mItemLayoutId = 0;

    public CommonAdapter(Context context) {
        super(context);
    }

    public CommonAdapter(Context context, List<T> data) {
        super(context, data);
    }

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        super(context, mDatas);
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return null == mData ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return null == mData ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CommonViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();

    }

    public abstract void convert(CommonViewHolder commonViewHolder, T item, int pos);

    /**
     * 目的：适配
     *
     * @param position
     * @param view
     */
    @Override
    protected void renderViewItem(int position, View view) {

    }

    /**
     * 目的适配
     *
     * @return
     */
    @Override
    public int getLayoutResId() {
        return mItemLayoutId;
    }

    private CommonViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return CommonViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

}
