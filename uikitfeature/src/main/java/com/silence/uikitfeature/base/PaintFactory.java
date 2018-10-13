package com.silence.uikitfeature.base;

import android.graphics.Paint;

/**
 * Paint对象的简单工厂/ 简单静态工厂，提供常用的Paint定义<br>
 *
 * Decouple 解耦。降低依赖<br>
 *
 * 设计模式之工厂模式：所有工厂模式都是为了封装对象的创建。<br>
 *
 * 1. 简单工厂：需要创建工厂对象，但可以通过继承来继续扩展工厂类<br>
 * 2. 静态工厂：不需要创建对象，直接调用，缺点是不能通过继承来扩展了<br>
 * 3. 工厂方法模式(creator)：定义了一个创建对象的接口（提供abstract方法），但由子类来决定要实例化的类是哪一个，工厂方法让类把实例化推迟到子类。<br>
 * 4. 抽象工厂模式：提供一个接口，用于创建相关或依赖对象的家族，而不需要明确指定具体类。<br>
 *
 *
 * @author violet
 * date :  2018/3/23 10:45
 */

public class PaintFactory {

    public static Paint createPaint(){
        Paint paint = new Paint();
        return paint;
    }

    public static Paint createAPaint(){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        return paint;
    }

    public static Paint createADPaint(){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        return paint;
    }


}
