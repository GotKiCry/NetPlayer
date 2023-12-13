package com.gotkicry.netplayer.event

data class LoadResult(
    val errorCode:Int,
    val errorMessage:String ? = null
) {
    companion object{
        const val TIMEOUT = 10504
    }
}