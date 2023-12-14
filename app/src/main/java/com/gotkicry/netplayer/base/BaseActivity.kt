package com.gotkicry.netplayer.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.gotkicry.netplayer.event.MsgEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : AppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getMsg(event: MsgEvent){
        onGetMsgEvent(event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun getStickyMsg(event: MsgEvent){
        onGetMsgEvent(event,true)
    }

    abstract fun onGetMsgEvent(event: MsgEvent,isSticky :Boolean = false)

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}