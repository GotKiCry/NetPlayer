package com.gotkicry.netplayer.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.gotkicry.netplayer.event.MsgEvent
import com.gotkicry.netplayer.util.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.videolan.libvlc.LibVLC.Event
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class CacheTempVideoService : Service() {

    private val binder = CacheTempVideoServiceBinder()

    private val buffCache = ByteArray(1024 * 512)

    private var canCache = false

    inner class CacheTempVideoServiceBinder : Binder(){
        fun getService(): CacheTempVideoService{
            return this@CacheTempVideoService
        }
    }

    override fun onBind(intent: Intent): IBinder {
        LogUtil.d("onBind")
        return binder
    }

    override fun onCreate() {
        LogUtil.d("on Service Create")
        super.onCreate()
    }

    fun stopCache(){
        canCache = false
    }

    fun createTempVideoFile(inputStream:InputStream,onCache:(filePath:String)->Unit){
        Log.d("GotKiCry","LoadTemp")
        canCache = true
        val cacheDir = this.applicationContext.cacheDir
        if (cacheDir.exists().not()){
            cacheDir.mkdirs()
        }
        val file = File(cacheDir, "cacheVideo")
        if (file.exists()){
            file.deleteRecursively()
            file.createNewFile()
        }
        val outputStream = file.outputStream()
        CoroutineScope(Dispatchers.IO).launch {

            inputStream.use { input->
                val buffer = ByteArray(1024)
                var bytesRead: Int
                outputStream.use {output->
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        // 在这里处理读取到的数据，例如打印到控制台
                        output.write(buffer, 0, bytesRead)
                        onCache(file.absolutePath)
                    }
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        val cacheDir = this.applicationContext.cacheDir
        val file = File(cacheDir, "cacheVideo")
        file.deleteRecursively()
    }




}