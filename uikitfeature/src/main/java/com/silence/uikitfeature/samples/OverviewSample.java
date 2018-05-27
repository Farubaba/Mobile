package com.silence.uikitfeature.samples;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.AttributeSet;

import com.silence.rootfeature.app.AppUtil;
import com.silence.rootfeature.logger.LogManager;
import com.silence.rootfeature.utils.ViewUtil;
import com.silence.uikitfeature.base.BaseView;
import com.silence.uikitfeature.base.PaintFactory;

/**
 * @author violet
 * @date 2018/3/23 10:25
 */

public class OverviewSample extends BaseView {

    protected Paint paint = PaintFactory.createPaint();
    protected Paint paintA = PaintFactory.createAPaint();
    protected Paint paintAD = PaintFactory.createADPaint();

    private int width = 100;
    private int height = 100;
    private int widthSize;
    private int heightSize;
    private int extraHeight = 1000;

    private Path animCirclePath;
    private PathMeasure animCirclePathMeasure;

    /**
     * 可重复使用的pathDst，使用之前调用reset()
     */
    private Path pathDst = new Path();
    private float passPercent;

    /**
     *
     */
    private RectF textOnPathRectF;
    private Path textOnPathPath;
    private PathMeasure animTextOnPathMeasure;

    /**
     *
     */
    private RectF rundRectF;
    private Path roundRectPath;
    private PathMeasure animRoundRectPathMeasure;

    /**
     *
     */
    private RectF arcRectF;
    private Path arcRectPath;
    private PathMeasure arcPathMeasure;
    private ValueAnimator valueAnimator;

    /**
     *
     */
    private RectF pieChartRectF;
    private RectF pieChartImportRectF;
    private Path pieChartPath;

    public OverviewSample(Context context) {
        this(context,null,0);
    }

    public OverviewSample(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OverviewSample(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);

    }

    public void initAnimCircle(){
        paint.setColor(Color.BLUE);

        animCirclePath = new Path();
        animCirclePath.addCircle(300,300,100, Path.Direction.CCW);

        animCirclePathMeasure = new PathMeasure();
        animCirclePathMeasure.setPath(animCirclePath,true);
    }

    public void initAnimTextOnPath(){
        textOnPathRectF = new RectF(100f,120f,400f,400f);
        textOnPathPath = new Path();
        textOnPathPath.addRect(textOnPathRectF,Path.Direction.CW);
        paint.setColor(Color.BLUE);

        animTextOnPathMeasure = new PathMeasure();
        animTextOnPathMeasure.setPath(textOnPathPath,true);
    }

    private void initRoundRectPath() {
        rundRectF = new RectF(500f,120f, 800f, 400f);
        roundRectPath = new Path();
        roundRectPath.addRoundRect(rundRectF,10f, 10f, Path.Direction.CCW);

        animRoundRectPathMeasure = new PathMeasure();
        animRoundRectPathMeasure.setPath(roundRectPath,true);
    }

    private void initArcPath() {
        arcRectF = new RectF(820f,140f,1180f,400f);
        arcRectPath = new Path();
        arcRectPath.addArc(arcRectF, 120f, 225f);

        arcPathMeasure = new PathMeasure();
        arcPathMeasure.setPath(arcRectPath,false);
//        arcPathMeasure.setPath(arcRectPath,true);
    }


    private void initPieChart() {
        pieChartRectF = new RectF(20f,440f,420f,840f);
        pieChartImportRectF = new RectF(20f, 450f, 420f,850f);
        pieChartPath = new Path();
        pieChartPath.addRect(pieChartRectF, Path.Direction.CCW);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        initAnimTextOnPath();

        initAnimCircle();

        initRoundRectPath();

        initValueAnimator();

        initArcPath();

        initPieChart();

    }

    //    public com.silence.uikitfeature.views.samples.OverviewSample(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }


    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        LogManager.getInstance().d("onDrawForeground");
        LogManager.getInstance().d("MeasureSpec.UNSPECIFIED =" + MeasureSpec.UNSPECIFIED + " MeasureSpec.EXACTLY= "+ MeasureSpec.EXACTLY + " MeasureSpec.AT_MOST = " + MeasureSpec.AT_MOST);
        LogManager.getInstance().d("screenWidth = "+ AppUtil.getWindowWidth() + " screenHeight = "+ AppUtil.getWindowHeight());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ViewUtil.displaySpecMode(widthMeasureSpec,heightMeasureSpec);

        int defaultWidth = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        int defaultHeight = getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);

        LogManager.getInstance().d("-mode defaultWidth = " + defaultWidth + "  defaultHeight = "+ defaultHeight);
        int widthMod = MeasureSpec.getMode(widthMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMod = MeasureSpec.getMode(heightMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthSize;
        switch (widthMod){
            case MeasureSpec.UNSPECIFIED:
                width = getFinalWidth();
                break;
            case MeasureSpec.AT_MOST:
                width = widthSize;
                break;
            case MeasureSpec.EXACTLY:
                width = getFinalWidth();
                break;
        }


        int height = heightSize;
        switch (heightMod){
            case MeasureSpec.UNSPECIFIED:
                height = getFinalHeight();
                break;
            case MeasureSpec.AT_MOST:
                height = getFinalHeight();
                break;
            case MeasureSpec.EXACTLY:
                height = getFinalHeight();
                break;
        }

        boolean isAtMostW = ViewUtil.isModeAtMost(widthMeasureSpec);
        boolean isExactlyW = ViewUtil.isModeExactly(widthMeasureSpec);
        boolean isUnSpecifiedW = ViewUtil.isModeUnspecified(widthMeasureSpec);

        boolean isAtMostH = ViewUtil.isModeAtMost(heightMeasureSpec);
        boolean isExactlyH = ViewUtil.isModeExactly(heightMeasureSpec);
        boolean isUnSpecifiedH = ViewUtil.isModeUnspecified(heightMeasureSpec);

        LogManager.getInstance().d("onMeasure  before setMeasuredDimension ==> width = "+ widthSize + " height = "+ heightSize + "widthMod = "+ widthMod + "  heightMod = "+ heightMod);

        setMeasuredDimension(width, height);
        //setMeasuredDimension(widthSize, heightSize);

        widthMod = MeasureSpec.getMode(widthMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);

        heightMod = MeasureSpec.getMode(heightMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);


        LogManager.getInstance().d("onMeasure after setMeasuredDimension ==> width = "+ widthSize + " height = "+ heightSize + "widthMod = "+ widthMod + "  heightMod = "+ heightMod);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
//        super.layout(l+width, t+width, r+height, b+height);
        //int widthdistance = (widthSize / 2) - (width / 2);
        //super.layout(l+widthdistance, t+width, r+widthdistance, b+height);
        LogManager.getInstance().d("layout l = "+ l + " t = "+t + " r = " + r +" b = " + b);
        LogManager.getInstance().d("layout l + 100 = "+ l + 100  + " t + 100  = "+t + 100  + " r + 100  = " + r + 100  +" b + 100  = " + b + 100 );
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        LogManager.getInstance().d("onLayout changed = "+ changed + "l = "+ left + " t = "+top + " r = " + right +" b = " + bottom);
        super.onLayout(changed, left , top , right, bottom);
    }

    private void initValueAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2 * 1000L);
        valueAnimator.setRepeatCount(5);
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                passPercent = (float)animation.getAnimatedValue();
                LogManager.getInstance().d("passPercent = "+ passPercent);
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textOnPathPath.lineTo(textOnPathRectF.left, textOnPathRectF.top);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                textOnPathPath.lineTo(textOnPathRectF.left, textOnPathRectF.top);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                pathDst.reset();
            }
        });
        valueAnimator.start();
    }

    public void stopValueAnimator(){
        if(valueAnimator != null && valueAnimator.isRunning()){
            valueAnimator.cancel();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopValueAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        LogManager.getInstance().d("onDraw");
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);



        drawARGB(canvas);

        drawCircle(canvas);

        drawPoint(canvas);

        drawRectPath(canvas);

        drawAnimCirclePath(canvas);

        drawRoundRectPath(canvas);

        drawArc(canvas);

        drawPieChart(canvas);

    }

    private void drawPieChart(Canvas canvas) {
        float centerX = pieChartRectF.centerX();
        float centerY = pieChartRectF.centerY();

//        canvas.rotate(180);

        int color = Color.parseColor("#516e78");
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(pieChartPath,paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.CYAN);

        canvas.drawArc(pieChartRectF,0f,35f,true,paint);
        canvas.save();

        /**
         *
         */
//        paint.setColor(Color.GREEN);
//        canvas.drawArc(pieChartRectF, 40f,120f,true,paint);
//        canvas.save();


        paint.setColor(Color.BLUE);
        canvas.drawArc(pieChartRectF, 165f,95f,true,paint);
        canvas.save();

        paint.setColor(Color.BLACK);
        canvas.drawArc(pieChartRectF, 265f,50f,true,paint);
        canvas.save();

        paint.setColor(Color.GREEN);
        canvas.drawArc(pieChartRectF, 320f,35f,true,paint);
        canvas.save();

        /**
         * 为了做出分离效果，重新定位一个RectF来绘制 这个扇形区域
         */
        paint.setColor(Color.RED);
        canvas.drawArc(pieChartImportRectF, 40f,120f,true,paint);
        canvas.save();

        canvas.rotate(90);


    }

    private void drawArc(Canvas canvas) {
        pathDst.reset();
        arcPathMeasure.getSegment(0,passPercent * arcPathMeasure.getLength(), pathDst, true);

        /**
         * 先画出整个Path
         */
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        canvas.drawPath(arcRectPath, paint);

        /**
         * DrawArc中依然使用drawPath，只不过Path初始化时，设置了pathDst.addArc();其中指定了一段弧
         */
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        canvas.drawPath(pathDst,paint);


        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.CYAN);
        canvas.drawArc(arcRectF,0f,100f,true,paint);


        float totalDegree = 100f;

        /**
         *  不断更新角度来产生动画效果
         */
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3f);
        canvas.drawArc(arcRectF, 0f, passPercent * totalDegree, true, paint);

    }

    private void drawRoundRectPath(Canvas canvas) {
        pathDst.reset();
        animRoundRectPathMeasure.getSegment(0,passPercent * animRoundRectPathMeasure.getLength(), pathDst, true);
        canvas.drawPath(pathDst, paint);

        drawTextOnPath(canvas, pathDst);
        //drawTextOnPath(canvas, roundRectPath);
    }

    private void drawRectPath(Canvas canvas) {
        pathDst.reset();
        animTextOnPathMeasure.getSegment(0, passPercent * animTextOnPathMeasure.getLength(),pathDst,true);
        paint.setColor(Color.BLUE);

        canvas.drawPath(pathDst,paint);

        /**
         * 使用pathDst，则随着PATH一起画出来，如果是使用textOnPath，则预先绘制出来，没有渐进的效果。
         */
//      drawTextOnPath(canvas, pathDst);
        drawTextOnPath(canvas, textOnPathPath);
        /**
         * 补全最后没有画出来的一个点
         */
        canvas.drawPoint(textOnPathRectF.left, textOnPathRectF.top,paint);

    }

    public void drawTextOnPath(Canvas canvas, Path path){
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawTextOnPath("text on Path, text On Path  ,text on Path ,", path,50,6,paint);
    }

    public void drawCircle(Canvas canvas){
        int widthdistance = (widthSize / 2);
        canvas.drawCircle(widthdistance, 60f,50f,paint);
    }

    public void drawARGB(Canvas canvas){
        canvas.drawARGB(222,225,225,225);
    }

    public void drawPoint(Canvas canvas){
        int center = widthSize / 2;
        canvas.drawPoint(center,60f,paint);
    }

    public void drawAnimCirclePath(Canvas canvas){
        pathDst.reset();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.RED);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        animCirclePathMeasure.getSegment(0,passPercent * animCirclePathMeasure.getLength(),pathDst,true);
        canvas.drawPath(pathDst,paint);
    }

    public int getFinalWidth(){
        //return width;
        return widthSize;
    }

    public int getFinalHeight(){
//        return height + 20 ;
        return heightSize > 0 ? heightSize : extraHeight;
    }
}


