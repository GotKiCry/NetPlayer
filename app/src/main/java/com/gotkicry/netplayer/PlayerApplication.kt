package com.gotkicry.netplayer

import android.app.Application
import org.videolan.libvlc.LibVLC

class PlayerApplication : Application() {
    companion object{
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this

    }

}