### 基础开发库

        
         项目结构：rootfeature[通用基础功能]--->uikitfeature[模块领域可复用功能]--->basefeature[业务领域功能]--->app
         
          
#### 一、集成Logger，并提供统一的访问入口LogManger

#### 二、集成AndroidTest，Mockito & Espresso & UIAutomator & Roboletric
        
        <https://developer.android.com/training/testing/index.html>
        
        <https://android-developers.googleblog.com/2015/12/leveraging-product-flavors-in-android.html>
        
        Unit Test 是 Small Test 的基础功能测试。
        
        1. Local Tests ：
            Unit Test run on Local JVM Machine ：
            测试那些不依赖Android Framework的简单功能，或者可以通过mock获得的部分android framewor功能。
            通常使用Mockito来提供模拟android对象。
        1. Instrumented Test : 
            Unit Test run on Android device or emulator ：
            此类测试需要访问instrumentation特性，例如Context
            此类测试用于需要访问instrumentation特性，并且拥有不太容易被Mock的Android 依赖。
        
#### 三、集成Retrofit 2, 并提供统一的访问入口NetworkManager

#### 四、集成Gson

#### 五、集成Rxjava，RxAndroid，RxPermission

#### 六、集成Dagger

#### 七、MVP 抽象层次封装提取

#### 八、通用基础类封装BaseActivity、BaseFragment、Basexxx

#### 九、常用自定view封装成UIkit库

#### 十、多屏幕适配支持

#### 十一、Android各个版本新特性支持

#### 十二、动画封装

#### 十三、图片处理封装 Glide

#### 十四、
