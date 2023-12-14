package com.gotkicry.netplayer.model

import android.net.Uri
import com.gotkicry.netplayer.util.LogUtil

data class WebDAVConfig(
    var userName: String = "",
    var password: String = "",
    var isEnableSSL: Boolean = true,
    var baseURL: String = "",
    var suffix: String = "",
    var port: Int = -1
){

    fun getConnectURL(dirName:String?=null) : String{
        var urlSuffix = suffix
        if (dirName != null){
            urlSuffix = dirName
            if (urlSuffix.first() == '/'){
                urlSuffix = urlSuffix.removeRange(0,1)
            }
        }
        urlSuffix = Uri.encode(urlSuffix)
        val url = "${getStartHttp()}://${baseURL}:${getPORT()}/${urlSuffix}"
        LogUtil.d("getConnectURL : $url")
        return url
    }

    fun getDownloadUrl(dirName: String):String{
        var urlSuffix = dirName
        if (urlSuffix.first() == '/'){
            urlSuffix = urlSuffix.removeRange(0,1)
        }
        urlSuffix = Uri.encode(urlSuffix)
        val url = "${getStartHttp()}://${userName}:${password}@${baseURL}:${getPORT()}/${urlSuffix}"
        LogUtil.d("getDownloadUrl : $url")
        return url
    }

    private fun getStartHttp():String{
        return if (isEnableSSL){
            "https"
        }else{
            "http"
        }
    }

    private fun getPORT():String{
        return if (port == -1){
            if (isEnableSSL){
                "443"
            }else{
                ""
            }
        }else{
            "$port"
        }
    }
}
