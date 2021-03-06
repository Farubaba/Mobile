package com.silence.mobile.trainning

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.silence.mobile.R
import kotlinx.android.synthetic.main.activity_main.*

class KotlinCActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
        sample_text.text = sample_text.text.toString() + stringFromJNITest();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun stringFromJNITest(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib-test")
            System.loadLibrary("native-lib")
        }
    }
}
