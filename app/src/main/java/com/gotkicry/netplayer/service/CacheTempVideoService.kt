package com.gotkicry.netplayer.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.gotkicry.netplayer.event.MsgEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.videolan.libvlc.LibVLC.Event
import java.io.File
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
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
    }

    @Subscribe
    fun getMsg(msgEvent: MsgEvent){
        if (msgEvent.msg == MsgEvent.CACHE){
            if (msgEvent.msgData is InputStream){
                canCache = true
                createTempVideoFile(msgEvent.msgData)
            }
        }

    }

    private fun createTempVideoFile(inputStream:InputStream){
        val cacheDir = this.applicationContext.cacheDir
        if (cacheDir.exists().not()){
            cacheDir.mkdirs()
        }
        while (inputStream.read(buffCache) != 0 && canCache){
            val file = File(cacheDir, "cacheVideo")
            if (file.exists()){
                file.deleteRecursively()
                file.createNewFile()
            }
            file.outputStream().write(buffCache,0,buffCache.count())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val cacheDir = this.applicationContext.cacheDir
        val file = File(cacheDir, "cacheVideo")
        file.deleteRecursively()
        EventBus.getDefault().unregister(this)
    }




}