package com.gotkicry.netplayer.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.gotkicry.netplayer.event.MsgEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseActivity : AppCompatActivity() {

    @Subscribe
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getMsg(event: MsgEvent){
        onGetMsgEvent(event)
    }

    abstract fun onGetMsgEvent(event: MsgEvent)

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}