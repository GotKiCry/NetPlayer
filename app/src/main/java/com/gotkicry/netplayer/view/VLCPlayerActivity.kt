package com.gotkicry.netplayer.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import com.gotkicry.netplayer.base.BaseActivity
import com.gotkicry.netplayer.databinding.ActivityMainBinding
import com.gotkicry.netplayer.databinding.ActivityVlcPlayerBinding
import com.gotkicry.netplayer.event.DavResEvent
import com.gotkicry.netplayer.event.MsgEvent
import com.gotkicry.netplayer.util.VLCManager
import org.videolan.libvlc.MediaPlayer


class VLCPlayerActivity : BaseActivity(){

    private val viewBinding : ActivityVlcPlayerBinding by lazy { ActivityVlcPlayerBinding.inflate(layoutInflater) }
    private lateinit var vlcManager: VLCManager
    private var davResEvent : DavResEvent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.vlc
        vlcManager = VLCManager(this,viewBinding.vlc)
        if (intent.hasExtra("path")){
            intent.getStringExtra("path")?.let {
                vlcManager.setMediaPath(it)

                Log.d("VLC","Path : $it")
            }

        }
    }

    override fun onGetMsgEvent(event: MsgEvent) {

    }

    override fun onStart() {
        super.onStart()
        if (davResEvent != null){
            vlcManager.prepareAndPlay()
            Log.d("VLC","play !!!")
        }
    }


    private fun startService(){
        startService()
    }



}