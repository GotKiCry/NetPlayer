package com.gotkicry.netplayer.util

import android.widget.Toast
import com.gotkicry.netplayer.PlayerApplication

object ToastUtil {

    fun show(msg:String){
        val toast = Toast(PlayerApplication.application)
        toast.duration = Toast.LENGTH_SHORT
        toast.setText(msg)
        toast.show()
//        Toast.makeText(PlayerApplication.application,msg,Toast.LENGTH_SHORT).show()
    }
}