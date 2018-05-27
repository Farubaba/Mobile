package com.silence.uikitfeature.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.silence.rootfeature.logger.LogManager;


/**
 * View基础扩展类。

 Category	        Methods	                                    Description
 Creation	        Constructors	                            There is a form of the constructor that are called when the view is created from code and a form that is called when the view is inflated from a layout file. The second form should parse and apply any attributes defined in the layout file.
                    onFinishInflate()	                        Called after a view and all of its children has been inflated from XML.
 Layout	            onMeasure(int, int)	                        Called to determine the size requirements for this view and all of its children.
                    onLayout(boolean, int, int, int, int)	    Called when this view should assign a size and position to all of its children.
                    onSizeChanged(int, int, int, int)	        Called when the size of this view has changed.
 Drawing	        onDraw(Canvas)	                            Called when the view should render its content.
 UriIntent processing	onKeyDown(int, KeyEvent)	                Called when a new key event occurs.
                    onKeyUp(int, KeyEvent)	                    Called when a key up event occurs.
                    onTrackballEvent(MotionEvent)	            Called when a trackball motion event occurs.
                    onTouchEvent(MotionEvent)	                Called when a touch screen motion event occurs.
 Focus	            onFocusChanged(boolean, int, Rect)	        Called when the view gains or loses focus.
                    onWindowFocusChanged(boolean)	            Called when the window containing the view gains or loses focus.
 Attaching	        onAttachedToWindow()	                    Called when the view is attached to a window.
                    onDetachedFromWindow()	                    Called when the view is detached from its window.
                    onWindowVisibilityChanged(int)	            Called when the visibility of the window containing the view has changed.

 * 相关API：<br>
 *  {@link Canvas} {@link android.graphics.Path} {@link android.graphics.Rect}  {@link android.graphics.RectF}
 *  {@link android.graphics.Matrix}  {@link android.graphics.Paint} {@link android.graphics.Bitmap} {@link android.graphics.Canvas.VertexMode}
 *  {@link android.graphics.Picture} {@link android.graphics.Canvas.EdgeType} {@link android.graphics.DrawFilter}
 * @author violet
 * @date 2018/3/23 10:28
 */

public class BaseView extends View{
    public String TAG = this.getClass().getSimpleName();
    //#####################################Creation##############################################
    public BaseView(Context context) {
        //super(context);
        this(context, null ,0 );
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        //super(context, attrs);
        this(context, attrs, 0);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogManager.getInstance().dt(TAG," constructor");
    }

    /**
     * support from API Level 21
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     */
//    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogManager.getInstance().dt(TAG," onFinishInflate");
    }

    //#####################################Layout##############################################
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogManager.getInstance().dt(TAG," onMeasure");
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        LogManager.getInstance().dt(TAG," layout");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogManager.getInstance().dt(TAG," onLayout");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogManager.getInstance().dt(TAG," onSizeChanged");
    }

    //#####################################Drawing##############################################
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogManager.getInstance().dt(TAG," onDraw");
        draw1(canvas);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        LogManager.getInstance().dt(TAG," onDrawForeground");
    }

    //#####################################UriIntent processing#####################################
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogManager.getInstance().dt(TAG," onKeyDown");
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        LogManager.getInstance().dt(TAG," onKeyUp");
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        LogManager.getInstance().dt(TAG," onTrackballEvent");
        return super.onTrackballEvent(event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        LogManager.getInstance().dt(TAG," onKeyLongPress");
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogManager.getInstance().dt(TAG," onTouchEvent");
        return super.onTouchEvent(event);
    }

    //#####################################Focus###############################################

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        LogManager.getInstance().dt(TAG, " onFocusChanged");
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        LogManager.getInstance().dt(TAG, " onWindowFocusChanged");
    }

    //#####################################Attaching###########################################

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogManager.getInstance().dt(TAG," onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogManager.getInstance().dt(TAG," onDetachedFromWindow");
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        LogManager.getInstance().dt(TAG," onWindowVisibilityChanged");
    }


    //#####################################自定义扩展方法########################################

    private void draw1(Canvas canvas) {

    }
}
