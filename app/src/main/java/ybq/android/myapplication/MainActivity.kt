package ybq.android.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.ui_main.*
import kotlinx.android.synthetic.main.ui_main.tv
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java!!.getSimpleName()
    lateinit var mContext: Context

    var path_file = "apk.patch"
    var url_download = "http://192.168.100.147/"+path_file

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext = this
        tv.text = BuildConfig.VERSION_NAME

        //申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val perms = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200)
            }
        }

        btnUpdate.setOnClickListener { view ->
             update()
        }

        btnSecond.setOnClickListener { view ->
            startActivity(Intent(this@MainActivity, ThirdActivity::class.java))
        }

    }

    /**
     * 合成安装包
     *
     * @param oldApk 旧版本安装包 如1.1.0安装包
     * @param patch  查分包 patch文件
     * @param output 合成后的新版本apk安装包
     */
    external fun bsPatch(oldApk: String, patch: String, output: String)

    @SuppressLint("StaticFieldLeak")
    fun update() {

        //从服务器下载patch到用户手机 sdcard

        object : AsyncTask<Void, Void, File>() {
            override fun doInBackground(vararg voids: Void): File {
//                val old1Apk = File(Environment.getExternalStorageDirectory(), "old.apk")
//                val patchApk = File(Environment.getExternalStorageDirectory(), "PATCH.patch")

//                Log.e(TAG, "patch = " + patchApk.exists() + " , " + patchApk.absolutePath)


                //获取现在运行的apk路径
                val oldApk =   applicationInfo.sourceDir
                Log.e(TAG, "第一步成功==>" + oldApk)

                // 获取拆分包的路径
                val patch = File(Environment.getExternalStorageDirectory(), "old-to-new_38.patch").getAbsolutePath()
                Log.e(TAG, "第二步成功==>" + patch)

                // 获取合成之后的新apk的路径
                val output = createNewApk().getAbsolutePath()
                Log.e(TAG, "第三步成功==>" + output)

                bsPatch(oldApk, patch, output)
                Log.e(TAG, "第四步成功==>")

                var curfile = File(output)
                Log.e(TAG, "第五步成功==>" + curfile.absolutePath)
                return curfile
            }

            override fun onPostExecute(file: File) {
                super.onPostExecute(file)
                //已经合成了，调用该方法
                UriparseUtils.installApk(this@MainActivity, file)
            }
        }.execute()
    }

    private fun createNewApk(): File {
        val newApk = File(Environment.getExternalStorageDirectory(), "new20190814.apk")
        if (!newApk.exists()) {
            try {
                newApk.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return newApk
    }

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
