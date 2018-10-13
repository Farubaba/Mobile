package com.silence.rootfeature.paging_old;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author gaoge
 * @version V1.0
 * date :  2016-05-10 15:38
 * tips 给通用Adapter使用的viewHolder
 */
public class CommonViewHolder {

    private final SparseArray<View> mViews;
    private View mConvertView;

    private CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        //setTag
        mConvertView.setTag(this);


    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static CommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {

        if (convertView == null) {
            return new CommonViewHolder(context, parent, layoutId, position);
        }
        return (CommonViewHolder) convertView.getTag();
    }


    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }


    public CommonViewHolder setVisiblity(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public CommonViewHolder setText(int viewId, String text) {
        return setText(viewId, text, -1);

    }

    public CommonViewHolder setText(int viewId, String text, int txtColor) {
        TextView view = getView(viewId);
        if (null != view) {
            view.setText(text);

            if (-1 != txtColor) {
                view.setTextColor(txtColor);
            }
        }
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public CommonViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 给TextView设置type face
     *
     * @param viewId
     * @param face
     * @return
     */
    public CommonViewHolder setTypeFace(int viewId, Typeface face) {
        TextView view = getView(viewId);
        if (null != view) {
            view.setTypeface(face);
        }

        return this;
    }

    /**
     * 给view设置点击事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    public CommonViewHolder setViewClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }
}
