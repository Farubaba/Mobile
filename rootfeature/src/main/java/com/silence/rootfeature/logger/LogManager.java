package com.silence.rootfeature.logger;

import android.support.v4.util.LruCache;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.orhanobut.logger.Printer;
import com.silence.rootfeature.BuildConfig;
import com.silence.rootfeature.app.C;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通用日志管理类，目前封装了 com.orhanobut.logger库，如果想要移除com.orhanobut.logger库，切换成支持其他的库，
 * 只需要在依赖中删除com.orhanobut.logger依赖，并且修改和com.orhanobut.logger相关的类，即可
 * <b>
 * LogManager中的代码几乎不需要修改，而且调用者的代码也不需要做任何修改
 * <b/>
 *
 * Example:<br>
 *      1.如果只采用默认TAG，不需要自定义TAG，则采用如下方式调用:
 * <code>
 *      LogManager.getInstance().d("this is the log information");
 *      LogManager.getInstance().d("this %1$s the %2$s information", "is", "log");
 * </code>
 *      2.如果需要自定义TAG，采用如下方式调用。
 * <code>
 *      LogManager.getInstance().t("CUSTOM_TAG").d("this is the log information");
 *      LogManager.getInstance().t("CUSTOM_TAG").d("this %1$s the %2$s information", "is", "log");
 * </code>
 *      3.如果想要以比较简洁的方式设置自定义TAG，采用如下方式调用：
 * <code>
 *      LogManager.getInstance().dt("CUSTOM_TAG","this is the log information")
 *      LogManager.getInstance().dt("CUSTOM_TAG","this %1$s the %2$s information", "is", "log")
 * </code>
 * 实现ILogPrinter只是为了生成对应的接口方法，
 *
 * @author violet
 * date :  2018/3/5 09:55
 */
public class LogManager implements ILogPrinter {

    private static LogManager instance = new LogManager();
    private static AtomicInteger index = new AtomicInteger(0);
    private boolean showIndexPrefix = false;
    private static  String INDEX_PREFIX_SAPERATOR = C.Strings.LOG_INDEX_SEPERATOR;

    private LogManager() {
        LruCache<String,String> lruCache = null;

    }

    public static LogManager getInstance() {
        return instance;
    }

    /**
     * 该方法不只能调用一次，否则会出现日志输出多次的情况。
     * @return
     */
    public LogManager init(){
        //FIXME 修改根据BuildConfig和Gradle Flavor 配置来控制初始化
        initLogcat().setShowIndexPrefix(false);
        //initLogDiskCsv();
        return this;
    }

    /**
     * 设置是否在日志前增加执行顺序序号。
     * @param showIndexPrefix
     * @return
     */
    public LogManager setShowIndexPrefix(boolean showIndexPrefix){
        this.showIndexPrefix = showIndexPrefix;
        return this;
    }

    /**
     * 后的最后的message
     * @param message
     * @return
     */
    private String getMessage(String message){
        if(showIndexPrefix){
            return index.incrementAndGet() + INDEX_PREFIX_SAPERATOR + message;
        }
        return message;
    }

    /**
     * 重置日志执行序号
     * @return
     */
    public LogManager resetIndex(){
        index.set(0);
        return this;
    }

    /**
     * 需要日志记录输出到logcat时候，应该调用此方法
     *
     * @return
     */
    private LogManager initLogcat() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(1)// (Optional) Hides internal method calls up to offset. Default 5
                // .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                //.tag(AppUtil.getPackageName())   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                //return super.isLoggable(priority, tag);
                //FIXME 根据BuildConfig和Gradle配置来决定是否开启日志
                if(BuildConfig.DEBUG){
                    return true;
                }
                return false;
            }
        });
        return this;
    }

    /**
     * 需要日志记录输出到磁盘时，应该初始化此方法
     *
     * @return
     */
    private LogManager initLogDiskCsv() {
        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()//
                .date(new Date()).dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                //.logStrategy("默认已经设置好，存放在sdcard目录，参考 DiskLogAdapter")
                //.tag(AppUtil.getPackageName())
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                //return super.isLoggable(priority, tag);
                //FIXME 根据BuildConfig和Gradle配置来决定是否开启日志
                if(BuildConfig.DEBUG){
                    return true;
                }
                return false;
            }
        });
        return this;
    }

    //Method inherit form  Printer

    @Override
    public void addAdapter(LogAdapter adapter) {
        Logger.addLogAdapter(adapter);
    }

    /**
     * 参考：{@link com.orhanobut.logger.LoggerPrinter}
     * @param tag
     * @return
     */
    @Override
    public Printer t(String tag) {
        return Logger.t(tag);
    }

    @Override
    public void d(String message, Object... args) {
        Logger.d(getMessage(message), args);
    }

    @Override
    public void d(Object object) {
        if(object instanceof String){
            Logger.d(getMessage((String)object));
        }else{
            Logger.d(object);
        }
    }

    @Override
    public void e(String message, Object... args) {
        Logger.e(getMessage(message), args);
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, getMessage(message), args);
    }

    @Override
    public void w(String message, Object... args) {
        Logger.w(getMessage(message), args);
    }

    @Override
    public void i(String message, Object... args) {
        Logger.i(getMessage(message), args);
    }

    @Override
    public void v(String message, Object... args) {
        Logger.v(getMessage(message), args);
    }

    @Override
    public void wtf(String message, Object... args) {
        Logger.wtf(getMessage(message), args);
    }

    @Override
    public void json(String json) {
        Logger.json(json);
    }

    @Override
    public void xml(String xml) {
        Logger.xml(xml);
    }

    @Override
    public void log(int priority, String tag, String message, Throwable throwable) {
        Logger.log(priority, tag, getMessage(message), throwable);
    }

    @Override
    public void clearLogAdapters() {
        Logger.clearLogAdapters();
    }


    /**
     * 直接附加TAG的方法
     * @param message
     * @param args
     */

    @Override
    public void dt(String TAG, String message, Object... args) {
        if(TAG != null && args != null){
            t(TAG).d(getMessage(message), args);
        }
    }

    @Override
    public void dt(String TAG, Object object) {
        if(TAG != null && object != null){
            if(object instanceof String){
                t(TAG).d(getMessage((String)object));
            }else{
                t(TAG).d(object);
            }
        }
    }

    @Override
    public void dt(String TAG, File file) {

    }

    @Override
    public void et(String TAG, String message, Object... args) {
        if(TAG != null && args != null) {
            t(TAG).e(getMessage(message), args);
        }
    }

    @Override
    public void et(String TAG, Throwable throwable, String message, Object... args) {
        if(TAG != null && message != null && args != null){
            t(TAG).e(throwable, getMessage(message), args);
        }
    }

    @Override
    public void wt(String TAG, String message, Object... args) {
        if(TAG != null && message !=null && args != null) {
            t(TAG).w(getMessage(message), args);
        }
    }

    @Override
    public void it(String TAG, String message, Object... args) {
        if(TAG != null && message !=null && args != null) {
            t(TAG).i(getMessage(message), args);
        }
    }

    @Override
    public void vt(String TAG, String message, Object... args) {
        if(TAG != null && message !=null && args != null) {
            t(TAG).v(getMessage(message), args);
        }
    }

    @Override
    public void wtft(String TAG, String message, Object... args) {
        if(TAG != null && message !=null && args != null) {
            t(TAG).wtf(getMessage(message), args);
        }
    }

    @Override
    public void jsont(String TAG, String json) {
        if(TAG !=null && json != null) {
            t(TAG).json(json);
        }
    }

    @Override
    public void xmlt(String TAG, String xml) {
        if(TAG !=null && xml != null) {
            t(TAG).xml(xml);
        }
    }
}
