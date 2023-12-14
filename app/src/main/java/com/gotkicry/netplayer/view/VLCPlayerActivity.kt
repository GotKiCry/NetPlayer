package com.gotkicry.netplayer.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.gotkicry.netplayer.base.BaseActivity
import com.gotkicry.netplayer.databinding.ActivityVlcPlayerBinding
import com.gotkicry.netplayer.event.MsgEvent
import com.gotkicry.netplayer.util.LogUtil
import com.gotkicry.netplayer.util.VLCManager


class VLCPlayerActivity : BaseActivity(){

    private val viewBinding : ActivityVlcPlayerBinding by lazy { ActivityVlcPlayerBinding.inflate(layoutInflater) }
    private lateinit var vlcManager: VLCManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        vlcManager = VLCManager(this,viewBinding.vlc)
        if (intent.hasExtra("url")){
            val url = intent.getStringExtra("url")
            if (url != null) {
                LogUtil.d("url : $url")
                vlcManager.setMediaPath(Uri.parse(url))
            }
        }
    }




    override fun onGetMsgEvent(event: MsgEvent, isSticky: Boolean) {
    }

    override fun onStart() {
        super.onStart()
        vlcManager.prepareAndPlay()
        Log.d("VLC","play !!!")
    }


    override fun onPause() {
        super.onPause()
        vlcManager.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        vlcManager.release()
    }

}