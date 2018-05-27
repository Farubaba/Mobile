#### 开发总结

1. Intent.putExtra(key,value); 其中key必须以packageName开头，例如:com.fengjr.mobile.SCHEME.
1. onNewIntent()方法中需要调用setIntent(intent)，之后使用getIntent()才能获取到新的intent，否则都是旧的。
1. try startActivity() 避免出现 android.content.ActivityNotFoundException: No Activity found to handle Intent
1. MockPackageManager	This class was deprecated in API level 24. Use a mocking framework like Mockito. New tests should be written using the Android Testing Support Library.


#### 新的测试库Android Testing Support Library
1. Android Testing Support Library替换mockito。  Use a mocking framework like Mockito. New tests should be written using the Android Testing Support Library.
