package com.gotkicry.netplayer.event

data class MsgEvent (val msg:String,val msgData:Any? = null){

    companion object{
        const val CACHE = "cache"
        const val CACHING = "caching"
    }

}