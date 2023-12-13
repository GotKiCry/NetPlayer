package com.gotkicry.netplayer

import com.thegrizzlylabs.sardineandroid.DavResource
import com.thegrizzlylabs.sardineandroid.impl.OkHttpSardine
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object Test {
    var USERNAME = "lzh"
    var PASSWORD = "lzh1103."
    var IS_ENABLE_SSL = true
    var BASE_URL = "gotkicry.com"
    var SUFFIX = "dav"
    var PORT = -1
    fun getDAVFile(dirName:String? = null) : List<DavResource>{
        return try {
            val okHttpSardine = OkHttpSardine(
                OkHttpClient.Builder()
                    .callTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build()
            )
            okHttpSardine.setCredentials(USERNAME,PASSWORD)
            if (dirName != null){
                SUFFIX = dirName
                if (SUFFIX.first() == '/'){
                    SUFFIX = SUFFIX.removeRange(0,1)
                }
            }
            val url = "${getStartHttp()}://$BASE_URL:${getPORT()}/$SUFFIX"

            okHttpSardine.list(url)
        }catch (e : Exception){
            e.printStackTrace()
            listOf()
        }
    }

    fun getStartHttp():String{
        return if (IS_ENABLE_SSL){
            "https"
        }else{
            "http"
        }
    }

    fun getPORT():String{
        return if (PORT == -1){
            if (IS_ENABLE_SSL){
                "443"
            }else{
                ""
            }
        }else{
            "$PORT"
        }
    }
}