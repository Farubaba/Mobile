package com.silence.uikitfeature.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.silence.rootfeature.app.AppUtil;
import com.silence.uikitfeature.R;

/**
 * @author violet
 * @date 2018/4/30 18:47
 */

public class LinearProgressBar extends ProgressBar {

    /**
     <attr name="progress_reached_color" format="color"></attr>
     <attr name="progress_reached_height" format="dimension"></attr>
     <attr name="progress_unreach_color" format="color"></attr>
     <attr name="progress_unreach_height" format="dimension"></attr>
     <attr name="progress_text_color" format="color"></attr>
     <attr name="progress_text_size" format="dimension"></attr>
     <attr name="progress_text_offset" format="dimension"></attr>
     <attr name="show_progress_text" format="boolean"></attr>
     <attr name="round_progress_line" format="boolean"></attr>
     */
    public static final int DEFAULT_PROGRESS_REACHED_COLOR = 0xFFFF9900;
    public static final int DEFAULT_PROGRESS_REACHED_HEIGHT = 20;//dp override from xml
    public static final int DEFAULT_PROGRESS_UNREACH_COLOR = 0xFFFF5555;
    public static final int DEFAULT_PROGRESS_UNREACH_HEIGHT = 15;//dp override from xml
    public static final int DEFAULT_PROGRESS_TEXT_COLOR = 0x79B66B;
    public static final int DEFAULT_PROGRESS_TEXT_SIZE = 15;//sp override from xml
    public static final int DEFAULT_PROGRESS_TEXT_OFFSET = 10;//dp override from xml

    public int progressReachedColor = DEFAULT_PROGRESS_REACHED_COLOR;
    public int progressReachedHeight = DEFAULT_PROGRESS_REACHED_HEIGHT;
    public int progressUnreachColor = DEFAULT_PROGRESS_UNREACH_COLOR;
    public int progressUnReachHeight = DEFAULT_PROGRESS_UNREACH_HEIGHT;
    public int progressTextColor = DEFAULT_PROGRESS_TEXT_COLOR;
    public int progressTextSize = DEFAULT_PROGRESS_TEXT_SIZE;
    public int progressTextOffset = DEFAULT_PROGRESS_TEXT_OFFSET;

    public boolean showProgressText = true;
    public boolean roundProgressLine = false;
    public Paint textPaint;
    public Paint reachedPaint;
    public Paint unreachPaint;

    public int realWidth;
    public int realHeight;


    public LinearProgressBar(Context context) {
        this(context,null);
    }

    public LinearProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

//    public LinearProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context,attrs,defStyleAttr);
//    }

    public LinearProgressBar init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinearProgressBar);

        progressReachedColor = typedArray.getColor(R.styleable.LinearProgressBar_progress_reached_color,progressReachedColor);
        float reachedHeight = typedArray.getDimension(R.styleable.LinearProgressBar_progress_reached_height, progressReachedHeight);
        progressReachedHeight = AppUtil.dp2pxInt(reachedHeight);

        progressUnreachColor = typedArray.getColor(R.styleable.LinearProgressBar_progress_unreach_color, progressUnreachColor);
        float unreachHeight = typedArray.getDimension(R.styleable.LinearProgressBar_progress_unreach_height, progressUnReachHeight);
        progressUnReachHeight = AppUtil.dp2pxInt(unreachHeight);

        progressTextColor = typedArray.getColor(R.styleable.LinearProgressBar_progress_text_color, progressTextColor);

        float textOffset = typedArray.getDimension(R.styleable.LinearProgressBar_progress_text_offset,progressTextOffset);
        progressTextOffset = AppUtil.dp2pxInt(textOffset);
        progressTextOffset = typedArray.getDimensionPixelOffset(R.styleable.LinearProgressBar_progress_text_offset,progressTextOffset);


        float textSize = typedArray.getDimension(R.styleable.LinearProgressBar_progress_text_size, progressTextSize);
        progressTextSize = AppUtil.sp2pxInt(textSize);
        progressTextSize = typedArray.getDimensionPixelOffset(R.styleable.LinearProgressBar_progress_text_size,progressTextSize);
        progressTextSize = typedArray.getDimensionPixelSize(R.styleable.LinearProgressBar_progress_text_size, progressTextSize);

        showProgressText = typedArray.getBoolean(R.styleable.LinearProgressBar_show_progress_text, true);
        roundProgressLine = typedArray.getBoolean(R.styleable.LinearProgressBar_round_progress_line, false);

        typedArray.recycle();

        textPaint = new Paint();
        textPaint.setTextSize(progressTextSize);
        textPaint.setColor(progressTextColor);

        reachedPaint = new Paint();
        reachedPaint.setColor(progressReachedColor);
        reachedPaint.setStrokeWidth(progressReachedHeight);

        unreachPaint = new Paint();
        unreachPaint.setColor(progressUnreachColor);
        unreachPaint.setStrokeWidth(progressUnReachHeight);
        return this;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidh = width;
        int resultHight = height;

        switch(widthMode){
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        //resolveSize()
        switch(heightMode){
            case MeasureSpec.EXACTLY:
                resultHight = height;
                break;
            case MeasureSpec.AT_MOST:
                //1.进度条高度应该是reachedHeight、TextHeight、unreachedHeight中最大的一个值
                //2.progressHeight 不能大于AT_MOST中的height值
                int textHeight = (int)Math.abs(textPaint.ascent() - textPaint.descent()) + getPaddingTop() + getPaddingBottom();
                resultHight = Math.min(Math.max(Math.max(progressReachedHeight,progressUnReachHeight),textHeight),height);
                break;
            case MeasureSpec.UNSPECIFIED:
                int th = (int)Math.abs(textPaint.ascent() - textPaint.descent()) + getPaddingTop() + getPaddingBottom();
                resultHight = Math.min(Math.max(Math.max(progressReachedHeight,progressUnReachHeight),th),height);
                break;
        }

        setMeasuredDimension(width, resultHight);

        realHeight = getMeasuredHeight();
        realWidth = getMeasuredWidth();

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        float progress = 39f;
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        float percent = progress / getMax();

        String text = progress + "%";
        float endx = percent * realWidth - progressTextOffset;
        canvas.drawLine(0,0,endx, 0,reachedPaint);
        canvas.drawText(text,endx + progressTextOffset,0,textPaint);
        float textwidth = textPaint.measureText(text);
        canvas.drawLine((endx + progressTextOffset *2 + textwidth),0,realWidth,0, unreachPaint);

        canvas.restore();
    }

}
