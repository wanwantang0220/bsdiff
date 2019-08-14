package ybq.android.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApkExtract.extract(this)

    }

    companion object {
        init {
            //在应用程序启动时加载本地的lib
            System.loadLibrary("native-lib")
        }
    }
}
