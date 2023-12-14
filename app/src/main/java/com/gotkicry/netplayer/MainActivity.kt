package com.gotkicry.netplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.gotkicry.netplayer.base.BaseActivity
import com.gotkicry.netplayer.databinding.ActivityMainBinding
import com.gotkicry.netplayer.event.MsgEvent
import com.gotkicry.netplayer.view.WebDAVFileFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.videolan.libvlc.util.VLCUtil

class MainActivity : BaseActivity() {

    companion object{
        private const val TAG = "MainActivity"
    }

    private val viewBinding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        openWebDAVFileList()

    }

    override fun onGetMsgEvent(event: MsgEvent, isSticky: Boolean) {

    }


    private fun openWebDAVFileList(){
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(viewBinding.mainFragment.id,WebDAVFileFragment())
        beginTransaction.commit()
    }


}